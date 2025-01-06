package library.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import library.LibraryManagementApplication;
import lombok.Data;

@Entity
@Table(name = "Book_table")
@Data
//<<< DDD / Aggregate Root
public class Book {

    @Id
    private String bookId;

    private String title;

    private String isbn;

    private String author;

    private String publisher;

    private BookHistory bookHistory;

    @Enumerated(EnumType.STRING)
    private BookCategory bookCategory;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    public static BookRepository repository() {
        BookRepository bookRepository = LibraryManagementApplication.applicationContext.getBean(
            BookRepository.class
        );
        return bookRepository;
    }

    //<<< Clean Arch / Port Method
    public void registerBook(RegisterBookCommand registerBookCommand) {
        //implement business logic here:

        BookRegistered bookRegistered = new BookRegistered(this);
        bookRegistered.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void changeBookStatus(
        ChangeBookStatusCommand changeBookStatusCommand
    ) {
        //implement business logic here:

        BookStatusChanged bookStatusChanged = new BookStatusChanged(this);
        bookStatusChanged.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void disposeBook(DisposeBookCommand disposeBookCommand) {
        //implement business logic here:

        BookDisposed bookDisposed = new BookDisposed(this);
        bookDisposed.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    // 비즈니스 로직 추가
    public void returnBook(LoanReturned loanReturned) {
        this.bookStatus = BookStatus.AVAILABLE;
        // 필요 시 더 많은 비즈니스 로직 추가
        BookStatusChanged bookStatusChanged = new BookStatusChanged(this);
        bookStatusChanged.setPreviousStatus(BookStatus.LOANED);
        bookStatusChanged.setNewStatus(BookStatus.AVAILABLE);
        bookStatusChanged.setChangedAt(loanReturned.getReturnDate());
        bookStatusChanged.publishAfterCommit();
    }
}
//>>> DDD / Aggregate Root
