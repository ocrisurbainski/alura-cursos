package br.com.urbainski.ecommerce.order;

import br.com.urbainski.ecommerce.email.EmailProducerService;

import java.util.UUID;

public class NewOrderMain {

    public static void main(String[] args) {

        var userId = UUID.randomUUID();

        var value = String.format("%s,1,1250.00", userId.toString());

        try (var newOrderService = new NewOrderService()) {
            newOrderService.send(userId.toString(), value);
        }

        try (var emailService = new EmailProducerService()) {
            emailService.send(userId.toString(), "Sua venda está sendo processada, aguarde...");
        }
    }


}
