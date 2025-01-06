package library.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Entity
@Data
public class BookHistory {

    private Long historyId;

    private Date actionDate;

    private String actionType;

    private BookStatus bookStatus;
}
