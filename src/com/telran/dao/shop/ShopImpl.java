package com.telran.dao.shop;

import com.telran.dao.entity.ProductEntity;
import com.telran.dao.entity.ProductUnit;

import java.util.HashMap;
import java.util.Map;

public class ShopImpl implements Shop {
    private final Map<String, ProductEntity> map;

    public ShopImpl() {
        map = new HashMap<>();
        map.put("Potato", new ProductEntity("Potato", 10, ProductUnit.KG, 0));
        map.put("Milk", new ProductEntity("Milk", 5, ProductUnit.LITER, 0));
        map.put("Pasta", new ProductEntity("Pasta", 15, ProductUnit.KG, 0));
        map.put("Oil", new ProductEntity("Oil", 12, ProductUnit.LITER, 0));
        map.put("Chicken", new ProductEntity("Chicken", 25, ProductUnit.KG, 0));
        map.put("Egg", new ProductEntity("Egg", 30, ProductUnit.UNIT, 0));
    }

    @Override
    public ProductEntity getProductByName(String name) {
        return map.get(name);
    }
}
