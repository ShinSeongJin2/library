package library.domain;

import java.util.Date;
import lombok.Data;

@Data
public class BookHistoryQuery {

    private String title;
    private Date actionDate;
    private String actionType;
    private BookStatus status;
}
