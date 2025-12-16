//The AdmissionEngine class is the main controller of the application.
//It reads applicant data, creates applicant objects, applies the scoring policy and quota policy, 
//and finally generates the merit list and waitlist. 
//This class coordinates all other classes and controls the complete admission workflow. 
//It demonstrates object creation, polymorphism by using interface references, and encapsulation
//by managing the entire process in a single place.

package admissions;

import java.io.*;
import java.util.*;


public class AdmissionEngine {

    public static void main(String[] args) throws Exception {

        String csvPath = "C:\\JAVA PROJECT\\UniversityAdmissionEngine\\data\\applicants.csv";
        String cfgPath = "C:\\JAVA PROJECT\\UniversityAdmissionEngine\\data\\weights.cfg";

        double examWeight = 0.7;
        double interviewWeight = 0.3;

        // ---------- READ weights.cfg ----------
        try (BufferedReader br = new BufferedReader(new FileReader(cfgPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("=");
                if (p[0].equalsIgnoreCase("examWeight"))
                    examWeight = Double.parseDouble(p[1]);
                if (p[0].equalsIgnoreCase("interviewWeight"))
                    interviewWeight = Double.parseDouble(p[1]);
            }
        }

        GeneralPolicy policy = new GeneralPolicy(examWeight, interviewWeight);

        // ---------- READ applicants ----------
        List<Applicant> applicants = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                applicants.add(new GeneralApplicant(
                        Integer.parseInt(p[0]),
                        p[1],
                        p[2],
                        Double.parseDouble(p[3]),
                        Double.parseDouble(p[4])
                ));
            }
        }

        // ---------- NORMALIZATION ----------
        double minExam = applicants.stream().mapToDouble(a -> a.examScore).min().getAsDouble();
        double maxExam = applicants.stream().mapToDouble(a -> a.examScore).max().getAsDouble();

        double minInterview = applicants.stream().mapToDouble(a -> a.interviewScore).min().getAsDouble();
        double maxInterview = applicants.stream().mapToDouble(a -> a.interviewScore).max().getAsDouble();

        for (Applicant a : applicants) {
            a.examScore = (a.examScore - minExam) / (maxExam - minExam);
            a.interviewScore = (a.interviewScore - minInterview) / (maxInterview - minInterview);
            a.computeTotal(policy);
        }

        // ---------- SORT BY MERIT ----------
        applicants.sort(Comparator.comparingDouble(Applicant::getTotalScore).reversed());

        // ---------- APPLY QUOTA USING STREAMS ----------
        Map<String, Integer> quota = Map.of(
                "GEN", 2,
                "OBC", 2,
                "SC", 2
        );

        Map<String, Long> countMap = new HashMap<>();
        List<Applicant> merit = new ArrayList<>();
        List<Applicant> waitlist = new ArrayList<>();

        for (Applicant a : applicants) {
            countMap.putIfAbsent(a.getCategory(), 0L);
            if (countMap.get(a.getCategory()) < quota.getOrDefault(a.getCategory(), Integer.MAX_VALUE)) {
                merit.add(a);
                countMap.put(a.getCategory(), countMap.get(a.getCategory()) + 1);
            } else {
                waitlist.add(a);
            }
        }

        // ---------- WRITE MERIT LIST ----------
        writeCsv("C:\\JAVA PROJECT\\UniversityAdmissionEngine\\data\\Merit list.csv", merit);
        writeCsv("C:\\JAVA PROJECT\\UniversityAdmissionEngine\\data\\Wait list.csv", waitlist);

        System.out.println("Merit list & waitlist generated successfully");
    }

    private static void writeCsv(String path, List<Applicant> list) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write("ID,Name,Category,TotalScore\n");
            for (Applicant a : list) {
                bw.write(a.toString());
                bw.newLine();
            }
        }
    }
}