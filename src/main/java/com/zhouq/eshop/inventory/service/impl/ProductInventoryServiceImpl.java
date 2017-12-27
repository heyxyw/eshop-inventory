package com.zhouq.eshop.inventory.service.impl;

import com.zhouq.eshop.inventory.dao.RedisDao;
import com.zhouq.eshop.inventory.mapper.ProductInventoryMapper;
import com.zhouq.eshop.inventory.model.ProductInventory;
import com.zhouq.eshop.inventory.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 商品库存service 实现
 *
 * @author zhouq
 * @email zhouqiao@gmail.com
 * @date 2017/12/4 23:27
 */
@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {


    @Autowired
    private ProductInventoryMapper productInventoryMapper;

    @Autowired
    private RedisDao redisDao;

    /**
     * 更新商品库存
     *
     * @param productInventory 商品库存
     */
    @Override
    public void updateProductInventory(ProductInventory productInventory) {

        productInventoryMapper.updateProductInventory(productInventory);
    }

    /**
     * 移除 商品库存缓存
     *
     * @param productInventory
     */
    @Override
    public void removeProductInventoryCache(ProductInventory productInventory) {
        redisDao.delete("product:inventory:" + productInventory.getProductId());
    }

    /**
     * 获取商品库存
     *
     * @param productId 商品ID
     * @return
     */
    @Override
    public ProductInventory findProductInventory(Integer productId) {

        return productInventoryMapper.findProductInventory(productId);
    }

    /**
     * 设置商品库存缓存
     *
     * @param productInventory
     */
    @Override
    public void setProductInventroyCache(ProductInventory productInventory) {
        redisDao.set("product:inventory:" + productInventory.getProductId(), String.valueOf(productInventory.getInventoryCnt()));
    }

    /**
     * 获取商品缓存数据
     *
     * @param productId
     * @return
     */
    @Override
    public ProductInventory getProductInventoryCache(Integer productId) {
        Long inventoryCnt = 0L;
        String key = "product:inventory" + productId;
        String s = redisDao.get(key);
        if (!StringUtils.isEmpty(s)){
            try {
                inventoryCnt = Long.valueOf(s);
                return new ProductInventory(productId,inventoryCnt);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }
}
