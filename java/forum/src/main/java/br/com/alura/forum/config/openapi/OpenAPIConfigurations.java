package br.com.alura.forum.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfigurations {

    private static final String PUBLIC_PATH = "/public/**";

    @Value("${info.app.name}")
    private String infoAppName;

    @Value("${info.app.description}")
    private String infoAppDescription;

    @Value("${info.app.version}")
    private String infoAppVersion;

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("forum-public")
                .pathsToMatch(PUBLIC_PATH)
                .displayName("Serviços públicos (não requer autenticação)")
                .build();
    }

    @Bean
    public GroupedOpenApi protectedApi() {
        return GroupedOpenApi.builder()
                .group("forum-protected")
                .pathsToExclude(PUBLIC_PATH)
                .displayName("Serviços protegidos (requer autenticação)")
                .build();
    }

    @Bean
    public OpenAPI forumApi() {
        Info info = new Info()
                .title(infoAppName)
                .description(infoAppDescription)
                .version(infoAppVersion);

        String bearerAuth = "Autenticacao JWT";

        SecurityScheme securityScheme = new SecurityScheme()
                .name("Authorization")
                .description("Cabeçalho para o token de autenticação JWT")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        Components components = new Components().addSecuritySchemes(bearerAuth, securityScheme);

        return new OpenAPI().info(info).components(components).addSecurityItem(new SecurityRequirement().addList(bearerAuth));
    }

}
