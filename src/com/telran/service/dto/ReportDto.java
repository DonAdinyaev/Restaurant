package com.telran.service.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ReportDto {
    public enum Type{
        Delivery, Order
    }

    private final String id;
    private final Type type;
    private final LocalDateTime date;
    private final List<String> items;
    private final double totalAmount;

    private ReportDto(String id, Type type, LocalDateTime date, List<String> items, double totalAmount) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public static ReportDto of(String id, Type type, LocalDateTime date, List<String> items, double totalAmount){
        return new ReportDto(
                Objects.requireNonNull(id),
                Objects.requireNonNull(type),
                Objects.requireNonNull(date),
                Objects.requireNonNull(items),
                totalAmount
        );
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public List<String> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportDto reportDto = (ReportDto) o;
        return id.equals(reportDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ReportDto{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", date=" + date +
                ", items=" + items +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
