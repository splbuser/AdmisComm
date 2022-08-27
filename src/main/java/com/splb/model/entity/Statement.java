package com.splb.model.entity;

import java.util.List;

public class Statement implements Comparable<Statement> {

    private Faculty faculty;
    private List<Applicant> applicants;
    private Applicant applicant;
    private int totalScore;

    public Statement() {
    }

    public Statement(Faculty faculty, List<Applicant> applicants) {
        this.faculty = faculty;
        this.applicants = applicants;
    }

    public Statement(Faculty faculty, Applicant applicant) {
        this.faculty = faculty;
        this.applicant = applicant;
    }

    public Statement(Faculty faculty, Applicant applicant, int totalScore) {
        this.faculty = faculty;
        this.applicant = applicant;
        this.totalScore = totalScore;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<Applicant> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<Applicant> applicants) {
        this.applicants = applicants;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return "Statement{" +
                "faculty=" + faculty +
                ", applicant=" + applicant +
                ", totalScore=" + totalScore +
                '}';
    }

    @Override
    public int compareTo(Statement o) {
        return 0;
    }
}
