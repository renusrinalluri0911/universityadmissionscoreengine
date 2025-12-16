package admissions;

//Applicant is the basic parent class for students.
//It just defines what details every student must have, like id, name, category and marks. 
//We don’t create objects of this class directly; it only acts as a base structure for other applicant classes.

public abstract class Applicant {
    protected int id;
    protected String name;
    protected String category;
    protected double examScore;
    protected double interviewScore;
    protected double totalScore;

    public Applicant(int id, String name, String category,
                     double examScore, double interviewScore) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.examScore = examScore;
        this.interviewScore = interviewScore;
    }

    public abstract void computeTotal(ScoringPolicy policy);

    public String getCategory() {
        return category;
    }

    public double getTotalScore() {
        return totalScore;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + category + "," + totalScore;
    }
}




