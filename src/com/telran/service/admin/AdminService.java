package com.telran.service.admin;

import com.telran.service.dto.IngredientDto;
import com.telran.service.dto.MenuItemDto;
import com.telran.service.dto.ProductDto;
import com.telran.service.dto.ReportDto;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public interface AdminService {
    void addMenuItem(String name, double price, String category, Set<IngredientDto> ingredients);
    void updateMenuItem(String name, double price, String category, Set<IngredientDto> ingredients);
    void deleteMenuItem(String name);
    Stream<MenuItemDto> getMenu();
    void makeProductDelivery(Map<String,Double> products);
    Stream<ProductDto> getStoreStatus();
    Stream<ReportDto> getReport(LocalDate from, LocalDate to);
    Stream<ReportDto> getReport(LocalDate from, LocalDate to, ReportDto.Type type);
}
