package com.zhouq.eshop.inventory.listener;

import com.zhouq.eshop.inventory.thread.RequestProcessorThreadPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author zhouq
 * @Description: 启动监听器
 * @email zhouqiao@gmail.com
 * @date 2017/11/30 0:15
 */
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //初始化工作线程池 和内存队列
        RequestProcessorThreadPool.init();
        System.out.println("====================系统初始化====================");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("====================系统注销====================");
    }
}
