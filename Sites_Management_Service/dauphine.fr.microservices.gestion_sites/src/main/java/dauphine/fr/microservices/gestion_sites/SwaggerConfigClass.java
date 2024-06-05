package dauphine.fr.microservices.gestion_sites;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfigClass {

    private static final String GROUP_NAME = "Public API";
    private static final String BASE_PACKAGE = "dauphine.fr.microservices.gestion_sites";
    private static final String API_TITLE = "Site Management Service";
    private static final String API_DESCRIPTION = "API for managing site information in the system.";
    private static final String API_VERSION = "1.0";
    private static final String TERMS_OF_SERVICE_URL = "http://localhost:8301/terms";
    private static final String CONTACT_NAME = "Support Team";
    private static final String CONTACT_URL = "http://localhost:8301/support";
    private static final String CONTACT_EMAIL = "support@gestionsites.com";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(GROUP_NAME)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_TITLE)
                .description(API_DESCRIPTION)
                .termsOfServiceUrl(TERMS_OF_SERVICE_URL)
                .contact(new Contact(CONTACT_NAME, CONTACT_URL, CONTACT_EMAIL))
                .version(API_VERSION)
                .build();
    }
}
