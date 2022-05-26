package br.com.urbainski.ecommerce.commons.kafka;

public class MyMessage<T> {

    private final CorrelationId correlationId;
    private final T payload;

    public MyMessage(CorrelationId correlationId, T payload) {
        this.correlationId = correlationId;
        this.payload = payload;
    }

    public CorrelationId getCorrelationId() {
        return correlationId;
    }

    public T getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "MyMessage{" +
                "correlationId=" + correlationId +
                ", payload=" + payload +
                '}';
    }

}
