package admissions;

//GeneralApplicant represents a real student. 
//It extends Applicant, so it automatically gets all the student details from the parent class. 
//This avoids rewriting the same code and clearly shows that a general applicant is a type of applicant.

public class GeneralApplicant extends Applicant {

    public GeneralApplicant(int id, String name, String category,
                            double examScore, double interviewScore) {
        super(id, name, category, examScore, interviewScore);
    }

    @Override
    public void computeTotal(ScoringPolicy policy) {
        totalScore = policy.calculateScore(this);
    }
}