package ge.bog.banking.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class SwaggerConfig {

   @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Banking Rest API").description("Banking API")
                .contact(new Contact().name("Burdila").email("gburdiladze@bog.ge")).version("1.0.0"));
    }

}
