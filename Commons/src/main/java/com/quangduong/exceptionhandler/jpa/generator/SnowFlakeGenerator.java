package com.quangduong.exceptionhandler.jpa.generator;

import de.mkammerer.snowflakeid.SnowflakeIdGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class SnowFlakeGenerator implements IdentifierGenerator {

    private static final Integer generatorId = 1;

    private static final SnowflakeIdGenerator generator = SnowflakeIdGenerator.createDefault(generatorId);

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return generator.next();
    }
}
