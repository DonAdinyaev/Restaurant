package com.telran.dao.entity;

import java.util.Objects;

public class IngredientEntity {
    private final String name;
    private final double count;
    private final ProductUnit unit;

    public IngredientEntity(String name, double count, ProductUnit unit) {
        this.name = name;
        this.count = count;
        this.unit = unit;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientEntity that = (IngredientEntity) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "IngredientEntity{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", unit=" + unit +
                '}';
    }
}
