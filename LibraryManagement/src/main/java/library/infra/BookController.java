package library.infra;
import library.domain.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/books")
@Transactional
public class BookController {
    @Autowired
    BookRepository bookRepository;

    @RequestMapping(value = "/books/registerbook",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Book registerBook(HttpServletRequest request, HttpServletResponse response, 
        @RequestBody RegisterBookCommand registerBookCommand) throws Exception {
            System.out.println("##### /book/registerBook  called #####");
            Book book = new Book();
            book.registerBook(registerBookCommand);
            bookRepository.save(book);
            return book;
    }
    @RequestMapping(value = "/books/{id}/",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8")
    public Book changeBookStatus(@PathVariable(value = "id")  id, @RequestBody ChangeBookStatusCommand changeBookStatusCommand, HttpServletRequest request, HttpServletResponse response) throws Exception {
            System.out.println("##### /book/changeBookStatus  called #####");
            Optional<Book> optionalBook = bookRepository.findById(id);
            
            optionalBook.orElseThrow(()-> new Exception("No Entity Found"));
            Book book = optionalBook.get();
            book.changeBookStatus(changeBookStatusCommand);
            
            bookRepository.save(book);
            return book;
            
    }
    @RequestMapping(value = "/books/{id}/",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8")
    public Book disposeBook(@PathVariable(value = "id")  id, @RequestBody DisposeBookCommand disposeBookCommand, HttpServletRequest request, HttpServletResponse response) throws Exception {
            System.out.println("##### /book/disposeBook  called #####");
            Optional<Book> optionalBook = bookRepository.findById(id);
            
            optionalBook.orElseThrow(()-> new Exception("No Entity Found"));
            Book book = optionalBook.get();
            book.disposeBook(disposeBookCommand);
            
            bookRepository.save(book);
            return book;
            
    }
}
//>>> Clean Arch / Inbound Adaptor
