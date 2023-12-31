package com.quangduong.sagas.command;

import io.eventuate.tram.commands.common.Command;

public record RestoreQuantityCommand(
        Long productId
) implements Command {}
