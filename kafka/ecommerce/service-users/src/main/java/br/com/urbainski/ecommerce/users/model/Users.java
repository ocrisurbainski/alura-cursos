package br.com.urbainski.ecommerce.users.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Users {
    private final String uuid;
    private final String email;

    public Users(String uuid, String email) {
        this.uuid = uuid;
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
