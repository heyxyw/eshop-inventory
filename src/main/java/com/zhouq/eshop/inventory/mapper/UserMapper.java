package com.zhouq.eshop.inventory.mapper;

import com.zhouq.eshop.inventory.model.User;
import org.springframework.stereotype.Component;

/**
 * @author zhouq
 * @Description: TODO
 * @email zhouqiao@gmail.com
 * @date 2017/11/29 22:58
 */
@Component
public interface UserMapper {
    User findUserById(Integer id);
}
