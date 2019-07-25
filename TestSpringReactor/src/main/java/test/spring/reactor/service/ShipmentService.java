package test.spring.reactor.service;

import test.spring.reactor.model.Shipment;

public interface ShipmentService {
    void updateShipmentAddress(Shipment shipment);
}
