package com.telran.service.dto;

import java.util.Objects;
import java.util.Set;

public class UserMenuDto {
    private final String orderId;
    private final Set<OrderItemDto> menu;


    private UserMenuDto(String orderId, Set<OrderItemDto> menu) {
        this.orderId = orderId;
        this.menu = menu;
    }

    public static UserMenuDto of(String orderId, Set<OrderItemDto> menu) {
        return new UserMenuDto(Objects.requireNonNull(orderId), Objects.requireNonNull(menu));
    }

    public String getOrderId() {
        return orderId;
    }

    public Set<OrderItemDto> getMenu() {
        return menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMenuDto that = (UserMenuDto) o;
        return orderId.equals(that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "UserMenuDto{" +
                "orderId='" + orderId + '\'' +
                ", menu=" + menu +
                '}';
    }
}
