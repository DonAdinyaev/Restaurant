package com.telran.service.dto;

import java.util.Objects;

public class IngredientDto {
    private final String name;
    private final double count;
    private final ProductUnitDto unit;

    private IngredientDto(String name, double count, ProductUnitDto unit) {
        this.name = name;
        this.count = count;
        this.unit = unit;
    }

    private static IngredientDto of(String name, double count, ProductUnitDto unit){
        return new IngredientDto(Objects.requireNonNull(name),count,Objects.requireNonNull(unit));
    }

    public String getName() {
        return name;
    }

    public double getCount() {
        return count;
    }

    public ProductUnitDto getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientDto that = (IngredientDto) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "IngredientDto{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", unit=" + unit +
                '}';
    }
}
