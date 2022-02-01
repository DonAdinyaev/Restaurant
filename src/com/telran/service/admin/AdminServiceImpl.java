package com.telran.service.admin;

import com.telran.dao.crm.CRM;
import com.telran.dao.entity.*;
import com.telran.dao.menu.Menu;
import com.telran.dao.shop.Shop;
import com.telran.dao.store.Store;
import com.telran.service.dto.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

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
        menu.addMenuItem(menuItemEntityOf(name, price, category, ingredients));
    }

    @Override
    public void updateMenuItem(String name, double price, String category, Set<IngredientDto> ingredients) {
        menu.updateMenuItem(menuItemEntityOf(name, price, category, ingredients));
    }

    @Override
    public void deleteMenuItem(String name) {
        menu.deleteMenuItem(name);
    }

    @Override
    public Stream<MenuItemDto> getMenu() {
        return menu.getAll()
                .map(m -> menuItemDtoOf(m.getName(),
                        m.getPrice(),
                        m.getCategory(),
                        m.getIngredients()));
    }

    @Override
    public void makeProductDelivery(Map<String, Double> products) {
        Set<ProductEntity> setOfProductEntities = products.entrySet().stream()
                .collect(HashSet::new,
                        (set, entry) -> set.add(productEntityOf(entry.getKey(), entry.getValue())),
                        HashSet::addAll);
        crm.addDeliveryOrder(new DeliveryOrderEntity(LocalDateTime.now(), setOfProductEntities));
    }

    private ProductEntity productEntityOf(String name, double count) {
        if (count < 0) throw new IllegalArgumentException("Product count should not be negative.");
        ProductEntity fromShop = shop.getProductByName(name);
        if (requireNonNull(fromShop).getCount() < count)
            throw new RuntimeException(
                    String.format("Delivery creation failure. Shop has only %.1f %s of %s",
                            fromShop.getCount(), fromShop.getUnit().name(), name));
        return new ProductEntity(
                fromShop.getName(),
                count,
                fromShop.getUnit(),
                fromShop.getPricePerUnit()
        );
    }

    @Override
    public Stream<ProductDto> getStoreStatus() {
        return store.getStoreStatus()
                .map(this::productEntityToDto);
    }

    private ProductDto productEntityToDto(ProductEntity entity) {
        return ProductDto.of(entity.getName(), entity.getCount(), ProductUnitDto.valueOf(entity.getUnit().name()));
    }

    @Override
    public Stream<ReportDto> getReport(LocalDate from, LocalDate to) {
        Stream<ReportDto> report = Stream.empty();
        for (ReportDto.Type type : ReportDto.Type.values())
            report = Stream.concat(report, getReport(from, to, type));
        return report;
    }

    @Override
    public Stream<ReportDto> getReport(LocalDate from, LocalDate to, ReportDto.Type type) {
        Stream<ReportDto> report;
        switch (type) {
            case Delivery -> report = crm.getDeliveryOrdersPeriod(from, to).map(this::deliveryToReportDto);
            case Order -> report = crm.getUserOrdersPeriod(from, to).map(this::orderToReportDto);
            default -> throw new IllegalArgumentException(String.format("%s is not an existing type", type.name()));
        }
        return report;
    }

    private ReportDto orderToReportDto(UserOrderEntity order) {
        return ReportDto.of(
            order.getId().toString(),
            ReportDto.Type.Delivery,
            order.getDate(),
            order.getMenuItems().stream()
                    .map(MenuItemEntity::getName)
                    .collect(Collectors.toList()),
            order.getMenuItems().stream()
                    .mapToDouble(MenuItemEntity::getPrice)
                    .reduce(0, Double::sum));
    }

    private ReportDto deliveryToReportDto(DeliveryOrderEntity order) {
        return ReportDto.of(
                order.getId().toString(),
                ReportDto.Type.Delivery,
                order.getDate(),
                order.getProducts().stream()
                        .map(ProductEntity::getName)
                        .collect(Collectors.toList()),
                order.getProducts().stream()
                        .mapToDouble(ProductEntity::getCount)
                        .reduce(0, Double::sum));
    }

    private IngredientEntity ingredientDtoToEntity(IngredientDto dto) {
        return new IngredientEntity(dto.getName(), dto.getCount(), ProductUnit.valueOf(dto.getUnit().name()));
    }

    private MenuItemEntity menuItemEntityOf(String name, double price, String category, Set<IngredientDto> ingredients) {
        return new MenuItemEntity(
                requireNonNull(name),
                price,
                requireNonNull(category),
                requireNonNull(ingredients).stream()
                        .map(this::ingredientDtoToEntity)
                        .collect(Collectors.toSet()));
    }

    private IngredientDto ingredientEntityToDTO(IngredientEntity entity) {
        return IngredientDto.of(entity.getName(), entity.getCount(), ProductUnitDto.valueOf(entity.getUnit().name()));
    }

    private MenuItemDto menuItemDtoOf(String name, double price, String category, Set<IngredientEntity> ingredients) {
        return MenuItemDto.of(
                requireNonNull(name),
                price,
                requireNonNull(category),
                requireNonNull(ingredients).stream()
                        .map(this::ingredientEntityToDTO)
                        .collect(Collectors.toSet()));
    }
}
