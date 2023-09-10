package com.trendyol.shipment.services;

import com.trendyol.shipment.ShipmentSize;

import java.util.Map;

public interface IShipmentService {

    Map<ShipmentSize, Long> getProductsReverseOrderedBySize();

    ShipmentSize findShipmentSize(Map<ShipmentSize, Long> sizeCounts);

    ShipmentSize getShipmentSize();
}
