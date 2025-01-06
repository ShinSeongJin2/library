package library.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class CreateReservationCommand {

    private String memberId;
    private Date reservationDate;
}
