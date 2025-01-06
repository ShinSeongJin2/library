package library.domain;

import java.util.*;
import library.domain.*;
import library.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class LoanReturned extends AbstractEvent {

    private Date returnDate;
    private Object status;
}
