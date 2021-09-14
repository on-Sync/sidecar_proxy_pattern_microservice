package com.putstack.user_service_command.config;

import java.util.ArrayList;
import java.util.List;

import com.putstack.user_service_command.aggregate.UserAggregate;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.common.caching.Cache;
import org.axonframework.common.caching.WeakReferenceCache;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.AggregateSnapshotter;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.GenericAggregateFactory;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.Repository;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j2;

@Configuration
@AutoConfigureAfter(AxonAutoConfiguration.class)
@Log4j2
public class AxonConfig {

    private final static int SNAPSHOT_THRESHOLD = 5; 
    

    // @Bean
	// @Primary
    // public CapabilityDiscoveryMode myCapabilityDiscoveryMode(RestTemplate restTemplate, Serializer serializer) {
    //     return RestCapabilityDiscoveryMode.builder()
    //                                       .restTemplate(restTemplate)
    //                                       .serializer(serializer)
    //                                       // Allows changing the endpoint used to find member capabilities
    //                                     //   .messageCapabilitiesEndpoint(/* custom message information endpoint */)
    //                                     //   .messageCapabilitiesEndpoint("/message-routing-information")
    //                                       .build();
    // }

    // @Bean
    // public CommandBusConnector springHttpCommandBusConnector(@Qualifier("localSegment") CommandBus localSegment,
    //                                                          RestOperations restOperations,
    //                                                          Serializer serializer) {
    //     return SpringHttpCommandBusConnector.builder()
    //                                         .localCommandBus(localSegment)
    //                                         .restOperations(restOperations)
    //                                         .serializer(serializer)
    //                                         // .executor(/* custom Executor */)
    //                                         .build();
    // }
    
    // @Bean
    // public CommandRouter springCloudCommandRouter(DiscoveryClient discoveryClient,
    //                                               Registration localServiceInstance,
    //                                               CapabilityDiscoveryMode myCapabilityDiscoveryMode) {
    //     return SpringCloudCommandRouter.builder()
    //                                    .discoveryClient(discoveryClient)
    //                                    .routingStrategy(new AnnotationRoutingStrategy())
    //                                    .localServiceInstance(localServiceInstance)
    //                                    .capabilityDiscoveryMode(myCapabilityDiscoveryMode)
    //                                 //    .serviceInstanceFilter(/* custom ServiceInstance filter */)
    //                                 //    .consistentHashChangeListener(/* ConsistentHash change listener */)
    //                                    .build();
    // }

    // @Bean
    // @Primary
    // public DistributedCommandBus distributedCommandBus(CommandRouter commandRouter,
    //                                                    CommandBusConnector commandBusConnector) {
    //     return DistributedCommandBus.builder()
    //                                 .commandRouter(commandRouter)
    //                                 .connector(commandBusConnector)
    //                                 .build();
    // }

    // ----

    @Bean
    @Qualifier("localSegment")
    public CommandBus commandBus(TransactionManager transactionManager) {
        log.debug("config {}", "AxonServerCommandBus -> SimpleCommandBus");
        return SimpleCommandBus.builder()
            .transactionManager(transactionManager)
            .build();
    }

    @Bean
    public List<AggregateFactory<?>> aggregateFactories(){
        List<AggregateFactory<?>> aggregateFactories = new ArrayList<AggregateFactory<?>>();
        // INFO: add Aggregate.class to list
        aggregateFactories.add(new GenericAggregateFactory<>(UserAggregate.class));
        return aggregateFactories;
    }

    @Bean
    public Snapshotter snapshotter(EventStore eventStore, TransactionManager transactionManager){

        List<AggregateFactory<?>> aggregateFactories = aggregateFactories();

        return AggregateSnapshotter
                .builder()
                .eventStore(eventStore)
                .aggregateFactories(aggregateFactories)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public SnapshotTriggerDefinition snapshotTriggerDefinition(Snapshotter snapshotter){
        return new EventCountSnapshotTriggerDefinition(snapshotter, SNAPSHOT_THRESHOLD);
    }

    @Bean // INFO: Snapshot repository - for custom aggregate
    public Repository<UserAggregate> catalogAggregateRepository(EventStore eventStore, SnapshotTriggerDefinition snapshotTriggerDefinition){
        return EventSourcingRepository
                .builder(UserAggregate.class)
                .eventStore(eventStore)
                .snapshotTriggerDefinition(snapshotTriggerDefinition)
                .build();
    }

    @Bean // INFO: Enhance read functionality by axon cache
    public Cache cache(){
        return new WeakReferenceCache();
    }

}
