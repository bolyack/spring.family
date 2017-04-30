package com.bamboo.jercn.common;

import org.springframework.context.ApplicationContext;

/**
 * Code14----------------------------------------------
 * Created by bamboo on 2017/4/30.
 */
public class ApplicationContextHelper {

    public static ApplicationContext ctx = null;

    public static Object getBean(String beanName) {
        return ctx.getBean(beanName);
    }

}
