package library.domain;

import java.time.LocalDate;
import java.util.*;
import library.domain.*;
import library.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class LoanCreated extends AbstractEvent {

    private String memberId;
    private Date loanDate;
    private Date returnDueDate;
    private LoanStatus status;

    public LoanCreated(Loan aggregate) {
        super(aggregate);
    }

    public LoanCreated() {
        super();
    }
}
//>>> DDD / Domain Event
