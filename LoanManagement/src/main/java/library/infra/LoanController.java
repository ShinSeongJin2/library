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
// @RequestMapping(value="/loans")
@Transactional
public class LoanController {
    @Autowired
    LoanRepository loanRepository;

    @RequestMapping(value = "/loans",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Loan createLoan(HttpServletRequest request, HttpServletResponse response, 
        @RequestBody CreateLoanCommand createLoanCommand) throws Exception {
            System.out.println("##### /loan/createLoan  called #####");
            Loan loan = new Loan();
            loan.createLoan(createLoanCommand);
            loanRepository.save(loan);
            return loan;
    }
    @RequestMapping(value = "/loans/{id}/",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8")
    public Loan returnLoan(@PathVariable(value = "id")  id, @RequestBody ReturnLoanCommand returnLoanCommand, HttpServletRequest request, HttpServletResponse response) throws Exception {
            System.out.println("##### /loan/returnLoan  called #####");
            Optional<Loan> optionalLoan = loanRepository.findById(id);
            
            optionalLoan.orElseThrow(()-> new Exception("No Entity Found"));
            Loan loan = optionalLoan.get();
            loan.returnLoan(returnLoanCommand);
            
            loanRepository.save(loan);
            return loan;
            
    }
    @RequestMapping(value = "/loans",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Loan createReservation(HttpServletRequest request, HttpServletResponse response, 
        @RequestBody CreateReservationCommand createReservationCommand) throws Exception {
            System.out.println("##### /loan/createReservation  called #####");
            Loan loan = new Loan();
            loan.createReservation(createReservationCommand);
            loanRepository.save(loan);
            return loan;
    }
}
//>>> Clean Arch / Inbound Adaptor
