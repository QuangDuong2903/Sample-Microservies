package com.quangduong.inventoryservice.saga;

import com.quangduong.inventoryservice.saga.handler.InventoryCommandHandler;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlerDispatcherRegister {
    @Bean
    public CommandDispatcher commandDispatcher(InventoryCommandHandler target,
                                               SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {
        return sagaCommandDispatcherFactory
                .make("inventoryCommandHandlerDispatcher", target.commandHandlersDefinitions());
    }
}
