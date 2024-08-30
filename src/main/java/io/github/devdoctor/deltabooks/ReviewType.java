package io.github.devdoctor.deltabooks;

public class ReviewType {
    private Integer value;
    private String note;

    public ReviewType(Integer value, String note) {
        this.value = value;
        this.note = note;
    }

    public Integer getValue() {
        return value;
    }

    public String getNote() {
        return note;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
