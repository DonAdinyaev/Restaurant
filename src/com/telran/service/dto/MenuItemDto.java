package com.telran.service.dto;

import java.util.Objects;
import java.util.Set;

public class MenuItemDto {
    private final String name;
    private final double price;
    private final String category;
    private final Set<IngredientDto> ingredients;

    private MenuItemDto(String name, double price, String category, Set<IngredientDto> ingredients) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.ingredients = ingredients;
    }

    public static MenuItemDto of(String name, double price, String category, Set<IngredientDto> ingredients){
        return new MenuItemDto(Objects.requireNonNull(name),price,Objects.requireNonNull(category),Objects.requireNonNull(ingredients));
    }

    public static MenuItemDto ofSoupCategory(String name, double price, Set<IngredientDto> ingredients){
        return of(Objects.requireNonNull(name),price,"Soup",Objects.requireNonNull(ingredients));
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

    public Set<IngredientDto> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItemDto that = (MenuItemDto) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "MenuItemDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
