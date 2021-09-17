package com.ynero.ss.senderservice.services;

import com.ynero.ss.senderservice.domain.DeviceSendingData;
import com.ynero.ss.senderservice.domain.TenantSendingData;
import com.ynero.ss.senderservice.persistence.DeviceSendingDataRepository;
import com.ynero.ss.senderservice.persistence.PortSendingDataRepository;
import com.ynero.ss.senderservice.persistence.TenantSendingDataRepository;
import com.ynero.ss.senderservice.senders.DefaultSender;
import domain.SendingParameters;
import dtos.ResultDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureDataMongo
@ActiveProfiles("integration-test")
@Testcontainers
@DirtiesContext
public class SendingDataSeekerTest {

    public static final String MONGO_VERSION = "4.4.4";

    @Autowired
    protected ReactiveMongoOperations mongo;

    @Autowired
    @InjectMocks
    private SendingDataSeeker seeker;

    @Mock
    private DefaultSender sender;

    @Autowired
    private DeviceSendingDataRepository deviceSendingDataRepository;

    @Autowired
    private PortSendingDataRepository portSendingDataRepository;

    @Autowired
    private TenantSendingDataRepository tenantSendingDataRepository;

    @Container
    protected static final MongoDBContainer MONGO_CONTAINER = new MongoDBContainer("mongo:" + MONGO_VERSION);

    @DynamicPropertySource
    protected static void mongoProperties(DynamicPropertyRegistry reg) {
        reg.add("spring.data.mongodb.uri", () -> {
            return MONGO_CONTAINER.getReplicaSetUrl();
        });
    }

    @AfterEach
    protected void cleanupAllDataInDb() {
        StepVerifier
                .create(mongo.getCollectionNames()
                        .flatMap(col -> mongo.remove(new Query(), col))
                        .collectList()
                )
                .expectNextCount(1L)
                .verifyComplete();
    }

    String portName;
    UUID deviceId;
    UUID pipelineId;
    String tenantId;
    Float value;
    UUID deviceDataId;
    UUID tenantDataId;
    ResultDTO resultDto;
    DeviceSendingData deviceSendingData;
    TenantSendingData tenantSendingData;
    SendingParameters sendingParametersForDevice;
    SendingParameters sendingParametersForTenant;

    @BeforeEach
    private void setUp() {
        portName = "humidity";
        deviceId = UUID.randomUUID();
        pipelineId = UUID.randomUUID();
        tenantId = "Cola";
        value = 31.5f;
        deviceDataId = UUID.randomUUID();
        tenantDataId = UUID.randomUUID();

        sendingParametersForDevice = new SendingParameters();
        sendingParametersForDevice.setSendingClassName("com.ynero.ss.senderservice.senders.DefaultSender");
        sendingParametersForDevice.setParameters(new HashMap<String, Object>(){{
            put("host", "localhost");
            put("port", "8080");
        }});

        sendingParametersForTenant = new SendingParameters();
        sendingParametersForTenant.setSendingClassName("com.ynero.ss.senderservice.senders.MySender");
        sendingParametersForTenant.setParameters(new HashMap<String, Object>(){{
            put("host", "localhost");
            put("port", "8080");
        }});

        resultDto = ResultDTO.builder()
                .deviceId(deviceId)
                .portName(portName)
                .pipelineId(pipelineId)
                .tenantId(tenantId)
                .value(value)
                .build();

        deviceSendingData = DeviceSendingData.builder()
                .deviceId(deviceId)
                .tenantId(tenantId)
                .dataId(deviceDataId)
                .parameters(sendingParametersForDevice)
                .build();
        tenantSendingData = TenantSendingData.builder()
                .tenantId(tenantId)
                .dataId(tenantDataId)
                .parameters(sendingParametersForTenant)
                .build();
        tenantSendingDataRepository.save(tenantSendingData);
        deviceSendingDataRepository.save(deviceSendingData);
    }

    @Test
    public void dataSeeker_FindDataAndInstanceNeededClass_WhenInstancedDataForDeviceAndTenant(){
        seeker.find(resultDto);
        Mockito.verify(sender, Mockito.times(1));
    }
}
