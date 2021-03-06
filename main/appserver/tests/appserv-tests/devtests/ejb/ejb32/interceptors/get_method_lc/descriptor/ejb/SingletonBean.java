package com.acme;

import javax.ejb.*;
import javax.annotation.*;
import javax.interceptor.*;

@Singleton
public class SingletonBean extends BaseBean implements Snglt {


    @EJB SfulEJB sful;

    public SingletonBean() {}

    @PostConstruct
    public void init() {
        System.out.println("In SingletonBean::init()");
        verifyMethod("init");
        sful.hello();
    }
    
    public String hello() {
        verifyA_AC("SingletonBean");
        //verifyAB_AC("SingletonBean");
        verifyA_PC("SingletonBean");
	System.out.println("In SingletonBean::hello()");
        sful.remove();
	return "hello, world!\n";
    }

    @PreDestroy
    public void destroy() {
        System.out.println("In SingletonBean::destroy()");
        verifyMethod("destroy");
    }
}
