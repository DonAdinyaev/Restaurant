package com.telran.service.dto;

import java.util.Objects;

public class OrderItemDto {
    private final String name;
    private final double price;
    private final int count;


    private OrderItemDto(String name, double price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public static OrderItemDto of(String name, double price, int count){
        return new OrderItemDto(Objects.requireNonNull(name),price,count);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemDto that = (OrderItemDto) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    @Override
    public String toString() {
        return "OrderItemDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
