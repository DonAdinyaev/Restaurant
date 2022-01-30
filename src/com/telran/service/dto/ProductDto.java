package com.telran.service.dto;

import java.util.Objects;

public class ProductDto {
    private final String name;
    private final double count;
    private final ProductUnitDto unit;

    private ProductDto(String name, double count, ProductUnitDto unit) {
        this.name = name;
        this.count = count;
        this.unit = unit;
    }

    public static ProductDto of(String name, double count, ProductUnitDto unit){
        return new ProductDto(Objects.requireNonNull(name),count,Objects.requireNonNull(unit));
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
        ProductDto that = (ProductDto) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", unit=" + unit +
                '}';
    }
}
