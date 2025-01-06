package library.domain;

import java.util.Date;
import java.util.List;
import library.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository
    extends PagingAndSortingRepository<Book, String> {
    @Query(
        value = "select book " +
        "from Book book " +
        "where(:title is null or book.title like %:title%) and (:actionDate is null or book.actionDate = :actionDate) and (:actionType is null or book.actionType like %:actionType%) and (:status is null or book.status = :status)"
    )
    List<Book> bookHistory(
        String title,
        Date actionDate,
        String actionType,
        BookStatus status,
        Pageable pageable
    );
}
