package com.telran.service.user;

import com.telran.service.dto.OrderItemDto;
import com.telran.service.dto.UserMenuDto;

import java.util.Map;
import java.util.stream.Stream;

public interface UserService {
    UserMenuDto getMenu();
    void makeOrder(String orderId, Map<String,Integer> menuItems);
    Stream<OrderItemDto> getOrderStatus(String orderId);
    void closeOrder(String orderId);
}
