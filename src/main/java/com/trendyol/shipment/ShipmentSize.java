package com.trendyol.shipment;

public enum ShipmentSize {

    SMALL,
    MEDIUM,
    LARGE,
    X_LARGE;

    public ShipmentSize getLarger(){
        return this == ShipmentSize.X_LARGE ? ShipmentSize.X_LARGE : values()[this.ordinal() + 1];
    }
}
