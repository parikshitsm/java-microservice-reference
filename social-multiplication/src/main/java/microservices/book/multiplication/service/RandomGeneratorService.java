package microservices.book.multiplication.service;

public interface RandomGeneratorService {

    /**
     * @return a randomly generated factor. Its always a number
     * between 11 and 99.
     * */
    int generateRandomFactor();
}
