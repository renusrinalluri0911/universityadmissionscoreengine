package admissions;

//GeneralPolicy implements ScoringPolicy and actually calculates the score using marks and weights.
//If the college wants to change the scoring formula later, only this class needs to be changed.

public class GeneralPolicy implements ScoringPolicy {

    private double examWeight;
    private double interviewWeight;

    public GeneralPolicy(double examWeight, double interviewWeight)
            throws InvalidWeightException {
        if (Math.abs(examWeight + interviewWeight - 1.0) > 0.0001) {
            throw new InvalidWeightException("Weights must sum to 1");
        }
        this.examWeight = examWeight;
        this.interviewWeight = interviewWeight;
    }

    @Override
    public double calculateScore(Applicant a) {
        return (a.examScore * examWeight) + (a.interviewScore * interviewWeight);
    }
}

