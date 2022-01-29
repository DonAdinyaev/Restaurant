package com.telran.dao.crm;

import com.telran.dao.entity.DeliveryOrderEntity;
import com.telran.dao.entity.MenuItemEntity;
import com.telran.dao.entity.UserOrderEntity;
import com.telran.dao.entity.UserOrderStatus;
import com.telran.dao.security.RolesAllowed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class CRMImpl implements CRM {
    private final TreeMap<LocalDateTime, List<DeliveryOrderEntity>> deliveryOrders;
    private final TreeMap<LocalDateTime, List<UserOrderEntity>> userOrdersByDate;
    private final Map<UUID, UserOrderEntity> userOrdersByUUID;

    public CRMImpl() {
        this.deliveryOrders = new TreeMap<>();
        this.userOrdersByDate = new TreeMap<>();
        this.userOrdersByUUID = new HashMap<>();
    }

    @Override
    @RolesAllowed("admin")
    public void addDeliveryOrder(DeliveryOrderEntity order) {
        deliveryOrders.computeIfAbsent(requireNonNull(order.getDate()), (v -> new ArrayList<>())).add(order);
    }

    @Override
    @RolesAllowed("admin")
    public Stream<DeliveryOrderEntity> getDeliveryOrderPeriod(LocalDate from, LocalDate to) {
        return deliveryOrders.subMap(requireNonNull(from.atStartOfDay()), true,
                requireNonNull(to.atStartOfDay()), false).values().stream()
                .flatMap(Collection::stream);
    }

    @Override
    @RolesAllowed("admin")
    public Stream<UserOrderEntity> getUserOrdersPeriod(LocalDate from, LocalDate to) {
        return userOrdersByDate.subMap(requireNonNull(from).atStartOfDay(), true,
                requireNonNull(to).atStartOfDay(), false).values().stream()
                .flatMap(Collection::stream);
    }

    @Override
    @RolesAllowed("admin")
    public Stream<UserOrderEntity> getUserOrdersPeriodByStatus(LocalDate from, LocalDate to, UserOrderStatus status) {
        requireNonNull(status);
        return userOrdersByDate.subMap(requireNonNull(from).atStartOfDay(), true,
                        requireNonNull(to).atStartOfDay(), false).values().stream()
                .flatMap(Collection::stream)
                .filter((userOrderEntity -> userOrderEntity.getStatus() == status));
    }

    @Override
    public UUID createUserOrder() {
        LocalDateTime orderTime = LocalDateTime.now();
        UUID orderUUID = UUID.randomUUID();
        UserOrderEntity userOrder = new UserOrderEntity(orderUUID, orderTime, new ArrayList<>(), UserOrderStatus.NEW);
        userOrdersByDate.computeIfAbsent(orderTime, v -> new ArrayList<>()).add(userOrder);
        userOrdersByUUID.put(orderUUID, userOrder);
        return orderUUID;
    }

    @Override
    public void updateUserOrder(UUID id, List<MenuItemEntity> items) {
        userOrdersByUUID.get(id).getMenuItems().addAll(Objects.requireNonNull(items));
        userOrdersByUUID.get(id).changeStatus(UserOrderStatus.PROGRESS);
    }

    @Override
    public UserOrderEntity getOrderById(UUID id) {
        return userOrdersByUUID.get(Objects.requireNonNull(id));
    }

    @Override
    public void closeOrder(UUID id) {
        userOrdersByUUID.get(Objects.requireNonNull(id)).changeStatus(UserOrderStatus.DONE);
    }
}
