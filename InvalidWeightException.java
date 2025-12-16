package admissions;

public class InvalidWeightException extends Exception {
    public InvalidWeightException(String msg) {
        super(msg);
    }
}

//This exception is used when the scoring weights are wrong.
//Instead of the program crashing, it shows a proper error message.