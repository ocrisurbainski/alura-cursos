package br.com.urbainski.ecommerce.commons.serializer.adapter;

import br.com.urbainski.ecommerce.commons.kafka.CorrelationId;
import br.com.urbainski.ecommerce.commons.kafka.MyMessage;
import com.google.gson.*;

import java.lang.reflect.Type;

public class MyMessageAdapter implements JsonSerializer<MyMessage<?>>, JsonDeserializer<MyMessage<?>> {

    private static final String TYPE = "type";
    private static final String PAYLOAD = "payload";
    private static final String CORRELATION_ID = "correlationId";

    @Override
    public JsonElement serialize(MyMessage myMessage, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();
        obj.addProperty(TYPE, myMessage.getPayload().getClass().getName());
        obj.add(PAYLOAD, jsonSerializationContext.serialize(myMessage.getPayload()));
        obj.add(CORRELATION_ID, jsonSerializationContext.serialize(myMessage.getCorrelationId()));
        return obj;
    }

    @Override
    public MyMessage<?> deserialize(
            JsonElement jsonElement,
            Type type,
            JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {

        var obj = jsonElement.getAsJsonObject();
        var payloadType = obj.get(TYPE).getAsString();
        var correlationId = (CorrelationId) jsonDeserializationContext.deserialize(obj.get(CORRELATION_ID), CorrelationId.class);

        try {
            var payload = jsonDeserializationContext.deserialize(obj.get(PAYLOAD), Class.forName(payloadType));
            return new MyMessage<>(correlationId, payload);
        } catch (ClassNotFoundException ex) {
            throw new JsonParseException(ex);
        }
    }
}
