package com.example.luizaabraamyan.studentmarkssystem;

public class Student {

    private int id;
    private String name;
    private String facNum;
    private int mark;
    private int isEndorsed;
    private String note;
    private int isPresent;

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

    public String getFacNum() {
        return facNum;
    }

    public void setFacNum(String facNum) {
        this.facNum = facNum;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getIsEndorsed() {
        return isEndorsed;
    }

    public void setIsEndorsed(int isEndorsed) {
        this.isEndorsed = isEndorsed;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(int isPresent) {
        this.isPresent = isPresent;
    }
}
