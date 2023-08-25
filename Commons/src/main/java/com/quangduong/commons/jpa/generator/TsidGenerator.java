package com.quangduong.commons.jpa.generator;

import com.github.f4b6a3.tsid.TsidFactory;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.time.Instant;

public class TsidGenerator implements IdentifierGenerator {

    private static final int datacenter = 1;
    private static final int worker = 1;
    private static final int node = (datacenter << 5 | worker);

    private static final TsidFactory factory = TsidFactory.builder()
            .withRandomFunction(byte[]::new)
            .withCustomEpoch(Instant.ofEpochMilli(1288834974657L))
            .withNode(node)
            .build();

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return factory.create().toLong();
    }

}
