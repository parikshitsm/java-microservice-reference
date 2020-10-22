package microservices.book.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


/**
 * This class identifies an attempt from {@link User} to solve
 * a {@link Multiplication}
 * */
@RequiredArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
@Entity
public final class MultiplicationResultAttempt {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private final User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MULTIPLICATION_ID")
    private final Multiplication multiplication;

    private final int resultAttempt;
    private final boolean correct;

    MultiplicationResultAttempt(){
        this.user = null;
        this.multiplication = null;
        this.resultAttempt = -1;
        this.correct = false;
    }
}
