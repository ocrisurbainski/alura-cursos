package br.com.urbainski.ecommerce.commons.order;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

public class Order {
    private final String orderId;

    private final String email;
    private final BigDecimal value;

    public Order(String orderId, String email, BigDecimal value) {
        this.orderId = orderId;
        this.email = email;
        this.value = value;
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
