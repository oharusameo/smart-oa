package com.quxue.template.config;

import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Data
@Component
@ConfigurationProperties("swagger")
@EnableWebMvc
@EnableOpenApi
public class SwaggerConfig {
    private boolean enabled;
    private String applicationName;
    private String applicationVersion;
    private String applicationDescription;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .pathMapping("/")
                .enable(enabled)
                //配置api文档的元信息
                .apiInfo(getApiInfo())
                //配置哪些接口需要发布为swagger文档
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.quxue.template.api"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title(applicationName)
                .description(applicationDescription)
                .version(applicationVersion)
                .contact(new Contact("harusame", "https://www.bilibili.com", "ggzst321@outlook.com"))
                .build();

    }
}
