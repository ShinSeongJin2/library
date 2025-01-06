package library.domain;

import java.util.Date;
import lombok.Data;

@Data
public class LoanDetailsQuery {

    private Member member;
    private BookId bookId;
    private LoanPeriod loanPeriod;
    private LoanStatus status;
    private Date createdAt;
    private Date lastModifiedAt;
}
