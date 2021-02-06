package com.moneylion.feature.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * Swagger config for rest api documentation
 * You can access through any browser with below url
 * http://[hostname]:[serverport]/swagger-ui/index.html
 *
 * @author srinivas
 * @version 1.0
 * @since 2021-02-06
 */

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.moneylion.feature.resource"))
                .paths(PathSelectors.any())
                .build().apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {

        return new ApiInfo(
                "Feature Management API",
                "This is a POC developed using Spring Boot and Swagger 2",
                "V1",
                "urn:tos",
                new Contact("Srinivas Boini", "https://github.com/srinivasboini", "srinivasboini7@gmail.com"),
                "",
                "",
                Collections.emptyList()

        );

    }
}
