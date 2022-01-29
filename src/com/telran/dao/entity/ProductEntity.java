package com.telran.dao.entity;

import java.util.Objects;

public class ProductEntity {
    private final String name;
    private final double count;
    private final ProductUnit unit;
    private final double pricePerUnit;

    public ProductEntity(String name, double count, ProductUnit unit, double pricePerUnit) {
        this.name = name;
        this.count = count;
        this.unit = unit;
        this.pricePerUnit = pricePerUnit;
    }

    public String getName() {
        return name;
    }

    public double getCount() {
        return count;
    }

    public ProductUnit getUnit() {
        return unit;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", unit=" + unit +
                ", pricePerUnit=" + pricePerUnit +
                '}';
    }
}
