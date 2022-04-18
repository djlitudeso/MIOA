package pers.jl.mioa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2API文档的配置
 * @author: JL
 */

@Configuration
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        // 创建 Docket 对象
        // 文档类型，使用 Swagger2
        return new Docket(DocumentationType.SWAGGER_2)
                // 设置 API 信息
                .apiInfo(apiInfo())
                // 扫描 Controller 包路径，获得 API 接口
                .select()
                .apis(RequestHandlerSelectors.basePackage("pers.jl.mioa.controller"))
                .paths(PathSelectors.any())
                // 构建出 Docket 对象
                .build()
                //添加登录认证
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    /**
     * 创建 API 信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("MIOA后台系统")
                .description("MIOA后台相关在线接口文档")
                .contact(new Contact("JL", "https://www.jeureka.cn", "djlsolitude@qq.com"))
                .version("1.0")
                .build();
    }

    private List<SecurityScheme> securitySchemes() {
        //设置请求头信息
        List<SecurityScheme> result = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
        result.add(apiKey);
        return result;
    }

    private List<SecurityContext> securityContexts() {
        //设置需要登录认证的路径
        List<SecurityContext> result = new ArrayList<>();
        result.add(getContextByPath("/*/.*"));
        return result;
    }

    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        result.add(new SecurityReference("Authorization", authorizationScopes));
        return result;
    }
}


