package admissions;

//QuotaPolicy handles reservation rules.
//It uses another scoring policy to calculate marks and then checks whether seats are available for the student’s category. 
//If seats are full, the student is moved to the waitlist.
//This keeps scoring and quota logic separate and clean.

import java.util.HashMap;
import java.util.Map;

public class QuotaPolicy implements ScoringPolicy {

    private ScoringPolicy basePolicy;
    private Map<String, Integer> quotaMap = new HashMap<>();
    private Map<String, Integer> usedQuota = new HashMap<>();

    public QuotaPolicy(ScoringPolicy basePolicy) {
        this.basePolicy = basePolicy;
    }

    public void setQuota(String category, int limit) {
        quotaMap.put(category, limit);
        usedQuota.put(category, 0);
    }

    @Override
    public double calculateScore(Applicant a) {
        String cat = a.getCategory();
        if (!quotaMap.containsKey(cat)) {
            // If category quota not set, allow
            return basePolicy.calculateScore(a);
        }

        if (usedQuota.get(cat) >= quotaMap.get(cat)) {
            throw new QuotaExceededException("Quota exceeded for " + cat);
        }
        usedQuota.put(cat, usedQuota.get(cat) + 1);
        return basePolicy.calculateScore(a);
    }
}

