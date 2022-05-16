package br.com.urbainski.ecommerce.kafka;

import br.com.urbainski.ecommerce.serializer.GsonDeserializer;
import br.com.urbainski.ecommerce.serializer.GsonSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public enum KafkaTypes {
    TEXT(StringSerializer.class.getName(), StringDeserializer.class.getName()),
    JSON(GsonSerializer.class.getName(), GsonDeserializer.class.getName());

    private final String classSerializer;
    private final String classDeserializer;

    KafkaTypes(String classSerializer, String classDeserializer) {
        this.classSerializer = classSerializer;
        this.classDeserializer = classDeserializer;
    }

    public String getClassSerializer() {
        return classSerializer;
    }

    public String getClassDeserializer() {
        return classDeserializer;
    }

}
