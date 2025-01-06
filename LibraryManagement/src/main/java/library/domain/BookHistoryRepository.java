package library.domain;

import library.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "bookhistories",
    path = "bookhistories"
)
public interface BookHistoryRepository
    extends PagingAndSortingRepository<BookHistory, Long> {}
