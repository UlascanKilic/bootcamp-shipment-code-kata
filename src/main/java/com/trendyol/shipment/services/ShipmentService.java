package com.trendyol.shipment.services;

import com.trendyol.shipment.Product;
import com.trendyol.shipment.ShipmentSize;
import com.trendyol.shipment.exceptions.EmptyBasketException;
import utils.Constants;
import utils.exceptionMessages.ShipmentExceptionMessage;


import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The {@code ShipmentService} class provides methods for calculating the shipment size
 * based on the products in a basket.
 */
public class ShipmentService implements IShipmentService {

    private final List<Product> productList;

    public ShipmentService(List<Product> productList) {
        this.productList = productList;
    }

    /**
     * Calculates the shipment size based on the products in the basket.
     *
     * @return the calculated shipment size
     * @throws EmptyBasketException if the basket is empty
     */
    @Override
    public ShipmentSize calculateShipmentSize() {
        return findShipmentSize(getReverseOrderedProductsBySize());
    }


    /**
     * Groups the products by shipment size in reverse order.
     *
     * @return a map of shipment sizes to their counts, ordered in reverse
     * @throws EmptyBasketException if the basket is empty
     */
    private Map<ShipmentSize, Long> getReverseOrderedProductsBySize() {

        if(productList.isEmpty()) throw new EmptyBasketException(ShipmentExceptionMessage.BASKET_IS_EMPTY.getMessage());

        return productList.stream()
                .collect(Collectors.groupingBy(
                        Product::getSize,
                        () -> new TreeMap<>(Comparator.reverseOrder()), //For edge case like -> S S S M M M L
                        Collectors.counting()
                ));
    }

    /**
     * Finds the shipment size based on the counts of products by size.
     *
     * @param sizeCounts a map of shipment sizes to their counts
     * @return the determined shipment size
     */
    private ShipmentSize findShipmentSize(Map<ShipmentSize, Long> sizeCounts) {

        if (sizeCounts.getOrDefault(ShipmentSize.X_LARGE, Constants.DEFAULT_VALUE) >= Constants.THRESHOLD_VALUE) {
            return ShipmentSize.X_LARGE;
        }

        Optional<Map.Entry<ShipmentSize, Long>> maxSizeEntry = sizeCounts.entrySet()
                .stream()
                .filter(entry -> entry.getValue() >= Constants.THRESHOLD_VALUE)
                .max(Map.Entry.comparingByKey());

        return maxSizeEntry.isPresent() ? maxSizeEntry.get().getKey().getLarger() :
                Collections.max(sizeCounts.keySet());
    }
}
