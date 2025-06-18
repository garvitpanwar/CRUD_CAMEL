package com.garvit.provider.route;

import com.garvit.provider.dto.ProviderDTO;
import com.garvit.provider.dto.ResponseMessage;
import com.garvit.provider.service.ProviderService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.camel.component.bean.validator.BeanValidationException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProviderRoute extends RouteBuilder {

    private static final Logger log = LoggerFactory.getLogger(ProviderRoute.class);

    @Inject
    ProviderService providerService;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public void configure() {
        // REST Configuration
        restConfiguration()
                .component("platform-http")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Provider API")
                .apiProperty("api.version", "1.0.0")
                .enableCORS(true);

        // Global Error handling
        onException(Exception.class)
                .handled(true)
                .log("Error occurred: ${exception}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
                .setBody(exchange -> new ResponseMessage("Internal server error"))
                .marshal().json(JsonLibrary.Jackson);

//        onException(BeanValidationException.class)
//                .handled(true)
//                .log("Validation error: ${exception.message}")
//                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
//                .process(exchange -> {
//                    ConstraintViolationException exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, ConstraintViolationException.class);
//                    if (exception == null) {
//                        exception = exchange.getException(ConstraintViolationException.class);
//                    }
//
//                    List<String> errors = exception.getConstraintViolations().stream()
//                            .map(v -> v.getPropertyPath() + ": " + v.getMessage())
//                            .collect(Collectors.toList());
//                    exchange.getMessage().setBody(new ResponseMessage("Validation failed", errors));
//                });


        onException(BeanValidationException.class)
                .handled(true)
                .log("Inside Exception")
                .process(exchange -> {
                    Exception ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, BeanValidationException.class);
//                    List<String> errors = ex.
//                            .stream()
//                            .map(ConstraintViolation::getMessage)
//                            .collect(Collectors.toList());
                    exchange.getMessage().setBody(Map.of("errors", ex.getMessage()));
                    exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
                })
                .marshal().json(JsonLibrary.Jackson);
        // REST Endpoints
        rest("/provider")
                .post("/add")
                .description("Add a new provider")
                .type(ProviderDTO.class)
                .outType(ResponseMessage.class)
                .consumes("application/json")
                .produces("application/json")
                .to("direct:addProvider")

                .get("/all")
                .description("Get all providers")
                .outType(List.class)
                .produces("application/json")
                .to("direct:getAllProviders")

                .get("/{id}")
                .description("Get provider by ID")
                .outType(ProviderDTO.class)
                .produces("application/json")
                .to("direct:getProviderById")

                .put("/update/{id}")
                .description("Update existing provider")
                .type(ProviderDTO.class)
                .outType(ResponseMessage.class)
                .consumes("application/json")
                .produces("application/json")
                .to("direct:updateProvider")

                .delete("/delete/{id}")
                .description("Delete provider by ID")
                .outType(ResponseMessage.class)
                .produces("application/json")
                .to("direct:deleteProvider");

        // Add Provider
//        from("direct:addProvider")
//                .routeId("add-provider-route")
//                .log("[AddProvider] - Received request to add provider: ${body}")
//
//                .log("[AddProvider] - Validating provider DTO")
//                .to("bean-validator://providerValidator")
//
//                .log("[AddProvider] - Mapping DTO to entity and persisting to DB via ProviderService")
//                .bean(providerService, "saveProvider(${body})")
//
//                .log("[AddProvider] - Provider saved to DB with ID: ${body.id}")
//
//                .setBody(exchange -> new ResponseMessage("Provider added successfully"))
//                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
//
//                .log("[AddProvider] - Responding with success message");



        from("direct:addProvider")
                .routeId("add-provider-route")

                // ⛑ERROR HANDLER BLOCK
                .onException(jakarta.persistence.PersistenceException.class)
                .handled(true)
                .log("Duplicate entry error: ${exception.message}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .setBody(exchange -> new ResponseMessage("Partner ID or Name already exists"))
                .end()

                // 🛠️ Main Route Logic
                .log("[AddProvider] - Received request to add provider: ${body}")
                .to("bean-validator://providerValidator")
                .log("[AddProvider] - Provider DTO validated")

                .bean(providerService, "saveProvider(${body})")
                .log("[AddProvider] - Provider saved with ID: ${body.id}")

                .setBody(exchange -> new ResponseMessage("Provider added successfully"))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
                .log("[AddProvider] - Success response sent");



        // Get All Providers
        from("direct:getAllProviders")
                .routeId("get-all-providers-route")

                .log("[GetAllProviders] - Received request to fetch all providers")

                .log("[GetAllProviders] - Calling ProviderService to fetch list")
                .bean(providerService, "getAllProviders")

                .choice()
                .when(body().isNull())
                .log("⚠[GetAllProviders] - No providers found in DB")
                .setBody(exchange -> new ResponseMessage("No providers found"))
                .otherwise()
                .log("[GetAllProviders] - Providers fetched successfully: ${body.size} record(s) found")
                .end();


        // Get Provider by ID
        from("direct:getProviderById")
                .routeId("get-provider-by-id-route")

                .log("[GetProviderById] - Received request to fetch provider with ID: ${header.id}")

                .log("[GetProviderById] - Calling ProviderService to fetch provider")
                .bean(providerService, "getProviderById(${header.id})")

                .choice()
                .when(body().isNull())
                .log("[GetProviderById] - No provider found with ID: ${header.id}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404))
                .setBody(exchange -> new ResponseMessage("Provider not found"))
                .otherwise()
                .log("[GetProviderById] - Provider found: ${body}")
                .end();


//    Update by ID
        from("direct:updateProvider")
                .routeId("update-provider-route")
                .log("Updating provider with ID: ${header.id}")
                .to("bean-validator://providerValidator")


                .bean(providerService, "updateProvider(${header.id}, ${body})")


                .choice()
                .when(body().isNotNull())
                .log("Provider updated successfully")
                .setBody(exchange -> new ResponseMessage("Provider updated successfully"))
                .otherwise()

                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404))
                .setBody(exchange -> new ResponseMessage("Provider not found"))
                .end();

        // Delete Provider
        from("direct:deleteProvider")
                .routeId("delete-provider-route")

                .log("[DeleteProvider] - Received request to delete provider with ID: ${header.id}")

                .log("[DeleteProvider] - Calling ProviderService to delete provider")
                .bean(providerService, "deleteProvider(${header.id})")

                .choice()
                .when(body().isEqualTo(true))
                .log("[DeleteProvider] - Provider with ID ${header.id} deleted successfully")
                .setBody(exchange -> new ResponseMessage("Provider deleted successfully"))
                .otherwise()
                .log("[DeleteProvider] - Provider with ID ${header.id} not found")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404))
                .setBody(exchange -> new ResponseMessage("Provider not found"))
                .end();

    }


}
