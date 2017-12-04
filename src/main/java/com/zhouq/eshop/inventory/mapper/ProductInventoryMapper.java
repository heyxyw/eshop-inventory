package com.zhouq.eshop.inventory.mapper;

import com.zhouq.eshop.inventory.model.ProductInventory;
import org.apache.ibatis.annotations.Param;

/**
 * 商品数量mapper
 *
 * @author zhouq
 * @email zhouqiao@gmail.com
 * @date 2017/12/1 0:13
 */
public interface ProductInventoryMapper {

    /**
     * 更新商品库存
     * @param inventoryCnt 商品库存
     */
    void updateProductInventory(ProductInventory inventoryCnt);

    /**
     * 获取商品库存
     * @param productId
     * @return
     */
    ProductInventory findProductInventory(@Param("productId") Integer productId);

}
