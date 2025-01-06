package library.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import library.LoanManagementApplication;
import lombok.Data;

@Entity
@Table(name = "Loan_table")
@Data
//<<< DDD / Aggregate Root
public class Loan {

    @Id
    private String loanId;

    @Embedded
    private Member member;

    @Embedded
    private LoanPeriod loanPeriod;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @Embedded
    private BookId bookId;

    public static LoanRepository repository() {
        LoanRepository loanRepository = LoanManagementApplication.applicationContext.getBean(
            LoanRepository.class
        );
        return loanRepository;
    }

    //<<< Clean Arch / Port Method
    public void createLoan(CreateLoanCommand createLoanCommand) {
        //implement business logic here:

        LoanCreated loanCreated = new LoanCreated(this);
        loanCreated.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void returnLoan(ReturnLoanCommand returnLoanCommand) {
        //implement business logic here:

        LoanReturned loanReturned = new LoanReturned(this);
        loanReturned.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void createReservation(
        CreateReservationCommand createReservationCommand
    ) {
        //implement business logic here:

        ReservationCreated reservationCreated = new ReservationCreated(this);
        reservationCreated.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
