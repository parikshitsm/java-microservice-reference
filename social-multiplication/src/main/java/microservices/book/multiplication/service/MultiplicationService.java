package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;

import java.util.List;

public interface MultiplicationService {

    /**
     * Creates a multiplication object with two randomly generated
     * factors between 11 and 99
     *
     * @return a Multiplication object with random factors.
     *
     * */
    Multiplication createRandomMultiplication();

    /**
     * Check the attempt given by {@link microservices.book.multiplication.domain.User} if its correct or wrong.
     * */
    boolean checkAttempt(MultiplicationResultAttempt resultAttempt);

    /**
     * Get recent attempts of user based on user alias
     * */
    List<MultiplicationResultAttempt> getStatsForUser(String userAlias);
}
