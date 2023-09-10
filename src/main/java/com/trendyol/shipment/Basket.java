package com.trendyol.shipment;


import com.trendyol.shipment.services.ShipmentService;
import java.util.List;

public class Basket {
    private List<Product> products;

    public ShipmentSize getShipmentSize() {
        return new ShipmentService(products).getShipmentSize();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
