package library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import library.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessage;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierObjectMapper;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeTypeUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMessageVerifier
public class ChangeBookStatusTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(
        ChangeBookStatusTest.class
    );

    @Autowired
    private MessageCollector messageCollector;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MessageVerifier<Message<?>> messageVerifier;

    @Autowired
    public BookRepository repository;

    @Test
    @SuppressWarnings("unchecked")
    public void test0() {
        //given:
        Book entity = new Book();

        entity.setBookId("BOOK-001");
        entity.setTitle("스프링 부트 강의");
        entity.setIsbn("N/A");
        entity.setAuthor("N/A");
        entity.setPublisher("N/A");
        entity.setCategory("N/A");
        entity.setStatus("대출가능");
        entity.setBookHistory("N/A");

        repository.save(entity);

        //when:
        try {
            ChangeBookStatusCommand command = new ChangeBookStatusCommand();

            command.setStatus("대출중");

            entity.changeBookStatus(command);

            //then:
            this.messageVerifier.send(
                    MessageBuilder
                        .withPayload(entity)
                        .setHeader(
                            MessageHeaders.CONTENT_TYPE,
                            MimeTypeUtils.APPLICATION_JSON
                        )
                        .build(),
                    "library"
                );

            Message<?> receivedMessage =
                this.messageVerifier.receive(
                        "library",
                        5000,
                        TimeUnit.MILLISECONDS
                    );
            assertNotNull("Resulted event must be published", receivedMessage);

            String receivedPayload = (String) receivedMessage.getPayload();

            BookStatusChanged outputEvent = objectMapper.readValue(
                receivedPayload,
                BookStatusChanged.class
            );

            LOGGER.info("Response received: {}", outputEvent);

            assertEquals(outputEvent.getPreviousStatus(), "대출가능");
            assertEquals(outputEvent.getNewStatus(), "대출중");
            assertEquals(outputEvent.getChangedAt(), "2024-03-20T00:00:00Z");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            assertTrue(e.getMessage(), false);
        }
    }
}
