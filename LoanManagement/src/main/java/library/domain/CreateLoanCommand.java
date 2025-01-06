package library.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class CreateLoanCommand {

    private String memberId;
    private Date loanDate;
    private Date returnDueDate;
}
