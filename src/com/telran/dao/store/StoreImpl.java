package com.telran.dao.store;

import com.telran.dao.entity.ProductEntity;
import com.telran.dao.security.RolesAllowed;

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
    @RolesAllowed("admin")
    public void addToStore(ProductEntity product) {
        if (productsByName.containsKey(requireNonNull(product).getName())) {
            ProductEntity removed = productsByName.remove(product.getName()); //
            productsByName.put(product.getName(),
                    new ProductEntity(
                            product.getName(),
                            Double.sum(removed.getCount(), product.getCount()),
                            product.getUnit(),
                            product.getPricePerUnit()));
        } else productsByName.put(requireNonNull(product).getName(), product);
    }

    @Override
    @RolesAllowed("admin")
    public void addToStore(Set<ProductEntity> products) {
        products.forEach(this::addToStore);
    }

    @Override
    @RolesAllowed("admin")
    public ProductEntity getProductByName(String name) {
        return productsByName.get(requireNonNull(name));
    }

    @Override
    @RolesAllowed("admin")
    public Stream<ProductEntity> getStoreStatus() {
        return productsByName.values().stream();
    }
}
