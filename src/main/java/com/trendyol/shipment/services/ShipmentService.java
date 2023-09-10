package com.trendyol.shipment.services;

import com.trendyol.shipment.Product;
import com.trendyol.shipment.ShipmentSize;
import utils.Constants;


import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collections;
import java.util.stream.Collectors;

public class ShipmentService implements IShipmentService {

    private final List<Product> productList;

    public ShipmentService(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public ShipmentSize getShipmentSize() {
        Map<ShipmentSize, Long> sizeCounts = getProductsReverseOrderedBySize();

        if (sizeCounts.getOrDefault(ShipmentSize.X_LARGE, Constants.DEFAULT_VALUE) >= Constants.THRESHOLD_VALUE) {
            return ShipmentSize.X_LARGE;
        }

        return findShipmentSize(sizeCounts);
    }

    @Override
    public Map<ShipmentSize, Long> getProductsReverseOrderedBySize() {
        return productList.stream()
                .collect(Collectors.groupingBy(
                        Product::getSize,
                        () -> new TreeMap<>(Comparator.reverseOrder()), //For edge case like -> S S S M M M L
                        Collectors.counting()
                ));
    }

    @Override
    public ShipmentSize findShipmentSize(Map<ShipmentSize, Long> sizeCounts) {
        for (Map.Entry<ShipmentSize, Long> entry : sizeCounts.entrySet()) {
            if (entry.getValue() >= Constants.THRESHOLD_VALUE) {
                return entry.getKey().getLarger();
            }
        }
        return Collections.max(sizeCounts.keySet());
    }
}
