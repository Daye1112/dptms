package com.darren1112.dptms.test;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * @author luyuhao
 * @date 2020/12/12 01:35
 */
public class CommonTest {

    @Test
    public void test01() {
        String string = new String();
        System.out.println(string.getClass().getName());
        System.out.println(string.getClass().getSimpleName());
        System.out.println(string.hashCode());

        int i = 1;
        Object object = i;
        System.out.println(object.hashCode());
    }

    @Test
    public void test02() throws UnsupportedEncodingException {
        String str = "";
        byte[] bytes = str.getBytes("UTF-8");
        System.out.println(bytes.length);
    }
}
