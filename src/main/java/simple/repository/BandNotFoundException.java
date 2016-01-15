package simple.repository;

public class BandNotFoundException extends RuntimeException {

    public BandNotFoundException(String message) {
        super(message);
    }
}
