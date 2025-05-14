package vla.sai.spring.notificationservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Collections;

@Configuration
@OpenAPI30
@RequiredArgsConstructor
public class OpenApiConfig {

    private final Environment environment;

    @Value("${springdoc.swagger-ui.enabled}")
    private boolean swaggerEnabled;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${server.port}")
    private String port;

    @Bean
    public OpenAPI openAPIDefinition() {
        String swaggerHost = (swaggerEnabled)? environment.getRequiredProperty("SWAGGER_HOSTNAME") : "localhost";
        return new OpenAPI()
                .info(new Info()
                        .title("Notification service")
                        .version("v0.0.1")
                        .description("Сервис для работы с почтовыми уведомлениями"))
                .servers(Collections.singletonList(new Server()
                        .description("Рабочий сервер")
                        .url("http://"+swaggerHost+":"+port+contextPath )));
    }
}