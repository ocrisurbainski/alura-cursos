package br.com.urbainski.ecommerce.commons.kafka;

public enum Topics {

    ECOMMERCE_NEW_ORDER,
    ECOMMERCE_SEND_EMAIL,
    ECOMMERCE_NEW_ORDER_APPROVED,
    ECOMMERCE_NEW_ORDER_REJECTED,
    ECOMMERCE_REPORT_FOR_ALL_USERS,
    ECOMMERCE_REPORT_USER,

    ECOMMERCE_ALL {
        @Override
        public String getTopicName() {
            return "ECOMMERCE.*";
        }
    };

    public String getTopicName() {
        return this.name();
    }

    public String getDeadLetterTopicName() {
        return this.name() + "_DL";
    }

}
