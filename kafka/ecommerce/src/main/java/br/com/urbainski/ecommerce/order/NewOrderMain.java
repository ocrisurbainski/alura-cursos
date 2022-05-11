package br.com.urbainski.ecommerce.order;

import br.com.urbainski.ecommerce.email.EmailProducerService;

import java.util.UUID;

public class NewOrderMain {

    public static void main(String[] args) {
        try (var newOrderService = new NewOrderService()) {
            try (var emailService = new EmailProducerService()) {
                for (var i = 0; i < 10; i++) {
                    var userId = UUID.randomUUID();
                    var value = String.format("%s,1,1250.00", userId.toString());
                    newOrderService.send(userId.toString(), value);
                    emailService.send(userId.toString(), "Sua venda está sendo processada, aguarde...");
                }
            }
        }

    }


}
