package com.zhouq.eshop.inventory.config;

import com.zhouq.eshop.inventory.listener.InitListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhouq
 * @Description: TODO
 * @email zhouqiao@gmail.com
 * @date 2017/11/30 0:15
 */
@Configuration
public class Config {

    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean(){
        ServletListenerRegistrationBean servletListenerRegistrationBean =
                new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new InitListener());
        return servletListenerRegistrationBean;
    }
}
