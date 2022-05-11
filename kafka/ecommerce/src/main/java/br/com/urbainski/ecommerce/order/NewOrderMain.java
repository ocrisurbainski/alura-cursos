package br.com.urbainski.ecommerce.order;

import br.com.urbainski.ecommerce.email.EmailProducerService;

public class NewOrderMain {

    public static void main(String[] args) {

        var value = "1,1,1250.00";

        try (var newOrderService = new NewOrderService()) {
            newOrderService.send(value);
        }

        try (var emailService = new EmailProducerService()) {
            emailService.send("Sua venda está sendo processada, aguarde...");
        }
    }


}
