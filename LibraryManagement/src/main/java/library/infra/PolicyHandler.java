package library.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import library.config.kafka.KafkaProcessor;
import library.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='LoanCreated'"
    )
    public void wheneverLoanCreated_LoanBookStatusPolicy(
        @Payload LoanCreated loanCreated
    ) {
        LoanCreated event = loanCreated;
        System.out.println(
            "\n\n##### listener LoanBookStatusPolicy : " + loanCreated + "\n\n"
        );
        // Comments //
        //도서 대출 시 자동으로 상태를 '대출중'으로 전환

        // Sample Logic //

    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='LoanReturned'"
    )
    public void wheneverLoanReturned_ReturnBookStatusPolicy(
        @Payload LoanReturned loanReturned
    ) {
        LoanReturned event = loanReturned;
        System.out.println(
            "\n\n##### listener ReturnBookStatusPolicy : " +
            loanReturned +
            "\n\n"
        );

        // Comments //
        //도서 반납 시 자동으로 상태를 '대출가능'으로 전환. 작가명, 출판사명은 임의의 FICTION 카테고리의 책을 활용할 것.

        // Sample Logic //
        Book book = Book
            .repository()
            .findById(loanReturned.getBookId())
            .orElseThrow(() -> new Exception("No Entity Found"));
        book.returnBook(loanReturned);
    }
}
//>>> Clean Arch / Inbound Adaptor
