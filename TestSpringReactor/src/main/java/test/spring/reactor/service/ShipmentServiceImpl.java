package test.spring.reactor.service;

import org.springframework.stereotype.Service;
import test.spring.reactor.model.Shipment;

@Service
public class ShipmentServiceImpl implements ShipmentService {
    @Override
    public void updateShipmentAddress(Shipment shipment) {
        System.out.println("Updating Current Address for Shipment Id: " + shipment.getId());
        try {
            Thread.sleep(3000);
            System.out.println("Updated Current Address for Shipment Id: " + shipment.getId());
        } catch (Exception ex) {
            System.out.println("Failed to update current address for shipment id: " + shipment.getId());
        }
    }
}
