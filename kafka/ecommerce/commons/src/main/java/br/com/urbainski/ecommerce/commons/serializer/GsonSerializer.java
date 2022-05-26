package br.com.urbainski.ecommerce.commons.serializer;

import br.com.urbainski.ecommerce.commons.gson.GsonFactory;
import br.com.urbainski.ecommerce.commons.kafka.MyMessage;
import br.com.urbainski.ecommerce.commons.serializer.adapter.MyMessageAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Serializer;

public class GsonSerializer<T> implements Serializer<T> {

    @Override
    public byte[] serialize(String s, T t) {
        return GsonFactory.getInstance().getGson().toJson(t).getBytes();
    }

}
