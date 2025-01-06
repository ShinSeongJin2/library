package library.domain;

import java.util.Date;
import lombok.Data;

@Data
public class LoanSummaryQuery {

    private String bookTitle;
    private Date loanDate;
    private Date returnDueDate;
    private LoanStatus status;
}
