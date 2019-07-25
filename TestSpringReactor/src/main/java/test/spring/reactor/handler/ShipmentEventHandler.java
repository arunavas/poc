package test.spring.reactor.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;
import test.spring.reactor.model.Shipment;
import test.spring.reactor.service.ShipmentService;

@Service
public class ShipmentEventHandler implements Consumer<Event<Shipment>> {
    @Autowired
    private ShipmentService shipmentService;

    @Override
    public void accept(Event<Shipment> shipmentEvent) {
        Shipment shipment = shipmentEvent.getData();
        System.out.println("Event Handler Received one shipment with id: " + shipment.getId() + ". Forwarding to ShipmentService...");
        shipmentService.updateShipmentAddress(shipment);
    }
}
