package com.zhouq.eshop.inventory.service;

import com.zhouq.eshop.inventory.request.Request;

/**
 * 请求异步执行 service
 *
 * @author zhouq
 * @email zhouqiao@gmail.com
 * @date 2017/12/5 23:46
 */
public interface RequertAsyncProcessService {

    void process(Request request);

}
