/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.bookrecommender;


/**
 * The type Review type.
 *
 * @author DevDoctor
 * @since 1.0
 */
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
