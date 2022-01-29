package com.telran.dao.crm;

import com.telran.dao.entity.DeliveryOrderEntity;
import com.telran.dao.entity.MenuItemEntity;
import com.telran.dao.entity.UserOrderEntity;
import com.telran.dao.entity.UserOrderStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public interface CRM {
    //For admin
    void addDeliveryOrder(DeliveryOrderEntity order);

    Stream<DeliveryOrderEntity> getDeliveryOrderPeriod(LocalDate from, LocalDate to);

    Stream<UserOrderEntity> getUserOrdersPeriod(LocalDate from, LocalDate to);

    Stream<UserOrderEntity> getUserOrdersPeriodByStatus(LocalDate from, LocalDate to, UserOrderStatus status);

    //For users
    UUID createUserOrder();

    void updateUserOrder(UUID id, List<MenuItemEntity> items);

    UserOrderEntity getOrderById(UUID id);

    void closeOrder(UUID id);
}
