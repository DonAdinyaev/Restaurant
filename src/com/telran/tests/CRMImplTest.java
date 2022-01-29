package com.telran.tests;

import com.telran.dao.crm.CRMImpl;
import com.telran.dao.entity.DeliveryOrderEntity;
import com.telran.dao.entity.ProductEntity;
import com.telran.dao.entity.ProductUnit;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CRMImplTest {
    CRMImpl crm;

    void init() {
        crm = new CRMImpl();
    }

    @Test
    void addDeliveryOrder_DeliveryOrder_Added() {
        init();
        var potatoes = new ProductEntity("Potato", 2, ProductUnit.KG, 0);
        var chicken = new ProductEntity("Chicken", 1, ProductUnit.KG, 0);
        Set<ProductEntity> setOfProducts = new HashSet<>();
        setOfProducts.add(potatoes);
        setOfProducts.add(chicken);
        DeliveryOrderEntity deliveryOrder = new DeliveryOrderEntity(LocalDateTime.now(), setOfProducts);
        crm.addDeliveryOrder(deliveryOrder);
        assertEquals(deliveryOrder, crm.getDeliveryOrderPeriod(LocalDateTime.MIN.toLocalDate(), LocalDateTime.MAX.toLocalDate()).findAny().orElse(null));
    }

    @Test
    void getDeliveryOrderPeriod() {
        init();
        ProductEntity potatoes = new ProductEntity("Potato", 2, ProductUnit.KG, 0);
        ProductEntity chicken = new ProductEntity("Chicken", 1, ProductUnit.KG, 0);
        Set<ProductEntity> setOfProducts = new HashSet<>();
        setOfProducts.add(potatoes);
        setOfProducts.add(chicken);
        DeliveryOrderEntity deliveryOrder1 = new DeliveryOrderEntity(LocalDateTime.now().minusDays(2), setOfProducts);
        DeliveryOrderEntity deliveryOrder2 = new DeliveryOrderEntity(LocalDateTime.now().minusDays(1), setOfProducts);
        DeliveryOrderEntity deliveryOrder3 = new DeliveryOrderEntity(LocalDateTime.now(), setOfProducts);
        crm.addDeliveryOrder(deliveryOrder1);
        crm.addDeliveryOrder(deliveryOrder2);
        crm.addDeliveryOrder(deliveryOrder3);
        DeliveryOrderEntity[] deliveryOrderEntities = {deliveryOrder1, deliveryOrder2};
        assertArrayEquals(deliveryOrderEntities, crm.getDeliveryOrderPeriod(LocalDateTime.now().minusDays(3).toLocalDate(), LocalDateTime.now().toLocalDate()).toArray());
    }

    @Test
    void getUserOrdersPeriod() {

    }
}