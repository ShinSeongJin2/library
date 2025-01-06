package library.domain;

import java.time.LocalDate;
import java.util.*;
import library.domain.*;
import library.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookDisposed extends AbstractEvent {

    private String disposeReason;
    private Date disposedAt;

    public BookDisposed(Book aggregate) {
        super(aggregate);
    }

    public BookDisposed() {
        super();
    }
}
//>>> DDD / Domain Event
