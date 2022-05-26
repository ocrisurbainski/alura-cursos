package br.com.urbainski.ecommerce.commons.gson;

import br.com.urbainski.ecommerce.commons.kafka.MyMessage;
import br.com.urbainski.ecommerce.commons.serializer.adapter.MyMessageAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {

    private static GsonFactory INSTANCE;
    private final Gson gson;

    private GsonFactory() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(MyMessage.class, new MyMessageAdapter())
                .create();
    }

    public Gson getGson() {
        return gson;
    }

    public static GsonFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GsonFactory();
        }
        return INSTANCE;
    }

}
