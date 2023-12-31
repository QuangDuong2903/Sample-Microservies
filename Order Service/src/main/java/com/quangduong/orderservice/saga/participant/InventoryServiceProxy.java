package com.quangduong.orderservice.saga.participant;

import com.quangduong.sagas.command.RestoreQuantityCommand;
import com.quangduong.sagas.command.SubtractQuantityCommand;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder;
import org.springframework.stereotype.Component;

@Component
public class InventoryServiceProxy {

    public final CommandWithDestination subtractQuantity(Long productId) {
        return CommandWithDestinationBuilder.send(new SubtractQuantityCommand(productId))
                .to("inventoryService")
                .build();
    }

    public final CommandWithDestination restoreQuantity(Long productId) {
        return CommandWithDestinationBuilder.send(new RestoreQuantityCommand(productId))
                .to("inventoryService")
                .build();
    }

}
