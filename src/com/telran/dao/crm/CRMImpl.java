package com.telran.dao.crm;

import com.telran.dao.entity.DeliveryOrderEntity;
import com.telran.dao.entity.MenuItemEntity;
import com.telran.dao.entity.UserOrderEntity;
import com.telran.dao.entity.UserOrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class CRMImpl implements CRM {
    private final TreeMap<LocalDate, HashMap<UUID, DeliveryOrderEntity>> deliveryOrders;
    private final TreeMap<LocalDate, HashMap<UUID, UserOrderEntity>> userOrdersByDate;
    private final Map<UUID, UserOrderEntity> userOrdersByID;

    public CRMImpl() {
        this.deliveryOrders = new TreeMap<>();
        this.userOrdersByDate = new TreeMap<>();
        this.userOrdersByID = new HashMap<>();
    }

    @Override
    public void addDeliveryOrder(DeliveryOrderEntity order) {
        deliveryOrders.computeIfAbsent(
                requireNonNull(order.getDate()).toLocalDate(),
                (v -> new HashMap<>())
        ).put(order.getId(), order);
    }

    @Override
    public Stream<DeliveryOrderEntity> getDeliveryOrdersPeriod(LocalDate from, LocalDate to) {
        return ordersByPeriod(from, to, deliveryOrders);
    }

    @Override
    public Stream<UserOrderEntity> getUserOrdersPeriod(LocalDate from, LocalDate to) {
        return ordersByPeriod(from, to, userOrdersByDate);
    }

    @Override
    public Stream<UserOrderEntity> getUserOrdersPeriodByStatus(LocalDate from, LocalDate to, UserOrderStatus status) {
        requireNonNull(status);
        return getUserOrdersPeriod(from, to)
                .filter((userOrderEntity -> userOrderEntity.getStatus() == status));
    }

    private <T> Stream<T> ordersByPeriod(LocalDate from, LocalDate to, TreeMap<LocalDate, HashMap<UUID, T>> map) {
        if (Objects.requireNonNull(from).isAfter(Objects.requireNonNull(to))) {
            throw new RuntimeException("Wrong date format");
        }
        return map.subMap(from, true, to, true).values().stream()
                .flatMap(m -> m.values().stream());
    }

    @Override
    public UUID createUserOrder(LocalDateTime orderTime) {
        UUID orderUUID = UUID.randomUUID();
        UserOrderEntity userOrder = new UserOrderEntity(orderUUID, orderTime, new ArrayList<>(), UserOrderStatus.NEW);
        userOrdersByDate.computeIfAbsent(orderTime.toLocalDate(), k -> new HashMap<>()).put(orderUUID, userOrder);
        userOrdersByID.put(orderUUID, userOrder);
        return orderUUID;
    }

    @Override
    public void updateUserOrder(UUID id, List<MenuItemEntity> items) {
        UserOrderEntity old = userOrdersByID.get(id);
        List<MenuItemEntity> menuItems = new ArrayList<>(old.getMenuItems());
        menuItems.addAll(items);
        UserOrderEntity newUserOrder = new UserOrderEntity(id, old.getDate(), menuItems, UserOrderStatus.PROGRESS);
        userOrdersByID.put(id, newUserOrder);
        userOrdersByDate.get(newUserOrder.getDate().toLocalDate()).put(newUserOrder.getId(), newUserOrder);
    }

    @Override
    public UserOrderEntity getOrderById(UUID id) {
        return userOrdersByID.get(Objects.requireNonNull(id));
    }

    @Override
    public void closeOrder(UUID id) {
        UserOrderEntity oldUserOrder = userOrdersByID.get(id);
        UserOrderEntity newUserOrder = new UserOrderEntity(
                oldUserOrder.getId(),
                oldUserOrder.getDate(),
                oldUserOrder.getMenuItems(),
                UserOrderStatus.DONE
        );
        userOrdersByID.put(id, newUserOrder);
        userOrdersByDate.get(newUserOrder.getDate().toLocalDate()).put(newUserOrder.getId(), newUserOrder);
    }
}
