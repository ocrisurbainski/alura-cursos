package br.com.urbainski.ecommerce.commons.email;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Email {

    private final String orderId;
    private final String title;
    private final String content;
    private final String to;

    public Email(String orderId, String title, String content, String to) {
        this.orderId = orderId;
        this.title = title;
        this.content = content;
        this.to = to;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
