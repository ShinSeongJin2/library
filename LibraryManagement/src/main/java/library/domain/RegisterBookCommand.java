package library.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class RegisterBookCommand {

    private String title;
    private String isbn;
    private String author;
    private String publisher;
    private BookCategory category;
}
