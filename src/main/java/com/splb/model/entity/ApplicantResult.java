package com.splb.model.entity;

public class ApplicantResult {

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
        if (algebra >= 1 && algebra <= 12) {
            this.algebra = algebra;
        } else System.out.println("Wrong mark for algebra!");
    }

    public int getBiology() {
        return biology;
    }

    public void setBiology(int biology) {
        if (biology >= 1 && biology <= 12) {
            this.biology = biology;
        } else System.out.println("Wrong mark for biology!");
    }

    public int getChemistry() {
        return chemistry;
    }

    public void setChemistry(int chemistry) {
        if (chemistry >= 1 && chemistry <= 12) {
            this.chemistry = chemistry;
        } else System.out.println("Wrong mark for chemistry!");
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        if (english >= 1 && english <= 12) {
            this.english = english;
        } else System.out.println("Wrong mark for english!");
    }

    public int getLiterature() {
        return literature;
    }

    public void setLiterature(int literature) {
        if (literature >= 1 && literature <= 12) {
            this.literature = literature;
        } else System.out.println("Wrong mark for literature!");
    }

    public int getWorldHistory() {
        return worldHistory;
    }

    public void setWorldHistory(int worldHistory) {
        if (worldHistory >= 1 && worldHistory <= 12) {
            this.worldHistory = worldHistory;
        } else System.out.println("Wrong mark for worldHistory!");
    }

    public int sum() {
        return algebra + biology + chemistry + english + literature + worldHistory;
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
