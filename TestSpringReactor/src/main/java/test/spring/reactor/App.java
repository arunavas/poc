package test.spring.reactor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.bus.Event;
import reactor.bus.EventBus;
import test.spring.reactor.handler.ShipmentEventHandler;
import test.spring.reactor.model.Shipment;

import static reactor.bus.selector.Selectors.$;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private EventBus eventBus;

    @Autowired
    private ShipmentEventHandler shipmentEventHandler;

    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        eventBus.on($("shipment"), shipmentEventHandler);
        for (int i = 0; i < 10; i++) {
            Shipment s = new Shipment();
            s.setId(i + 1);
            System.out.println("Publishing Shipment Id: " + s.getId());
            eventBus.notify("shipment", Event.wrap(s));
        }
    }
}
