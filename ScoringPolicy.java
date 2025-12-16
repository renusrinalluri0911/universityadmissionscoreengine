package admissions;

public interface ScoringPolicy {
    double calculateScore(Applicant a);
}

//ScoringPolicy is an interface that defines how a score should be calculated.
//It does not contain any logic, it only gives a rule that any scoring class must follow. 
//This makes the scoring system flexible.