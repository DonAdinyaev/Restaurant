package com.telran.service.admin;

import com.telran.dao.crm.CRMImpl;
import com.telran.dao.entity.IngredientEntity;
import com.telran.dao.entity.MenuItemEntity;
import com.telran.dao.entity.ProductUnit;
import com.telran.dao.menu.MenuImpl;
import com.telran.dao.shop.ShopImpl;
import com.telran.dao.store.StoreImpl;
import com.telran.service.dto.IngredientDto;
import com.telran.service.dto.MenuItemDto;
import com.telran.service.dto.ProductUnitDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

class AdminServiceImplTest {
    AdminServiceImpl admin;
    MenuItemEntity omelette;
    MenuItemDto omeletteDto;

    @BeforeEach
    void setUp() {
        admin = new AdminServiceImpl(
                new ShopImpl(), new StoreImpl(), new CRMImpl(), new MenuImpl());
        omelette = new MenuItemEntity(
                "Omelette", 25, "Breakfast", Set.of(
                        new IngredientEntity("Egg", 3, ProductUnit.UNIT),
                        new IngredientEntity("Oil", 0.1, ProductUnit.LITER),
                        new IngredientEntity("Milk", 0.3, ProductUnit.LITER)));
        omeletteDto = MenuItemDto.of(
                "Omelette", 25, "Breakfast", Set.of(
                       IngredientDto.of("Egg", 3, ProductUnitDto.UNIT),
                       IngredientDto.of("Oil", 0.1, ProductUnitDto.LITER),
                       IngredientDto.of("Milk", 0.3, ProductUnitDto.LITER)));
    }

    @Test
    void addMenuItem_MenuItem_Pass() {
        admin.addMenuItem(omeletteDto.getName(), omeletteDto.getPrice(), omeletteDto.getCategory(), omeletteDto.getIngredients());
        assertEquals(omeletteDto, admin.getMenu().filter(item -> item.getName().equals("Omelette")).findAny().orElse(null));
    }

    @Test
    void updateMenuItem() {
    }

    @Test
    void deleteMenuItem() {
    }

    @Test
    void getMenu() {
    }

    @Test
    void makeProductDelivery() {
    }

    @Test
    void getStoreStatus() {
    }

    @Test
    void getReport() {
    }

    @Test
    void testGetReport() {
    }
}