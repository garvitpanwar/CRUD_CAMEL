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

//
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
//            from("direct:deleteProvider")
//                    .routeId("deleteProvider")
//                    .process(addCorrelationId())
//                    .process(log("Request received from user ${Body}"))
//                    .process(exchange -> {
//                        Long id = exchange.getIn().getHeader("id", Long.class);
//                        boolean deleted = providerRepository.deleteById(id);
//
//                        if (deleted) {
//                            exchange.getMessage().setBody(new ResponseMessage("Provider deleted successfully"));
//                        } else {
//                            exchange.getMessage().setBody(new ResponseMessage("Provider not found"));
//                        }
//                    })
//                    .process(log("Response sent to user"));
//        }
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
//
//
//
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
//                    providerRepository.persistProvider(provider);
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
//                .process(exchange -> {
//                    exchange.getMessage().setBody(providerRepository.getAllProviders());
//                })
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
//                    boolean deleted = providerRepository.deleteProviderById(id);
//
//                    if (deleted) {
//                        exchange.getMessage().setBody(new ResponseMessage("Provider deleted successfully"));
//                    } else {
//                        exchange.getMessage().setBody(new ResponseMessage("Provider not found"));
//                        exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
//                    }
//                })
//                .process(log("Response sent to user"));
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.garvit.provider.service.ProviderService;
import com.garvit.provider.dto.ProviderDTO;
import com.garvit.provider.model.Provider;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


import com.garvit.provider.dto.ProviderDTO;
import com.garvit.provider.dto.ResponseMessage;
import com.garvit.provider.model.Provider;
import com.garvit.provider.service.ProviderService;
import org.apache.camel.Exchange;


@ApplicationScoped
public class ProviderRoute extends RouteBuilder {

    private static final Logger log = LoggerFactory.getLogger(ProviderRoute.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @Inject
    ProviderRepository providerRepository;

    @Inject
    ProviderService providerService;

    @Inject
    LogHelper logHelper;

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

        // Add Provider - Proper Camel Way
        from("direct:addProvider")
                .routeId("addProvider")
                .process(initializeLogging())
                .process(logRequestReceived("POST /provider/add"))
                .process(logRequestSentToService("ProviderMapper.toEntity"))
                .bean(ProviderMapper.class, "toEntity")
                .process(logResponseReceivedFromService("ProviderMapper.toEntity"))
                .process(logRequestSentToService("ProviderRepository.persistProvider"))
                .process(exchange -> {
                    Provider provider = exchange.getMessage().getBody(Provider.class);
                    providerRepository.persistProvider(provider);
                    exchange.getMessage().setBody(new ResponseMessage("Provider saved successfully"));
                    logInfo("Provider saved successfully");
                })
                .process(logResponseReceivedFromService("ProviderRepository.persistProvider"))
                .process(logResponseSentToUser("Provider saved successfully"))
                .process(cleanupLogging());

        // Get All Providers - Proper Camel Way
        from("direct:getAllProviders")
                .routeId("getAllProviders")
                .process(initializeLogging())
                .process(logRequestReceived("GET /provider/all"))
                .process(logRequestSentToService("ProviderRepository.getAllProviders"))
                .process(exchange -> {
                    Object result = providerRepository.getAllProviders();
                    exchange.getMessage().setBody(result);
                    logInfo("All providers retrieved successfully");
                })
                .process(logResponseReceivedFromService("ProviderRepository.getAllProviders"))
                .process(logResponseSentToUser("All providers retrieved"))
                .process(cleanupLogging());

        // Update Provider - Proper Camel Way
        // ProviderRoute.java mein update karo
        from("direct:updateProviderDirect")
                .routeId("updateProviderDirect")
                .process(initializeLogging())
                .process(logRequestReceived("PUT /provider/update/{id}"))
                .process(exchange -> {
                    String idParam = exchange.getIn().getHeader("id", String.class);
                    try {
                        Long id = Long.parseLong(idParam);
                        exchange.getIn().setHeader("providerId", id);
                    } catch (NumberFormatException e) {
                        exchange.getMessage().setBody(new ResponseMessage("Invalid provider ID format"));
                        exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
                        log.error("Invalid ID format: " + idParam, e);
                    }
                })
                .choice()
                .when(header(Exchange.HTTP_RESPONSE_CODE).isNull())
                .bean(providerService, "updateProvider(${header.providerId}, ${body})")
                .process(exchange -> {
                    ProviderDTO result = exchange.getIn().getBody(ProviderDTO.class);
                    exchange.getMessage().setBody(result);
                })
                .process(logResponseSentToUser("Provider updated successfully"))
                .end()
                .process(cleanupLogging());

        // Delete Provider - Proper Camel Way
        from("direct:deleteProvider")
                .routeId("deleteProvider")
                .process(initializeLogging())
                .process(logRequestReceived("DELETE /provider/delete/{id}"))
                .process(exchange -> {
                    Long id = exchange.getIn().getHeader("id", Long.class);
                    logInfo("Processing delete request for provider ID: " + id);
                })
                .process(logRequestSentToService("ProviderRepository.deleteProviderById"))
                .process(exchange -> {
                    Long id = exchange.getIn().getHeader("id", Long.class);
                    boolean deleted = providerRepository.deleteProviderById(id);
                    exchange.getMessage().setBody(deleted);
                    exchange.getIn().setHeader("deleteResult", deleted);
                })
                .process(logResponseReceivedFromService("ProviderRepository.deleteProviderById"))
                .choice()
                .when(header("deleteResult").isEqualTo(true))
                .process(exchange -> {
                    Long id = exchange.getIn().getHeader("id", Long.class);
                    exchange.getMessage().setBody(new ResponseMessage("Provider deleted successfully"));
                    logInfo("Provider deleted successfully, ID: " + id);
                })
                .otherwise()
                .process(exchange -> {
                    Long id = exchange.getIn().getHeader("id", Long.class);
                    exchange.getMessage().setBody(new ResponseMessage("Provider not found"));
                    exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
                    logInfo("Provider not found for deletion, ID: " + id);
                })
                .end()
                .process(logResponseSentToUser("Delete operation completed"))
                .process(cleanupLogging());

        // Get Provider by ID - Proper Camel Way
        from("direct:getProviderById")
                .routeId("getProviderById")
                .process(initializeLogging())
                .process(logRequestReceived("GET /provider/{id}"))
                .process(exchange -> {
                    String idHeader = exchange.getIn().getHeader("id", String.class);
                    logInfo("Processing request for provider ID: " + idHeader);
                    exchange.getIn().setHeader("idToProcess", idHeader);
                })
                .choice()
                .when(header("idToProcess").isEqualToIgnoreCase("all"))
                .process(logRequestSentToService("ProviderRepository.getAllProviders"))
                .process(exchange -> {
                    Object result = providerRepository.getAllProviders();
                    exchange.getMessage().setBody(result);
                    logInfo("All providers retrieved via /provider/all endpoint");
                })
                .process(logResponseReceivedFromService("ProviderRepository.getAllProviders"))
                .otherwise()
                .process(exchange -> {
                    String idHeader = exchange.getIn().getHeader("idToProcess", String.class);
                    try {
                        Long id = Long.parseLong(idHeader);
                        exchange.getIn().setHeader("parsedId", id);
                        exchange.getIn().setHeader("validId", true);
                    } catch (NumberFormatException e) {
                        exchange.getMessage().setBody(new ResponseMessage("Invalid provider ID: " + idHeader));
                        exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
                        exchange.getIn().setHeader("validId", false);
                        logError("Invalid provider ID format: " + idHeader, e);
                    }
                })
                .choice()
                .when(header("validId").isEqualTo(false))
                // Error message already set above, just log
                .process(exchange -> {
                    logInfo("Returning error response for invalid ID format");
                })
                .otherwise()
                .process(logRequestSentToService("ProviderRepository.findProviderById"))
                .process(exchange -> {
                    Long id = exchange.getIn().getHeader("parsedId", Long.class);
                    Provider provider = providerRepository.findProviderById(id);
                    exchange.getMessage().setBody(provider);
                    exchange.getIn().setHeader("foundProvider", provider);
                })
                .process(logResponseReceivedFromService("ProviderRepository.findProviderById"))
                .choice()
                .when(header("foundProvider").isNull())
                .process(exchange -> {
                    Long id = exchange.getIn().getHeader("parsedId", Long.class);
                    exchange.getMessage().setBody(new ResponseMessage("Provider not found"));
                    exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
                    logInfo("Provider not found for ID: " + id);
                })
                .otherwise()
                .process(exchange -> {
                    Long id = exchange.getIn().getHeader("parsedId", Long.class);
                    logInfo("Provider found successfully for ID: " + id);
                })
                .end()
                .end()
                .end()
                .process(logResponseSentToUser("Provider data retrieval completed"))
                .process(cleanupLogging());
    }

    // Initialize correlation ID and MDC for logging
    private Processor initializeLogging() {
        return exchange -> {
            String correlationId = exchange.getIn().getHeader("X-Correlation-ID", String.class);
            if (correlationId == null || correlationId.isBlank()) {
                correlationId = UUID.randomUUID().toString();
                exchange.getIn().setHeader("X-Correlation-ID", correlationId);
            }

            // Set MDC for structured logging
            MDC.put("correlationId", correlationId);
            MDC.put("timestamp", FORMATTER.format(ZonedDateTime.now()));
            MDC.put("routeId", exchange.getFromRouteId());
        };
    }

    // Clean up MDC after processing
    private Processor cleanupLogging() {
        return exchange -> {
            MDC.clear();
        };
    }

    // Log request received from user
    private Processor logRequestReceived(String endpoint) {
        return exchange -> {
            String correlationId = exchange.getIn().getHeader("X-Correlation-ID", String.class);
            String timestamp = FORMATTER.format(ZonedDateTime.now());
            Object requestBody = exchange.getIn().getBody();
            String bodyStr = formatBody(requestBody);
            String logMessage = String.format("[%s] [%s] REQUEST_RECEIVED_FROM_USER - Endpoint: %s - Body: %s",
                    timestamp, correlationId, endpoint, bodyStr);
            System.out.println(logMessage);
        };
    }

    // Log request sent to service/repository
    private Processor logRequestSentToService(String serviceName) {
        return exchange -> {
            String correlationId = exchange.getIn().getHeader("X-Correlation-ID", String.class);
            String timestamp = FORMATTER.format(ZonedDateTime.now());
            Object requestBody = exchange.getIn().getBody();
            String bodyStr = formatBody(requestBody);
            String logMessage = String.format("[%s] [%s] REQUEST_SENT_TO_SERVICE - Service: %s - Body: %s",
                    timestamp, correlationId, serviceName, bodyStr);
            System.out.println(logMessage);
        };
    }

    // Log response received from service/repository
    private Processor logResponseReceivedFromService(String serviceName) {
        return exchange -> {
            String correlationId = exchange.getIn().getHeader("X-Correlation-ID", String.class);
            String timestamp = FORMATTER.format(ZonedDateTime.now());
            Object responseBody = exchange.getIn().getBody();
            String bodyStr = formatBody(responseBody);
            String logMessage = String.format("[%s] [%s] RESPONSE_RECEIVED_FROM_SERVICE - Service: %s - Body: %s",
                    timestamp, correlationId, serviceName, bodyStr);
            System.out.println(logMessage);
        };
    }

    // Log response sent to user
    private Processor logResponseSentToUser(String message) {
        return exchange -> {
            String correlationId = exchange.getIn().getHeader("X-Correlation-ID", String.class);
            String timestamp = FORMATTER.format(ZonedDateTime.now());
            Object responseBody = exchange.getIn().getBody();
            String bodyStr = formatBody(responseBody);
            String logMessage = String.format("[%s] [%s] RESPONSE_SENT_TO_USER - Message: %s - Body: %s",
                    timestamp, correlationId, message, bodyStr);
            System.out.println(logMessage);
        };
    }

    // Generic info logging
    private void logInfo(String message) {
        String correlationId = MDC.get("correlationId");
        String timestamp = FORMATTER.format(ZonedDateTime.now());
        String logMessage = String.format("[%s] [%s] INFO - %s", timestamp, correlationId, message);
        System.out.println(logMessage);
    }

    // Generic error logging
    private void logError(String message, Exception e) {
        String correlationId = MDC.get("correlationId");
        String timestamp = FORMATTER.format(ZonedDateTime.now());
        String logMessage = String.format("[%s] [%s] ERROR - %s - Exception: %s",
                timestamp, correlationId, message, e.getMessage());
        System.out.println(logMessage);
    }

    // Format body for logging
    private String formatBody(Object body) {
        if (body == null) {
            return "null";
        }

        // Handle different object types
        if (body instanceof ProviderDTO) {
            ProviderDTO dto = (ProviderDTO) body;
            String channels = dto.getSupportedChannels() != null ?
                    java.util.Arrays.toString(dto.getSupportedChannels()) : "null";
            String slaStr = dto.getSla() != null ?
                    String.format("SLADTO{deliveryTimeMs=%s, uptimePercent=%s}",
                            dto.getSla().getDeliveryTimeMs(), dto.getSla().getUptimePercent()) : "null";
            return String.format("ProviderDTO{partnerId='%s', name='%s', contactInfo='%s', supportedChannels=%s, sla=%s}",
                    dto.getPartnerId(), dto.getName(), dto.getContactInfo(), channels, slaStr);
        }

        if (body instanceof Provider) {
            Provider provider = (Provider) body;
            String channels = provider.getSupportedChannels() != null ?
                    java.util.Arrays.toString(provider.getSupportedChannels()) : "null";
            String slaStr = provider.getSla() != null ?
                    String.format("SLA{deliveryTimeMs=%s, uptimePercent=%s}",
                            provider.getSla().getDeliveryTimeMs(), provider.getSla().getUptimePercent()) : "null";
            return String.format("Provider{id=%s, partnerId='%s', name='%s', contactInfo='%s', supportedChannels=%s, sla=%s}",
                    provider.getId(), provider.getPartnerId(), provider.getName(),
                    provider.getContactInfo(), channels, slaStr);
        }

        if (body instanceof ResponseMessage) {
            ResponseMessage response = (ResponseMessage) body;
            return String.format("ResponseMessage{message='%s'}", response.getMessage());
        }

        // For lists or other complex objects
        if (body instanceof java.util.List) {
            java.util.List<?> list = (java.util.List<?>) body;
            return String.format("List[size=%d, elements=%s]", list.size(),
                    list.size() <= 3 ? list.toString() : list.subList(0, 3) + "...");
        }

        // For arrays
        if (body.getClass().isArray()) {
            if (body instanceof String[]) {
                return java.util.Arrays.toString((String[]) body);
            }
            return java.util.Arrays.toString((Object[]) body);
        }

        // Default toString with size limit
        String bodyStr = body.toString();
        return bodyStr.length() > 500 ? bodyStr.substring(0, 500) + "..." : bodyStr;
    }
}