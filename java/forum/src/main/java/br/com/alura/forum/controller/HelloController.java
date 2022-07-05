package br.com.alura.forum.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @ResponseBody
    @GetMapping(value = "/", produces = "text/plain")
    @Operation(
            operationId = "hello",
            description = "Método de exemplo para dizer olá ao usuário",
            responses = {
					@ApiResponse(responseCode = "200", description = "Sucesso na execução do método")
            })
    public String hello() {
        return "Hello World!";
    }

}
