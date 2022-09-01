package com.splb.model.entity;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Faculty extends Entity implements Comparable<Faculty> {

    private int id;
    private String name;
    private int budgetPlaces;
    private int totalPlaces;
    private String subjOne;
    private String subjTwo;
    private List<Applicant> applicantList;

    public Faculty() {
    }

    public Faculty(String name, int budgetPlaces, int totalPlaces) {
        this.name = name;
        this.budgetPlaces = budgetPlaces;
        this.totalPlaces = totalPlaces;
    }

    public Faculty(String name, int budgetPlaces, int totalPlaces, String subjOne, String subjTwo) {
        this.name = name;
        this.budgetPlaces = budgetPlaces;
        this.totalPlaces = totalPlaces;
        this.subjOne = subjOne;
        this.subjTwo = subjTwo;
    }

    public Faculty(int id, String name, int budgetPlaces, int totalPlaces, String subjOne, String subjTwo) {
        this.id = id;
        this.name = name;
        this.budgetPlaces = budgetPlaces;
        this.totalPlaces = totalPlaces;
        this.subjOne = subjOne;
        this.subjTwo = subjTwo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBudgetPlaces() {
        return budgetPlaces;
    }

    public void setBudgetPlaces(int budgetPlaces) {
        this.budgetPlaces = budgetPlaces;
    }

    public int getTotalPlaces() {
        return totalPlaces;
    }

    public void setTotalPlaces(int totalPlaces) {
        this.totalPlaces = totalPlaces;
    }

    public String getSubjOne() {
        return subjOne;
    }

    public void setSubjOne(String subjOne) {
        this.subjOne = subjOne;
    }

    public String getSubjTwo() {
        return subjTwo;
    }

    public void setSubjTwo(String subjTwo) {
        this.subjTwo = subjTwo;
    }

    public List<Applicant> getApplicantList() {
        return applicantList;
    }

    public void setApplicantList(List<Applicant> applicantList) {
        this.applicantList = applicantList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Faculty faculty = (Faculty) o;
        if (id != faculty.id) {
            return false;
        }
        if (totalPlaces != faculty.totalPlaces) {
            return false;
        }
        if (budgetPlaces != faculty.budgetPlaces) {
            return false;
        }
        return Objects.equals(name, faculty.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + totalPlaces + budgetPlaces;
        return result;
    }

    @Override
    public int compareTo(Faculty o) {
        return o.totalPlaces != totalPlaces ?
                Integer.compare(o.totalPlaces, totalPlaces) : Integer.compare(budgetPlaces, o.budgetPlaces);
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", budgetPlaces=" + budgetPlaces +
                ", totalPlaces=" + totalPlaces +
                ", subjOne='" + subjOne + '\'' +
                ", subjTwo='" + subjTwo + '\'' +
                '}';
    }
}
