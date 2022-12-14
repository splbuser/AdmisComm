package com.splb.model.entity;

import java.io.IOException;
import java.util.List;

public class Applicant extends Entity implements Comparable<Applicant> {

    private int id;
    private String userName;
    private String password;
    private boolean adminStatus;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String region;
    private String educationalInstitution;
    private boolean blockStatus;
    private boolean statementStatus;
    private int enrollStatus;
    private ApplicantResult applicantResult;
    private int score;
    private String uploaded;
    private List<Faculty> list;

    public Applicant() {
    }

    public Applicant(int id, String userName, String password, String firstName, String lastName,
                     String email, String city, String region, String educationalInstitution) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.region = region;
        this.educationalInstitution = educationalInstitution;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(boolean adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEducationalInstitution() {
        return educationalInstitution;
    }

    public void setEducationalInstitution(String educationalInstitution) {
        this.educationalInstitution = educationalInstitution;
    }

    public boolean isBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(boolean blockStatus) {
        this.blockStatus = blockStatus;
    }


    public boolean isStatementStatus() {
        return statementStatus;
    }

    public void setStatementStatus(boolean statementStatus) {
        this.statementStatus = statementStatus;
    }

    public int getEnrollStatus() {
        return enrollStatus;
    }

    public void setEnrollStatus(int enrollStatus) {
        this.enrollStatus = enrollStatus;
    }

    public ApplicantResult getApplicantResult() {
        return applicantResult;
    }

    public void setApplicantResult(ApplicantResult applicantResult) {
        this.applicantResult = applicantResult;
    }

    public List<Faculty> getList() {
        return list;
    }

    public void setList(List<Faculty> list) {
        this.list = list;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUploaded() {
        return uploaded;
    }

    public void setUploaded(String uploaded) {
        this.uploaded = uploaded;
    }

    @Override
    public int compareTo(Applicant a) {
        return a.score != score ?
                Integer.compare(a.score, score) : Integer.compare(id, a.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Applicant applicant = (Applicant) o;
        return id == applicant.id;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        return result;
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public String toString() {
        return "Applicant{" +
                "id=" + id +
                ", user_name='" + userName + '\'' +
                ", password='*****" + '\'' +
                ", admin_status=" + adminStatus +
                ", first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", educationalInstitution='" + educationalInstitution + '\'' +
                ", block_status=" + blockStatus + "\n" +
                ", statement_status=" + statementStatus + "\n" +
                ", enroll_status=" + enrollStatus + "\n" +
                ", result=" + score + "\n";
    }
}
