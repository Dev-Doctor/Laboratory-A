package io.github.devdoctor.deltabooks;

import java.util.Collection;

public class Review {
    // The max size a note can be
    public static final int MAX_NOTE_SIZE = 255;
    // max number of book that a user can recommend
    public static final int MAX_BOOKS_RECOM = 3;

    // the review score (not the cleanest, should rework)
    private int style;
    private int content;
    private int niceness;
    private int originality;
    private int edition;

    // the custom note wrote by the user
    private char[] note;

    // recommended books by the user
    private Collection<Book> recommended_books;

    // creator fiscal number of the review
    private String creator_id;


    public Review() {
        note = new char[MAX_NOTE_SIZE];
    }

    public Review(int style, int content, int niceness, int originality, int edition, String note, Collection<Book> books, String creator_id) {
        this.style = style;
        this.content = content;
        this.niceness = niceness;
        this.originality = originality;
        this.edition = edition;
        this.note = note.toCharArray();
        this.creator_id = creator_id;
        this.recommended_books = books;
    }

    public int getFinalVote() {
        return Math.floorDiv((style + content + niceness + originality + edition), 5);
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public int getNiceness() {
        return niceness;
    }

    public void setNiceness(int niceness) {
        this.niceness = niceness;
    }

    public int getOriginality() {
        return originality;
    }

    public void setOriginality(int originality) {
        this.originality = originality;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public char[] getNote() {
        return note;
    }

    public void setNote(char[] note) {
        this.note = note;
    }

    /**
     * Sets note from string. Checks the {@code String} size if it is valid.
     * If the size is bigger than {@value MAX_NOTE_SIZE} returns {@code False} else saves
     * the {@code String} and returns {@code True}
     *
     * @param string the string to check and save
     * @return A boolean is returned if the string is saved.
     */
    public boolean setNoteFromString(String string) {
        if(string.length() > 255) {
            return false;
        }
        this.note = string.toCharArray();
        return true;
    }

    /**
     * Converts the note {@code char} array to {@code String}.
     *
     * @return the note converted to {@code String}
     */
    public String getNoteToString() {
        return new String(note);
    }
}
