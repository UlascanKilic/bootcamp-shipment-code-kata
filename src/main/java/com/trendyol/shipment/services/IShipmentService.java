package com.trendyol.shipment.services;

import com.trendyol.shipment.ShipmentSize;

@FunctionalInterface
public interface IShipmentService {
        ShipmentSize getShipmentSize();
}
