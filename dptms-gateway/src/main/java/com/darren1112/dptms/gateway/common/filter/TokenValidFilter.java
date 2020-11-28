package com.darren1112.dptms.gateway.common.filter;

import com.darren1112.dptms.common.core.constants.FileConstant;
import com.darren1112.dptms.common.core.constants.SecurityConstant;
import com.darren1112.dptms.common.core.message.JsonResult;
import com.darren1112.dptms.common.core.util.CookieUtil;
import com.darren1112.dptms.common.core.util.ResponseUtil;
import com.darren1112.dptms.common.core.util.StringUtil;
import com.darren1112.dptms.common.core.util.UrlUtil;
import com.darren1112.dptms.common.redis.starter.util.TokenUtil;
import com.darren1112.dptms.common.spi.common.entity.ActiveUser;
import com.darren1112.dptms.gateway.common.enums.GatewayErrorCodeEnum;
import com.darren1112.dptms.gateway.common.properties.SecurityProperties;
import com.darren1112.dptms.gateway.remoting.AuthRemoting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 安全验证过滤器
 *
 * @author luyuhao
 * @date 2020/11/26 23:28
 */
@Slf4j
@Component
public class TokenValidFilter extends OncePerRequestFilter {


    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthRemoting authRemoting;

    /**
     * 是否需要过滤
     *
     * @return true/false
     * @author luyuhao
     * @date 20/08/02 14:45
     */
    private boolean shouldNotFilter(String uri) {
        log.info("TokenValidFilter" );
        //白名单校验
        return UrlUtil.matchUri(uri, securityProperties.getAnonUris())
                || UrlUtil.matchUri(uri, FileConstant.STATIC_PATTERNS);
    }

    /**
     * 执行内容
     * 1. accessToken和refreshToken均无效 => 打回
     * 2. accessToken和refreshToken均有效 => 放行
     * 3. accessToken无效, refreshToken有效 => 刷新token，重置cookie
     * 4. accessToken有效, refreshToken无效 => 打回
     * 5. 适当延长refreshToken有效期
     *
     * @author luyuhao
     * @date 20/08/02 14:46
     */
    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (shouldNotFilter(uri)) {
            chain.doFilter(request, response);
            return;
        }
        try {
            // token校验
            // 优先处理cookie中携带的cookie
            String accessToken = CookieUtil.getCookie(SecurityConstant.ACCESS_TOKEN_KEY, request);
            String refreshToken = CookieUtil.getCookie(SecurityConstant.REFRESH_TOKEN_KEY, request);
            if (StringUtil.isBlank(accessToken) || StringUtil.isBlank(refreshToken)) {
                accessToken = request.getHeader(SecurityConstant.ACCESS_TOKEN_KEY);
                refreshToken = request.getHeader(SecurityConstant.REFRESH_TOKEN_KEY);
            }
            // 非空验证
            if (StringUtil.isBlank(accessToken) || StringUtil.isBlank(refreshToken)) {
                ResponseUtil.setJsonResult(response, JsonResult.buildErrorEnum(GatewayErrorCodeEnum.NOT_LOGIN));
                return;
            }
            // token存在，校验是否合法
            String redisRefreshToken = tokenUtil.getRefreshToken(accessToken);
            ActiveUser activeUser = tokenUtil.getActiveUser(refreshToken);

            if (!tokenValidHandle(refreshToken, redisRefreshToken, activeUser, response)) {
                ResponseUtil.setJsonResult(response, JsonResult.buildErrorEnum(GatewayErrorCodeEnum.NOT_LOGIN));
                return;
            }
            // TODO 【暂不需要】refresh token快失效，延长有效期
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ResponseUtil.setJsonResult(response, JsonResult.buildErrorEnum(GatewayErrorCodeEnum.TOKEN_VALID_ERROR));
            return;
        }
        chain.doFilter(request, response);
    }

    /**
     * token校验处理
     *
     * @param refreshToken      refreshToken
     * @param redisRefreshToken redis中的refreshToken
     * @param activeUser        redis中的activeUser
     * @param response          响应域
     * @return true/false
     * @author luyuhao
     * @date 2020/11/28 10:47
     */
    private boolean tokenValidHandle(String refreshToken, String redisRefreshToken, ActiveUser activeUser, HttpServletResponse response) {
        if (StringUtil.isNotBlank(redisRefreshToken)
                && redisRefreshToken.equals(refreshToken)
                && Objects.nonNull(activeUser)) {
            // accessToken和refreshToken均有效 => 放行
            return true;
        } else if (StringUtil.isBlank(redisRefreshToken) && Objects.nonNull(activeUser)) {
            // accessToken无效, refreshToken有效 => 刷新accessToken，重置cookie
            String newAccessToken = authRemoting.refreshAccessToken(refreshToken).getData();
            CookieUtil.setCookie(SecurityConstant.ACCESS_TOKEN_KEY, newAccessToken, response);
            return true;
        } else {
            // accessToken和refreshToken均无效 => 打回
            // accessToken有效, refreshToken无效 => 打回
            return false;
        }
    }

}
