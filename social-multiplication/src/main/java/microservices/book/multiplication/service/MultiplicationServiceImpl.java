package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MultiplicationServiceImpl implements MultiplicationService{

    private RandomGeneratorService randomGeneratorService;

    private MultiplicationResultAttemptRepository attemptRepository;

    private UserRepository userRepository;

    public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorServiceArg,
                                     MultiplicationResultAttemptRepository mAttemptRepository,
                                     UserRepository mUserRepository){
        this.randomGeneratorService = randomGeneratorServiceArg;
        this.attemptRepository = mAttemptRepository;
        this.userRepository = mUserRepository;
    }

    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new Multiplication(factorA, factorB);
    }

    /**
     * This method will check what user has entered if it is equal to actual multiplication
     * result.
     * */
    @Transactional
    @Override
    public boolean checkAttempt(MultiplicationResultAttempt resultAttempt) {

        Optional<User> user = userRepository.findByAlias(resultAttempt.getUser().getAlias());

        boolean isCorrect = resultAttempt.getResultAttempt() ==
                resultAttempt.getMultiplication().getFactorA() * resultAttempt.getMultiplication().getFactorB();

        //Avoids hack attempts
        Assert.isTrue(!resultAttempt.isCorrect(), "You can't send an attempt marked as correct!!");

        //Create a copy of MultiplicationResultAttempt from original & append the correct value.
        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(user.orElse(resultAttempt.getUser()), resultAttempt.getMultiplication(), resultAttempt.getResultAttempt(), isCorrect);

        //Save the attempt
        attemptRepository.save(checkedAttempt);

        return isCorrect;
    }

    @Override
    public List<MultiplicationResultAttempt> getStatsForUser(String userAlias) {
        return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
    }
}
