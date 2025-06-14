////package com.garvit.provider.route;
////
////import com.garvit.provider.dto.ProviderDTO;
////import com.garvit.provider.mapper.ProviderMapper;
////import com.garvit.provider.model.Provider;
////import com.garvit.provider.repository.ProviderRepository;
////import jakarta.enterprise.context.ApplicationScoped;
////import jakarta.inject.Inject;
////import jakarta.transaction.Transactional;
////import org.apache.camel.builder.RouteBuilder;
////import org.apache.camel.model.rest.RestBindingMode;
////
////@ApplicationScoped
////public class ProviderRoute extends RouteBuilder {
////
//////    private final ProviderRepository providerRepository;
//////
//////    public ProviderRoute(ProviderRepository providerRepository) {
//////        this.providerRepository = providerRepository;
//////    }
////
////    @Inject
////    ProviderRepository providerRepository;
////
////
////    @Override
////    @Transactional
////    public void configure() throws Exception {
////        // Configure REST to use platform-http (Quarkus native REST)
////        restConfiguration()
//////                .component("platform-http")
////                .bindingMode(RestBindingMode.json);
////
////        // Define REST endpoint and bind to direct route
////        rest("/provider")
////                .post("/add").type(ProviderDTO.class)
////                .to("direct:addProvider");
////
////        // Internal route that handles persistence
////        from("direct:addProvider")
////                .log("Received provider: ${body}")
////                .bean(ProviderMapper.class, "toEntity")
////                .process(exchange -> {
////                    Provider provider = exchange.getMessage().getBody(Provider.class);
////                    providerRepository.persist(provider);
////                })
////                .setBody(constant("Provider saved successfully"));
////    }
////}
//
//
//package com.garvit.provider.route;
//
//import com.garvit.provider.dto.ProviderDTO;
//import com.garvit.provider.mapper.ProviderMapper;
//import com.garvit.provider.model.Provider;
//import com.garvit.provider.service.ProviderService;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.model.rest.RestBindingMode;
//
//@ApplicationScoped
//public class ProviderRoute extends RouteBuilder {
//
//    @Inject
//    ProviderService providerService;
//
//    @Override
//    public void configure() throws Exception {
//        restConfiguration()
//                .bindingMode(RestBindingMode.json);
//
//        rest("/provider")
//                .post("/add").type(ProviderDTO.class)
//                .to("direct:addProvider");
//
//        from("direct:addProvider")
//                .log("Received provider: ${body}")
//                .bean(ProviderMapper.class, "toEntity")
//                .process(exchange -> {
//                    Provider provider = exchange.getMessage().getBody(Provider.class);
//                    providerService.saveProvider(provider);  //
//                })
//            .log("Data saved successfully")
//                .setBody(constant("Provider saved successfully"));
//    }
//}


//package com.garvit.provider.route;
//import com.garvit.provider.dto.ResponseMessage;
//import org.apache.camel.model.dataformat.JsonLibrary;
//
//import com.garvit.provider.dto.ProviderDTO;
//import com.garvit.provider.mapper.ProviderMapper;
//import com.garvit.provider.model.Provider;
//import com.garvit.provider.repository.ProviderRepository;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.transaction.Transactional;
//import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.model.rest.RestBindingMode;
//
//@ApplicationScoped
//public class ProviderRoute extends RouteBuilder {
//
//    @Inject
//    ProviderRepository providerRepository;
//
//    @Override
//    public void configure() {
//        restConfiguration()
//                .component("platform-http")
//                .bindingMode(RestBindingMode.json);
//
//        rest("/provider")
//                .post("/add")
//                .type(ProviderDTO.class)
//                .to("direct:addProvider");
//
//        from("direct:addProvider")
//                .routeId("addProvider")
//                .log("Payload: ${body}")
//                .bean(ProviderMapper.class, "toEntity")
//                .process(exchange -> {
//                    Provider provider = exchange.getMessage().getBody(Provider.class);
//                    persistProvider(provider);
////                    exchange.getMessage().setBody("{\"message\":\"Provider saved successfully\"}");
//                    exchange.getMessage().setBody(new ResponseMessage("Provider saved successfully"));
//
//                });
//    }
//
//    @Transactional
//    void persistProvider(Provider p) {
//        providerRepository.persist(p);
//    }
//}


//
//package com.garvit.provider.route;
//
//import com.garvit.provider.dto.ProviderDTO;
//import com.garvit.provider.dto.ResponseMessage;
//import com.garvit.provider.mapper.ProviderMapper;
//import com.garvit.provider.model.Provider;
//import com.garvit.provider.model.SLA;
//import com.garvit.provider.repository.ProviderRepository;
//
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.transaction.Transactional;
//import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.model.rest.RestBindingMode;
//
//@ApplicationScoped
//@Transactional
//public class ProviderRoute extends RouteBuilder {
//
//    @Inject
//    ProviderRepository providerRepository;
//
//    @Override
//    public void configure() {
//
//        // REST Configuration
//        restConfiguration()
//                .component("platform-http")
//                .bindingMode(RestBindingMode.json);
//
//        // ======== REST DSL Definitions (No .route()) ==========
//        rest("/provider")
//                .post("/add").type(ProviderDTO.class).to("direct:addProvider")
//                .get("/all").to("direct:getAllProviders")
//                .get("/{id}").to("direct:getProviderById")
//                .put("/update/{id}").type(ProviderDTO.class).to("direct:updateProvider")
//                .delete("/delete/{id}").to("direct:deleteProvider");
//
//        // ========== ROUTES ==========
//
//        // POST - Add Provider
//        from("direct:addProvider")
//                .routeId("addProvider")
//                .log("Payload: ${body}")
//                .bean(ProviderMapper.class, "toEntity")
//                .process(exchange -> {
//                    Provider provider = exchange.getMessage().getBody(Provider.class);
//                    persistProvider(provider);
//                    exchange.getMessage().setBody(new ResponseMessage("Provider saved successfully"));
//                });
//
//        // GET - All Providers
//        from("direct:getAllProviders")
//                .log("Getting all providers")
//                .routeId("getAllProviders")
//                .bean(ProviderRepository.class, "getAllProviders")
//                .log("Retrieved ${body.size} providers");
//
//
//        // GET - Provider by ID
//        from("direct:getProviderById")
//                .routeId("getProviderById")
//                .process(exchange -> {
//                    Long id = exchange.getIn().getHeader("id", Long.class);
//                    Provider provider = providerRepository.findById(id);
//                    if (provider == null) {
//                        exchange.getMessage().setBody(new ResponseMessage("Provider not found"));
//                    } else {
//                        exchange.getMessage().setBody(provider);
//                    }
//                });
//
//        // PUT - Update Provider
//        from("direct:updateProvider")
//                .routeId("updateProvider")
//                .process(exchange -> {
//                    Long id = exchange.getIn().getHeader("id", Long.class);
//                    Provider existing = providerRepository.findById(id);
//                    if (existing == null) {
//                        exchange.getMessage().setBody(new ResponseMessage("Provider not found"));
//                        return;
//                    }
//
//                    ProviderDTO dto = exchange.getMessage().getBody(ProviderDTO.class);
//                    existing.setPartnerId(dto.getPartnerId());
//                    existing.setName(dto.getName());
//                    existing.setContactInfo(dto.getContactInfo());
//                    existing.setSupportedChannels(dto.getSupportedChannels());
//
//                    SLA sla = new SLA();
//                    sla.setDeliveryTimeMs(dto.getSla().getDeliveryTimeMs());
//                    sla.setUptimePercent(dto.getSla().getUptimePercent());
//                    existing.setSla(sla);
//
//                    providerRepository.persist(existing);
//                    exchange.getMessage().setBody(new ResponseMessage("Provider updated successfully"));
//                });
//
//        // DELETE - Delete Provider
//        from("direct:deleteProvider")
//                .routeId("deleteProvider")
//                .process(exchange -> {
//                    Long id = exchange.getIn().getHeader("id", Long.class);
//                    boolean deleted = providerRepository.deleteById(id);
//                    if (deleted) {
//                        exchange.getMessage().setBody(new ResponseMessage("Provider deleted successfully"));
//                    } else {
//                        exchange.getMessage().setBody(new ResponseMessage("Provider not found"));
//                    }
//                });
//    }
//
//    @Transactional
//    void persistProvider(Provider p) {
//        providerRepository.persist(p);
//    }
//}

//package com.garvit.provider.route;
//
//import com.garvit.provider.dto.ProviderDTO;
//import com.garvit.provider.dto.ResponseMessage;
//import com.garvit.provider.mapper.ProviderMapper;
//import com.garvit.provider.model.Provider;
//import com.garvit.provider.model.SLA;
//import com.garvit.provider.repository.ProviderRepository;
//import com.garvit.provider.util.LogHelper;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.transaction.Transactional;
//import org.apache.camel.Exchange;
//import org.apache.camel.Processor;
//import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.model.rest.RestBindingMode;
//
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.UUID;
//
//@ApplicationScoped
//@Transactional
//public class ProviderRoute extends RouteBuilder {
//
//    @Inject
//    ProviderRepository providerRepository;
//
//    @Inject
//    LogHelper logHelper;
//
//    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
//
//    @Override
//    public void configure() {
//
//        // REST Configuration
//        restConfiguration()
//                .component("platform-http")
//                .bindingMode(RestBindingMode.json);
//
//        // REST Endpoints
//        rest("/provider")
//                .post("/add").type(ProviderDTO.class).to("direct:addProvider")
//                .get("/all").to("direct:getAllProviders")
//                .get("/{id}").to("direct:getProviderById")
//                .put("/update/{id}").type(ProviderDTO.class).to("direct:updateProviderDirect")
//                .delete("/delete/{id}").to("direct:deleteProvider");
//
//        // Add Provider
//        from("direct:addProvider")
//                .routeId("addProvider")
//                .process(addCorrelationId())
//                .process(log("Request received from user"))
//                .bean(ProviderMapper.class, "toEntity")
//                .process(exchange -> {
//                    Provider provider = exchange.getMessage().getBody(Provider.class);
//                    persistProvider(provider);
//                })
//                .process(log("Provider saved to database"))
//                .process(exchange -> exchange.getMessage().setBody(new ResponseMessage("Provider saved successfully")))
//                .process(log("Response sent to user"));
//
//        // Get All Providers
//        from("direct:getAllProviders")
//                .routeId("getAllProviders")
//                .process(addCorrelationId())
//                .process(log("Request received from user"))
//                .bean(ProviderRepository.class, "getAllProviders")
//                .process(log("Response sent to user"));
//
//        // Get Provider by ID
//        from("direct:getProviderById")
//                .routeId("getProviderById")
//                .process(addCorrelationId())
//                .process(log("Request received from user"))
//                .process(exchange -> {
//                    String idHeader = exchange.getIn().getHeader("id", String.class);
//
//                    if ("all".equalsIgnoreCase(idHeader)) {
//                        exchange.getMessage().setBody(providerRepository.getAllProviders());
//                    } else {
//                        try {
//                            Long id = Long.parseLong(idHeader);
//                            Provider provider = providerRepository.findProviderById(id);
//
//                            if (provider == null) {
//                                exchange.getMessage().setBody(new ResponseMessage("Provider not found"));
//                            } else {
//                                exchange.getMessage().setBody(provider);
//                            }
//                        } catch (NumberFormatException e) {
//                            exchange.getMessage().setBody(new ResponseMessage("Invalid provider ID: " + idHeader));
//                        }
//                    }
//                })
//                .process(log("Response sent to user"));
//
//        // Update Provider
//        from("direct:updateProviderDirect")
//                .routeId("updateProviderDirect")
//                .process(addCorrelationId())
//                .process(log("Request received from user"))
//                .process(exchange -> {
//                    Long id = exchange.getIn().getHeader("id", Long.class);
//                    Provider existing = providerRepository.findProviderById(id);
//
//                    if (existing == null) {
//                        exchange.getMessage().setBody(new ResponseMessage("Provider not found"));
//                        return;
//                    }
//
//                    ProviderDTO dto = exchange.getMessage().getBody(ProviderDTO.class);
//                    existing.setPartnerId(dto.getPartnerId());
//                    existing.setName(dto.getName());
//                    existing.setContactInfo(dto.getContactInfo());
//                    existing.setSupportedChannels(dto.getSupportedChannels());
//
//                    SLA sla = new SLA();
//                    sla.setDeliveryTimeMs(dto.getSla().getDeliveryTimeMs());
//                    sla.setUptimePercent(dto.getSla().getUptimePercent());
//                    existing.setSla(sla);
//
//                    providerRepository.updateProvider(existing);
//                    exchange.getMessage().setBody(new ResponseMessage("Provider updated successfully"));
//                })
//                .process(log("Response sent to user"));
//
//        // Delete Provider
//        from("direct:deleteProvider")
//                .routeId("deleteProvider")
//                .process(addCorrelationId())
//                .process(log("Request received from user"))
//                .process(exchange -> {
//                    Long id = exchange.getIn().getHeader("id", Long.class);
//                    boolean deleted = providerRepository.deleteById(id);
//
//                    if (deleted) {
//                        exchange.getMessage().setBody(new ResponseMessage("Provider deleted successfully"));
//                    } else {
//                        exchange.getMessage().setBody(new ResponseMessage("Provider not found"));
//                    }
//                })
//                .process(log("Response sent to user"));
//    }
//
//    @Transactional
//    void persistProvider(Provider p) {
//        providerRepository.persist(p);
//    }
//
//    private Processor addCorrelationId() {
//        return exchange -> {
//            String correlationId = exchange.getIn().getHeader("X-Correlation-ID", String.class);
//            if (correlationId == null || correlationId.isBlank()) {
//                correlationId = UUID.randomUUID().toString();
//            }
//            exchange.getIn().setHeader("X-Correlation-ID", correlationId);
//            exchange.getIn().setHeader("LogTimestamp", FORMATTER.format(ZonedDateTime.now()));
//        };
//    }
//
//    private Processor log(String message) {
//        return exchange -> {
//            String logMsg = logHelper.log(message, exchange.getIn().getHeaders());
//            System.out.println(logMsg);
//        };
//    }
//}


package com.garvit.provider.route;

import com.garvit.provider.dto.ProviderDTO;
import com.garvit.provider.dto.ResponseMessage;
import com.garvit.provider.mapper.ProviderMapper;
import com.garvit.provider.model.Provider;
import com.garvit.provider.model.SLA;
import com.garvit.provider.repository.ProviderRepository;
import com.garvit.provider.util.LogHelper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class ProviderRoute extends RouteBuilder {

    @Inject
    ProviderRepository providerRepository;

    @Inject
    LogHelper logHelper;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @Override
    public void configure() {

        // REST Configuration
        restConfiguration()
                .component("platform-http")
                .bindingMode(RestBindingMode.json);

        // REST Endpoints
        rest("/provider")
                .post("/add").type(ProviderDTO.class).to("direct:addProvider")
                .get("/all").to("direct:getAllProviders")
                .get("/{id}").to("direct:getProviderById")
                .put("/update/{id}").type(ProviderDTO.class).to("direct:updateProviderDirect")
                .delete("/delete/{id}").to("direct:deleteProvider");

        // Add Provider
        from("direct:addProvider")
                .routeId("addProvider")
                .process(addCorrelationId())
                .process(log("Request received from user"))
                .bean(ProviderMapper.class, "toEntity")
                .process(exchange -> {
                    Provider provider = exchange.getMessage().getBody(Provider.class);
                    persistProvider(provider);
                })
                .process(log("Provider saved to database"))
                .process(exchange -> exchange.getMessage().setBody(new ResponseMessage("Provider saved successfully")))
                .process(log("Response sent to user"));

        // Get All Providers
        from("direct:getAllProviders")
                .routeId("getAllProviders")
                .process(addCorrelationId())
                .process(log("Request received from user"))
                .bean(ProviderRepository.class, "getAllProviders")
                .process(log("Response sent to user"));

        // Get Provider by ID
        from("direct:getProviderById")
                .routeId("getProviderById")
                .process(addCorrelationId())
                .process(log("Request received from user"))
                .process(exchange -> {
                    String idHeader = exchange.getIn().getHeader("id", String.class);

                    if ("all".equalsIgnoreCase(idHeader)) {
                        exchange.getMessage().setBody(providerRepository.getAllProviders());
                    } else {
                        try {
                            Long id = Long.parseLong(idHeader);
                            Provider provider = providerRepository.findProviderById(id);

                            if (provider == null) {
                                exchange.getMessage().setBody(new ResponseMessage("Provider not found"));
                            } else {
                                exchange.getMessage().setBody(provider);
                            }
                        } catch (NumberFormatException e) {
                            exchange.getMessage().setBody(new ResponseMessage("Invalid provider ID: " + idHeader));
                        }
                    }
                })
                .process(log("Response sent to user"));

        // Update Provider
        from("direct:updateProviderDirect")
                .routeId("updateProviderDirect")
                .process(addCorrelationId())
                .process(log("Request received from user"))
                .process(exchange -> {
                    Long id = exchange.getIn().getHeader("id", Long.class);
                    Provider existing = providerRepository.findProviderById(id);

                    if (existing == null) {
                        exchange.getMessage().setBody(new ResponseMessage("Provider not found"));
                        return;
                    }

                    ProviderDTO dto = exchange.getMessage().getBody(ProviderDTO.class);
                    existing.setPartnerId(dto.getPartnerId());
                    existing.setName(dto.getName());
                    existing.setContactInfo(dto.getContactInfo());
                    existing.setSupportedChannels(dto.getSupportedChannels());

                    SLA sla = new SLA();
                    sla.setDeliveryTimeMs(dto.getSla().getDeliveryTimeMs());
                    sla.setUptimePercent(dto.getSla().getUptimePercent());
                    existing.setSla(sla);

                    providerRepository.updateProvider(existing);
                    exchange.getMessage().setBody(new ResponseMessage("Provider updated successfully"));
                })
                .process(log("Response sent to user"));

        // Delete Provider
        from("direct:deleteProvider")
                .routeId("deleteProvider")
                .process(addCorrelationId())
                .process(log("Request received from user"))
                .process(exchange -> {
                    Long id = exchange.getIn().getHeader("id", Long.class);
                    boolean deleted = providerRepository.deleteById(id);

                    if (deleted) {
                        exchange.getMessage().setBody(new ResponseMessage("Provider deleted successfully"));
                    } else {
                        exchange.getMessage().setBody(new ResponseMessage("Provider not found"));
                    }
                })
                .process(log("Response sent to user"));
    }

    @Transactional
    void persistProvider(Provider p) {
        providerRepository.persist(p);
    }

    private Processor addCorrelationId() {
        return exchange -> {
            String correlationId = exchange.getIn().getHeader("X-Correlation-ID", String.class);
            if (correlationId == null || correlationId.isBlank()) {
                correlationId = UUID.randomUUID().toString();
            }
            exchange.getIn().setHeader("X-Correlation-ID", correlationId);
            exchange.getIn().setHeader("LogTimestamp", FORMATTER.format(ZonedDateTime.now()));
        };
    }

    private Processor log(String message) {
        return exchange -> {
            String logMsg = logHelper.log(message, exchange.getIn().getHeaders());
            System.out.println(logMsg);
        };
    }
}





