package library.domain;

import java.util.*;
import library.domain.*;
import library.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class LoanCreated extends AbstractEvent {

    private String memberId;
    private Date loanDate;
    private Date returnDueDate;
    private Object status;
}
