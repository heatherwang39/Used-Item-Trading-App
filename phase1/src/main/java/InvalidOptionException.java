package main.java;

/**
 * Exception to be thrown when an the input option does not match any of the given options.
 * @author Sarah Aliakbari
 * @version %I%, %G%
 * @since Phase 1
 */
public class InvalidOptionException extends Exception {

    private String message = "Invalid Option detected. Please try again.";

    /**
     * Class Constructor
     */
    public InvalidOptionException(){
        super();
    }

    public InvalidOptionException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

//TODO: might delete these extra methods and replace with toString method if it's correct to do so.