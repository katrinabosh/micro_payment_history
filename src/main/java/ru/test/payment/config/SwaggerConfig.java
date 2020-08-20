package ru.test.payment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired(required = false)
    private BuildProperties buildProperties;

    @Bean

    public Docket api() {
        var version = buildProperties != null ? buildProperties.getVersion() : "undefined";
        var apiInfo = new ApiInfoBuilder()
                .title("Payment history")
                .description("Сервис истории платежей")
                .version(version)
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.test.payment.web"))
                .paths(PathSelectors.ant("/**"))
                .build();
    }
}
