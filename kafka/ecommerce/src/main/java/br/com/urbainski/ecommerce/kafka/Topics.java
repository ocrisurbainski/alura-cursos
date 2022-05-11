package br.com.urbainski.ecommerce.kafka;

public enum Topics {

    ECOMMERCE_NEW_ORDER,
    ECOMMERCE_SEND_EMAIL,

    ECOMMERCE_ALL {
        @Override
        public String getTopicName() {
            return "ECOMMERCE.*";
        }
    };

    public String getTopicName() {
        return this.name();
    }

}
