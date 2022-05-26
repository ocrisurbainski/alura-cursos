package br.com.urbainski.ecommerce.commons.kafka;

import java.util.UUID;

public class CorrelationId {

    private final String id;

    public CorrelationId(String title) {

        this.id = String.format("%s(%s)", title, UUID.randomUUID());
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CorrelationId{" +
                "id='" + id + '\'' +
                '}';
    }

}
