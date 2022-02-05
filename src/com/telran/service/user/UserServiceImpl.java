package com.telran.service.user;

import com.telran.dao.crm.CRM;
import com.telran.dao.entity.MenuItemEntity;
import com.telran.dao.menu.Menu;
import com.telran.service.dto.OrderItemDto;
import com.telran.service.dto.UserMenuDto;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class UserServiceImpl implements UserService {
    private final CRM crm;
    private final Menu menu;

    public UserServiceImpl(CRM crm, Menu menu) {
        this.crm = crm;
        this.menu = menu;
    }

    @Override
    public UserMenuDto getMenu() {
        return UserMenuDto.of(String.valueOf(crm.createUserOrder(LocalDateTime.now())),Set.of());
    }

    @Override
    public void makeOrder(String orderId, Map<String, Integer> menuItems) {
        List<MenuItemEntity> filteredProducts = menuItems.entrySet().stream()
                .collect(filtering(
                                (Map.Entry<String, Integer> e) -> Objects.nonNull(menu.getMenuItem(e.getKey())),
                                mapping(
                                        (Map.Entry<String, Integer> e) -> menu.getMenuItem(e.getKey()),
                                        toList())));
        crm.updateUserOrder(UUID.fromString(orderId), filteredProducts);
    }

    @Override
    public Stream<OrderItemDto> getOrderStatus(String orderId) {
        Map<String, OrderItemDto> ordersMap = crm.getOrderById(UUID.fromString(orderId)).getMenuItems().stream()
                .collect(HashMap::new,
                        (map, item) -> map.put(item.getName(),
                                OrderItemDto.of(item.getName(), item.getPrice(), 1)),
                        (map1, map2) -> map2.forEach((name, item) -> map1.merge(
                                name, item, (i1, i2) -> OrderItemDto.of(
                                        name, i1.getPrice(), i1.getCount() + i2.getCount()))));
        return ordersMap.values().stream();
    }

    @Override
    public void closeOrder(String orderId) {
        crm.closeOrder(UUID.fromString(orderId));
    }
}
