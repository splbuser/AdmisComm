package com.splb.model.entity;

import java.io.IOException;

public class ApplicantResult extends Entity  {

    private int userId;
    private int algebra;
    private int biology;
    private int chemistry;
    private int english;
    private int literature;
    private int worldHistory;

    public ApplicantResult() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ApplicantResult(int userId, int algebra, int biology, int chemistry, int english, int literature, int worldHistory) {
        this.userId = userId;
        this.algebra = algebra;
        this.biology = biology;
        this.chemistry = chemistry;
        this.english = english;
        this.literature = literature;
        this.worldHistory = worldHistory;
    }

    public int getAlgebra() {
        return algebra;
    }

    public void setAlgebra(int algebra) {
        this.algebra = algebra;
    }

    public int getBiology() {
        return biology;
    }

    public void setBiology(int biology) {
       this.biology = biology;
    }

    public int getChemistry() {
        return chemistry;
    }

    public void setChemistry(int chemistry) {
        this.chemistry = chemistry;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
       this.english = english;
    }

    public int getLiterature() {
        return literature;
    }

    public void setLiterature(int literature) {
        this.literature = literature;
    }

    public int getWorldHistory() {
        return worldHistory;
    }

    public void setWorldHistory(int worldHistory) {
        this.worldHistory=worldHistory;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public String toString() {
        return "ApplicantResult{" +
                "Al=" + algebra +
                ", Bio=" + biology +
                ", Ch=" + chemistry +
                ", Eng=" + english +
                ", Lit=" + literature +
                ", WH=" + worldHistory +
                "}\n";
    }
}
