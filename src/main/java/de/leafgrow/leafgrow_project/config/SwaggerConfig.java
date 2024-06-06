package de.leafgrow.leafgrow_project.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "JWT demo app",
                description = "Demo application for JSON web tokens",
                version = "1.0.0",
                contact = @Contact(
                        name = "LeafGrow",
                        email = "leafgrow.project@gmail.com",
                        url = "https://github.com/LeafGrow"
                )
        )
)
public class SwaggerConfig {
}
