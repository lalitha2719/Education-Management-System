package com.ems.config;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import org.springframework.context.annotation.Bean;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(this.apiInfo()).select().build();
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Course Faculty Service").description("Course Faculty API for EMS").termsOfServiceUrl("http://www.cognizant.com").version("2.0").build();
    }
}