package com.splb.model.entity;

import java.util.List;

public class Faculty implements Comparable<Faculty> {

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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Faculty other = (Faculty) obj;
        return name.equals(other.name)
                && budgetPlaces == (other.budgetPlaces)
                && totalPlaces == (other.totalPlaces)
                && subjOne.equals(other.subjOne)
                && subjTwo.equals(other.subjTwo);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
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

    @Override
    public int compareTo(Faculty o) {
        return o.totalPlaces != totalPlaces ?
                Integer.compare(o.totalPlaces, totalPlaces) : Integer.compare(budgetPlaces, o.budgetPlaces);
    }
}
