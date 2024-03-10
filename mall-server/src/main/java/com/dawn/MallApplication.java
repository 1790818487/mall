package com.dawn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MallApplication {
    public static void main(String[] args) {
        System.out.println("商城系统启动中，保佑没有bug");
        SpringApplication.run(MallApplication.class,args);
        System.out.println("(♥◠‿◠)ﾉﾞ  商城系统启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
