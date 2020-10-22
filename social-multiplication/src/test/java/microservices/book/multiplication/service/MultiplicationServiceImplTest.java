package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

public class MultiplicationServiceImplTest {

    @Mock
    private RandomGeneratorService randomGeneratorService;

    private MultiplicationServiceImpl multiplicationServiceImpl;

    @Mock
    private MultiplicationResultAttemptRepository attemptRepository;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp(){
        //initMocks
        MockitoAnnotations.initMocks(this);
        multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService, attemptRepository, userRepository);
    }

    @Test
    public void createRandomMultiplicationTest(){

        //given(or mocked randomgenerator service will return first 50 and then 30
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

        //when
        Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();

        //then
        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
    }

    @Test
    public void checkCorrectAttemptTest(){

        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("springdeveloper");
        int userAttempt = 3000;
        MultiplicationResultAttempt requestAttempt = new MultiplicationResultAttempt(user, multiplication, userAttempt, false);
        MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(user, multiplication, userAttempt, true);
        given(userRepository.findByAlias("springdeveloper")).willReturn(Optional.empty());

        //when
        boolean checkAttemptResult = multiplicationServiceImpl.checkAttempt(requestAttempt);

        //assert
        assertThat(checkAttemptResult).isTrue();
        verify(attemptRepository).save(verifiedAttempt);
    }

    @Test
    public void checkWrongAttemptTest(){

        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("awsdeveloper");
        int userAttempt = 3010;
        MultiplicationResultAttempt requestAttempt = new MultiplicationResultAttempt(user, multiplication, userAttempt, false);

        //when
        boolean checkAttemptResult = multiplicationServiceImpl.checkAttempt(requestAttempt);

        //assert
        assertThat(checkAttemptResult).isFalse();
        verify(attemptRepository).save(requestAttempt);
    }

    @Test
    public void retrieveStatsTest(){
        User user = new User("javadev");
        Multiplication multiplication = new Multiplication(50, 60);
        MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt(user, multiplication, 3100, false);
        MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt(user, multiplication, 3500, false);
        MultiplicationResultAttempt attempt3 = new MultiplicationResultAttempt(user, multiplication, 3000, false);

        List<MultiplicationResultAttempt> latestAttempts = Lists.newArrayList(attempt1, attempt2, attempt3);

        given(userRepository.findByAlias("javadev")).willReturn(Optional.empty());
        given(attemptRepository.findTop5ByUserAliasOrderByIdDesc("javadev")).willReturn(latestAttempts);

        //when
        List<MultiplicationResultAttempt> latestAttemptsResult = multiplicationServiceImpl.getStatsForUser("javadev");

        //then
        assertThat(latestAttemptsResult).isEqualTo(latestAttempts);

    }
}
