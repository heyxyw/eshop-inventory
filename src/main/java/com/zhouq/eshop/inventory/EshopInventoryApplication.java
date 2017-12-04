package com.zhouq.eshop.inventory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 商品库存服务
 * @author zhouq
 *
 */
@SpringBootApplication
@MapperScan("com.zhouq.eshop.inventory.mapper")
public class EshopInventoryApplication {
	public static void main(String[] args) {
		SpringApplication.run(EshopInventoryApplication.class, args);
	}
}
