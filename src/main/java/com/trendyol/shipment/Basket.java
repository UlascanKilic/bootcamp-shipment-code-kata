package com.trendyol.shipment;

import utils.Constants;

import java.util.*;
import java.util.stream.Collectors;

public class Basket {
    private List<Product> products;

    public ShipmentSize getShipmentSize() {
        Map<ShipmentSize, Long> sizeCounts = getProductsReverseOrderedBySize();

        if (sizeCounts.getOrDefault(ShipmentSize.X_LARGE, 0L) >= Constants.THRESHOLD_VALUE) {
            return ShipmentSize.X_LARGE;
        }

        return findShipmentSize(sizeCounts);

    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    private Map<ShipmentSize, Long> getProductsReverseOrderedBySize(){
        return getProducts().stream()
                .collect(Collectors.groupingBy(
                        Product::getSize,
                        () -> new TreeMap<>(Comparator.reverseOrder()), //For edge case like -> S S S M M M L
                        Collectors.counting()
                ));
    }

    private ShipmentSize findShipmentSize(Map<ShipmentSize, Long> sizeCounts) {
        for (Map.Entry<ShipmentSize, Long> entry : sizeCounts.entrySet()) {
            if (entry.getValue() >= Constants.THRESHOLD_VALUE) {
                return entry.getKey().getLarger();
            }
        }
        return Collections.max(sizeCounts.keySet());
    }
}
