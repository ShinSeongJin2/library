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
public class RegisterBookTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(
        RegisterBookTest.class
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

        entity.setBookId(null);
        entity.setTitle(null);
        entity.setIsbn(null);
        entity.setAuthor(null);
        entity.setPublisher(null);
        entity.setCategory(null);
        entity.setStatus(null);
        entity.setBookHistory("N/A");

        repository.save(entity);

        //when:
        try {
            RegisterBookCommand command = new RegisterBookCommand();

            command.setTitle("스프링 부트 강의");
            command.setIsbn("9781234567890");
            command.setAuthor("홍길동");
            command.setPublisher("테크출판사");
            command.setCategory("학술");

            entity.registerBook(command);

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

            BookRegistered outputEvent = objectMapper.readValue(
                receivedPayload,
                BookRegistered.class
            );

            LOGGER.info("Response received: {}", outputEvent);

            assertEquals(outputEvent.getTitle(), "스프링 부트 강의");
            assertEquals(outputEvent.getIsbn(), "9781234567890");
            assertEquals(outputEvent.getAuthor(), "홍길동");
            assertEquals(outputEvent.getPublisher(), "테크출판사");
            assertEquals(outputEvent.getCategory(), "학술");
            assertEquals(outputEvent.getStatus(), "대출가능");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            assertTrue(e.getMessage(), false);
        }
    }
}
