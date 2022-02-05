package com.telran.dao.menu;

import com.telran.dao.entity.MenuItemEntity;

import java.util.stream.Stream;

public interface Menu {
    void addMenuItem(MenuItemEntity item);

    void updateMenuItem(MenuItemEntity item);

    void deleteMenuItem(String name);

    MenuItemEntity getMenuItem(String name);

    Stream<MenuItemEntity> getAll();

    Stream<MenuItemEntity> getByCategory(String category);
}
