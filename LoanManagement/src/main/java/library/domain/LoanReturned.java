package library.domain;

import java.time.LocalDate;
import java.util.*;
import library.domain.*;
import library.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class LoanReturned extends AbstractEvent {

    private Date returnDate;
    private LoanStatus status;

    public LoanReturned(Loan aggregate) {
        super(aggregate);
    }

    public LoanReturned() {
        super();
    }
}
//>>> DDD / Domain Event
