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
    private String note;

    // recommended books_uuids by the user
    private Collection<Book> recommended_books;

    // creator of the review UUID number
    private String creator_uuid;

    public Review(String UUID) {
        this.creator_uuid = UUID;
    }

    public Review(int style, int content, int niceness, int originality, int edition, String note, Collection<Book> books, String creator_uuid) {
        this.style = style;
        this.content = content;
        this.niceness = niceness;
        this.originality = originality;
        this.edition = edition;
        this.note = note;
        this.creator_uuid = creator_uuid;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Converts the note {@code char} array to {@code String}.
     *
     * @return the note converted to {@code String}
     */
    public String getNoteToString() {
        return new String(note);
    }

    public String getCreator_uuid() {
        return creator_uuid;
    }

    public void setCreator_uuid(String creator_uuid) {
        this.creator_uuid = creator_uuid;
    }

}
