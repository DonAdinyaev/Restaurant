package com.telran.service.admin;

import com.telran.dao.crm.CRM;
import com.telran.dao.entity.IngredientEntity;
import com.telran.dao.entity.MenuItemEntity;
import com.telran.dao.entity.ProductUnit;
import com.telran.dao.menu.Menu;
import com.telran.dao.shop.Shop;
import com.telran.dao.shop.ShopImpl;
import com.telran.dao.store.Store;
import com.telran.service.dto.IngredientDto;
import com.telran.service.dto.MenuItemDto;
import com.telran.service.dto.ProductDto;
import com.telran.service.dto.ReportDto;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdminServiceImpl implements AdminService {
    private final Shop shop;
    private final Store store;
    private final CRM crm;
    private final Menu menu;

    public AdminServiceImpl(Shop shop, Store store, CRM crm, Menu menu) {
        this.shop = shop;
        this.store = store;
        this.crm = crm;
        this.menu = menu;
    }

    @Override
    public void addMenuItem(String name, double price, String category, Set<IngredientDto> ingredients) {
        menu.addMenuItem(new MenuItemEntity(
                Objects.requireNonNull(name),
                price,
                Objects.requireNonNull(category),
                Objects.requireNonNull(ingredients).stream()
                        .map(this::map)
                        .collect(Collectors.toSet())
        ));
    }

    @Override
    public void updateMenuItem(String name, double price, String category, Set<IngredientDto> ingredients) {

    }

    @Override
    public void deleteMenuItem(String name) {

    }

    @Override
    public Stream<MenuItemDto> getMenu() {
        return null;
    }

    @Override
    public void makeProductDelivery(Map<String, Double> products) {

    }

    @Override
    public Stream<ProductDto> getStoreStatus() {
        return null;
    }

    @Override
    public Stream<ReportDto> getReport(LocalDate from, LocalDate to) {
        return null;
    }

    @Override
    public Stream<ReportDto> getReport(LocalDate from, LocalDate to, ReportDto.Type type) {
        return null;
    }

    private IngredientEntity map(IngredientDto dto){
        return new IngredientEntity(dto.getName(),dto.getCount(), ProductUnit.valueOf(dto.getUnit().name()));
    }
}
