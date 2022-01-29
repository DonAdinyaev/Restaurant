package com.telran.dao.entity;

import java.util.Objects;
import java.util.Set;

public class MenuItemEntity {
    private final String name;
    private final double price;
    private final String category;
    private final Set<IngredientEntity> ingredients;

    public MenuItemEntity(String name, double price, String category, Set<IngredientEntity> ingredients) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public Set<IngredientEntity> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItemEntity that = (MenuItemEntity) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "MenuItemEntity{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
