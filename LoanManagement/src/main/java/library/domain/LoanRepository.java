package library.domain;

import java.util.Date;
import java.util.List;
import library.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "loans", path = "loans")
public interface LoanRepository
    extends PagingAndSortingRepository<Loan, String> {
    @Query(
        value = "select loan " +
        "from Loan loan " +
        "where(:bookTitle is null or loan.bookTitle like %:bookTitle%) and (:loanDate is null or loan.loanDate = :loanDate) and (:returnDueDate is null or loan.returnDueDate = :returnDueDate) and (:status is null or loan.status = :status)"
    )
    List<Loan> loanSummary(
        String bookTitle,
        Date loanDate,
        Date returnDueDate,
        LoanStatus status,
        Pageable pageable
    );

    @Query(
        value = "select loan " +
        "from Loan loan " +
        "where(:member is null or loan.member = :member) and (:bookId is null or loan.bookId = :bookId) and (:loanPeriod is null or loan.loanPeriod = :loanPeriod) and (:status is null or loan.status = :status) and (:createdAt is null or loan.createdAt = :createdAt) and (:lastModifiedAt is null or loan.lastModifiedAt = :lastModifiedAt)"
    )
    Loan loanDetails(
        Member member,
        BookId bookId,
        LoanPeriod loanPeriod,
        LoanStatus status,
        Date createdAt,
        Date lastModifiedAt
    );
}
