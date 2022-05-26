package br.com.urbainski.ecommerce.commons.serializer;

import br.com.urbainski.ecommerce.commons.gson.GsonFactory;
import br.com.urbainski.ecommerce.commons.kafka.MyMessage;
import org.apache.kafka.common.serialization.Deserializer;

public class GsonDeserializer implements Deserializer<MyMessage<?>> {

    public GsonDeserializer() {

    }

    @Override
    public MyMessage<?> deserialize(String s, byte[] bytes) {
        return GsonFactory.getInstance().getGson().fromJson(new String(bytes), MyMessage.class);
    }

}
