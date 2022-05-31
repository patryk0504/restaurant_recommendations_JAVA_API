package com.project.ZTI;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.media.JsonSchema;
import java.util.HashMap;

@Configuration
public class SpringDocConfig {


    @Bean
    public OpenAPI myOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI().info(new Info().title("My MWE API")
                .description("This document specifies the API")
                .version("v23"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components().addSecuritySchemes(securitySchemeName,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

//    @Bean
//    public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {
//        JsonSchema jsonSchema = new JsonSchema();
//        RequestBody requestBody = new RequestBody();
////        requestBody.setContent();
//        requestBody.addExtension("username", new RequestBody().description("username")
//                .content(new Content().addMediaType("application/json", new MediaType().schema(jsonSchema))));
////        requestBody.addExtension("username","username");
//        return openApi -> openApi.path("/api/login",
//                new PathItem().post(new Operation()
//                        .operationId("login")
//                        .requestBody(requestBody)
//                        .responses(new ApiResponses()
//                            .addApiResponse("200", new ApiResponse().description("OK")
//                                .content(new Content().addMediaType("*/*", new MediaType())))
//                        )));
//    }

}