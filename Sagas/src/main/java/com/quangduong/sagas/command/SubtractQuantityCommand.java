package com.quangduong.sagas.command;

import io.eventuate.tram.commands.common.Command;
import lombok.Builder;

public record SubtractQuantityCommand(
        Long productId
) implements Command {}
