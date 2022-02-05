package com.telran.dao.store;

import com.telran.dao.entity.ProductEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class StoreImpl implements Store {
    private final Map<String, ProductEntity> productsByName;

    public StoreImpl() {
        productsByName = new HashMap<>();
    }

    @Override
    public void addToStore(ProductEntity product) {
        productsByName.merge(
                product.getName(),
                requireNonNull(product),
                (p1, p2) -> new ProductEntity(
                        p2.getName(),
                        Double.sum(p1.getCount(), p2.getCount()),
                        p2.getUnit(),
                        p2.getPricePerUnit()));
    }

    @Override
    public void addToStore(Set<ProductEntity> products) {
        products.forEach(this::addToStore);
    }

    @Override
    public ProductEntity getProductByName(String name) {
        return productsByName.get(requireNonNull(name));
    }

    @Override
    public Stream<ProductEntity> getStoreStatus() {
        return productsByName.values().stream();
    }
}
