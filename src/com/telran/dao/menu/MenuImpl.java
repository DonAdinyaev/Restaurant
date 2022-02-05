package com.telran.dao.menu;

import com.telran.dao.entity.MenuItemEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

public class MenuImpl implements Menu {
    private final Map<String, MenuItemEntity> menu;

    public MenuImpl() {
        menu = new HashMap<>();
    }

    @Override
    public void addMenuItem(MenuItemEntity item) {
        menu.put(Objects.requireNonNull(item).getName(), item);
    }

    @Override
    public void updateMenuItem(MenuItemEntity item) {
        if (!menu.containsKey(Objects.requireNonNull(item).getName()))
            throw new NoSuchElementException("Item " + item.getName() + " was not found in the menu.");
        menu.put(item.getName(), item);
    }

    @Override
    public void deleteMenuItem(String name) {
        if (!menu.containsKey(name))
            throw new NoSuchElementException("Item " + name + " was not found in the menu.");
        menu.remove(name);
    }

    @Override
    public MenuItemEntity getMenuItem(String name) {
        return menu.get(name);
    }

    @Override
    public Stream<MenuItemEntity> getAll() {
        return menu.values().stream();
    }

    @Override
    public Stream<MenuItemEntity> getByCategory(String category) {
        Objects.requireNonNull(category);
        return menu.values().stream()
                .filter(menuEntity -> category.equals(menuEntity.getCategory()));
    }
}
