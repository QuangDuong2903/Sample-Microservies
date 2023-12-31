package com.quangduong.inventoryservice.saga.handler;

import com.quangduong.inventoryservice.service.InventoryService;
import com.quangduong.sagas.command.RestoreQuantityCommand;
import com.quangduong.sagas.command.SubtractQuantityCommand;
import com.quangduong.sagas.reply.RestoreQuantitySuccess;
import com.quangduong.sagas.reply.SubtractQuantitySuccess;
import io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryCommandHandler {

    private final InventoryService inventoryService;

    public CommandHandlers commandHandlersDefinitions() {
        return SagaCommandHandlersBuilder
                .fromChannel("inventoryService")
                .onMessage(SubtractQuantityCommand.class, this::subtractQuantity)
                .onMessage(RestoreQuantityCommand.class, this::restoreQuantity)
                .build();
    }

    private Message subtractQuantity(CommandMessage<SubtractQuantityCommand> commandMessage) {
        SubtractQuantityCommand command = commandMessage.getCommand();
        inventoryService.subtractQuantity(command.productId());
        return CommandHandlerReplyBuilder.withSuccess(
            new SubtractQuantitySuccess("Subtract quantity product with id %s success".formatted(command.productId()))
        );
    }

    private Message restoreQuantity(CommandMessage<RestoreQuantityCommand> commandMessage) {
        RestoreQuantityCommand command = commandMessage.getCommand();
        inventoryService.restoreQuantity(command.productId());
        return CommandHandlerReplyBuilder.withSuccess(
                new RestoreQuantitySuccess("Restore quantity product with id %s success".formatted(command.productId()))
        );
    }

}
