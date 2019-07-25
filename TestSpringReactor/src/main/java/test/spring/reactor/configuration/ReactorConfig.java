package test.spring.reactor.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.Environment;
import reactor.bus.EventBus;
import reactor.core.config.DispatcherType;

@Configuration
public class ReactorConfig {
    @Bean
    Environment env() {
        return Environment.initializeIfEmpty()
                .setDispatcher("customDispatcher1", Environment.newDispatcher(5, 5, DispatcherType.THREAD_POOL_EXECUTOR))
                .setDispatcher("customDispatcher2", Environment.newDispatcher(9, 9, DispatcherType.THREAD_POOL_EXECUTOR))
                .assignErrorJournal();
    }

    @Bean
    EventBus createEventBus(Environment env) {
        return EventBus.create(env, env.getDispatcher("customDispatcher2"));
    }
}
