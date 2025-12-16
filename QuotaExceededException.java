package admissions;

public class QuotaExceededException extends RuntimeException {
    public QuotaExceededException(String msg) {
        super(msg);
    }
}

//This exception is used when the seat limit for a category is already full. 
//It helps move extra students to the waitlist smoothly.