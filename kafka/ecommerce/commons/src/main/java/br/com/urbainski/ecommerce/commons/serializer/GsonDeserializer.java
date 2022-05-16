package br.com.urbainski.ecommerce.commons.serializer;

import br.com.urbainski.ecommerce.commons.kafka.KafkaProperties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class GsonDeserializer<T> implements Deserializer<T> {

    private final Gson gson = new GsonBuilder().create();
    private Class<T> type;

    public GsonDeserializer() {

    }

    @Override
    @SuppressWarnings("unchecked")
    public void configure(Map<String, ?> configs, boolean isKey) {

        var config = configs.get(KafkaProperties.TYPE_CLASS_CONFIG);
        if (config == null) {
            throw new RuntimeException("Type class config does not exists");
        }

        try {
            this.type = (Class<T>) Class.forName(String.valueOf(config));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Type class config does not exists", e);
        }
    }

    @Override
    public T deserialize(String s, byte[] bytes) {
        return gson.fromJson(new String(bytes), this.type);
    }

}
