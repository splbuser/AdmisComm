package com.splb.model.entity;


import java.io.IOException;
import java.util.Objects;

public class StatementResult extends Entity {

    private Applicant applicant;
    private Faculty faculty;
    private int firstSubject;
    private int secondSubject;
    private int total;


    public StatementResult() {
    }

    public StatementResult(Applicant applicant, Faculty faculty, int firstSubject, int secondSubject) {
        this.applicant = applicant;
        this.faculty = faculty;
        this.firstSubject = firstSubject;
        this.secondSubject = secondSubject;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public int getFirstSubject() {
        return firstSubject;
    }

    public void setFirstSubject(int firstSubject) {
        this.firstSubject = firstSubject;
    }

    public int getSecondSubject() {
        return secondSubject;
    }

    public void setSecondSubject(int secondSubject) {
        this.secondSubject = secondSubject;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatementResult that = (StatementResult) o;
        return firstSubject == that.firstSubject && secondSubject == that.secondSubject && total == that.total
                && applicant.equals(that.applicant) && faculty.equals(that.faculty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicant, faculty, firstSubject, secondSubject, total);
    }

    @Override
    public void close() throws IOException {
    }
}
