package library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeTypeUtils;

import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessage;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import library.domain.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReturnBookStatusPolicyTest {

   private static final Logger LOGGER = LoggerFactory.getLogger(ReturnBookStatusPolicyTest.class);
   
   @Autowired
   private MessageCollector messageCollector;
   @Autowired
   private ApplicationContext applicationContext;

   @Autowired
   private MessageVerifier<Message<?>> messageVerifier;


   @Test
   @SuppressWarnings("unchecked")
   public void test0() {

      //given:

      entity.setBookId("1");
      entity.setTitle("흑설공주");
      entity.setIsbn("1234567890");
      entity.setAuthor("작가1");
      entity.setPublisher("출판사1");
      entity.setBookHistory([object Object]);
      entity.setBookCategory("FICTION");
      entity.setBookStatus("LOANED");

      repository.save(entity);

      //when:  
      
      LoanReturned event = new LoanReturned();

      event.setReturnDate("2022-02-01");
      event.setStatus("RETURNED");
   
   
   LibraryManagementApplication.applicationContext = applicationContext;

      ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      try {
         this.messageVerifier.send(MessageBuilder
                .withPayload(event)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .setHeader("type", event.getEventType())
                .build(), "library");

         //then:
         Message<?> receivedMessage = this.messageVerifier.receive("library", 5000, TimeUnit.MILLISECONDS);
         assertNotNull("Resulted event must be published", receivedMessage);

         String receivedPayload = (String) receivedMessage.getPayload();
         BookStatusChanged outputEvent = objectMapper.readValue(receivedPayload, BookStatusChanged.class);


         LOGGER.info("Response received: {}", outputEvent);

         assertEquals(outputEvent.getPreviousStatus(), "LOANED");
         assertEquals(outputEvent.getNewStatus(), "AVAILABLE");
         assertEquals(outputEvent.getChangedAt(), "2022-02-01");


      } catch (JsonProcessingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         assertTrue(e.getMessage(), false);
      }

     
   }

}
