
package library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;

import javax.inject.Inject;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessage;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import library.domain.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMessageVerifier
public class CreateLoanTest {

   private static final Logger LOGGER = LoggerFactory.getLogger(CreateLoanTest.class);
   
   @Autowired
   private MessageCollector messageCollector;
   
   @Autowired
   private ApplicationContext applicationContext;

   @Autowired
   ObjectMapper objectMapper;

   @Autowired
   private MessageVerifier<Message<?>> messageVerifier;

   @Autowired
   public LoanRepository repository;

   @Test
   @SuppressWarnings("unchecked")
   public void test0() {

      //given:  
      Loan entity = new Loan();

      entity.setLoanId(null);
      entity.setMember([object Object]);
      entity.setLoanPeriod([object Object]);
      entity.setStatus("대출가능");
      entity.setBookId([object Object]);

      repository.save(entity);

      //when:  
      try {


         CreateLoanCommand command = new CreateLoanCommand();

         command.setMemberId("MEM-001");
         command.setLoanDate("2023-11-01T00:00:00Z");
         command.setReturnDueDate("2023-11-08T00:00:00Z");
         
         entity.createLoan(command);
           

         //then:
         this.messageVerifier.send(MessageBuilder
                .withPayload(entity)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build(), "library");

         Message<?> receivedMessage = this.messageVerifier.receive("library", 5000, TimeUnit.MILLISECONDS);
         assertNotNull("Resulted event must be published", receivedMessage);

         String receivedPayload = (String) receivedMessage.getPayload();

         LoanCreated outputEvent = objectMapper.readValue(receivedPayload, LoanCreated.class);


         LOGGER.info("Response received: {}", outputEvent);
         
         assertEquals(outputEvent.getMemberId(), "MEM-001");
         assertEquals(outputEvent.getLoanDate(), "2023-11-01T00:00:00Z");
         assertEquals(outputEvent.getReturnDueDate(), "2023-11-08T00:00:00Z");
         assertEquals(outputEvent.getStatus(), "대출중");

      } catch (JsonProcessingException e) {
         e.printStackTrace();
         assertTrue(e.getMessage(), false);
      }

     
   }

}
