package com.splb.model.entity;


import java.io.IOException;

public class Enrollment extends Entity  implements Comparable<Enrollment>{

    private Faculty faculty;
    private Applicant applicant;
    private EnrollStatus status;

    public Enrollment() {
    }

    public Enrollment(Faculty faculty, Applicant applicant, EnrollStatus status) {
        this.faculty = faculty;
        this.applicant = applicant;
        this.status = status;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public EnrollStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollStatus status) {
        this.status = status;
    }

    @Override
    public int compareTo(Enrollment e) {
        return e.applicant.getEnrollStatus() != applicant.getEnrollStatus() ?
                Integer.compare(e.applicant.getEnrollStatus(), applicant.getEnrollStatus()) :
                Integer.compare(e.faculty.getName().charAt(0), faculty.getName().charAt(0));

    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "faculty=" + faculty +
                ", applicant=" + applicant +
                ", status=" + status +
                '}';
    }
}
