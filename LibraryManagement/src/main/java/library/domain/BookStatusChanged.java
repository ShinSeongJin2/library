package library.domain;

import java.time.LocalDate;
import java.util.*;
import library.domain.*;
import library.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookStatusChanged extends AbstractEvent {

    private BookStatus previousStatus;
    private BookStatus newStatus;
    private Date changedAt;

    public BookStatusChanged(Book aggregate) {
        super(aggregate);
    }

    public BookStatusChanged() {
        super();
    }
}
//>>> DDD / Domain Event
