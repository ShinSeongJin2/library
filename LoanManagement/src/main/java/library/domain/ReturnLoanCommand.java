package library.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class ReturnLoanCommand {

    private Date returnDate;
}
