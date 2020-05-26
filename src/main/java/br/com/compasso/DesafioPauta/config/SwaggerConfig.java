package br.com.compasso.DesafioPauta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;

@Configuration
@EnableSwagger2WebMvc
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfig {

    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.compasso.DesafioPauta"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .apiInfo(metaInfo());


    }

    private ApiInfo metaInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "API meeting agenda",
                "API REST",
                "1.0",
                "https://www.google.com.br",
                new Contact("Matheus Barkert", "https://github.com/Matheus-Barkert",
                        "mbarkert11@hotmail.com"),
                "Without license",
                "https://www.google.com.br", new ArrayList<VendorExtension>()
        );

        return apiInfo;
    }


}