package io.github.devdoctor.deltabooks;

import java.util.Collection;
import java.util.UUID;

public class Review {
    // The max size a note can be
    public static final int MAX_NOTE_SIZE = 255;
    // max number of book that a user can recommend
    public static final int MAX_BOOKS_RECOM = 3;

    // the review score (not the cleanest, should rework)
    private ReviewType style;
    private ReviewType content;
    private ReviewType niceness;
    private ReviewType originality;
    private ReviewType edition;

    // recommended books_uuids by the user
    private Collection<String> books_uuid;

    // creator of the review UUID number
    private String creator_uuid;

    public Review(String UUID) {
        this.creator_uuid = UUID;
    }

    public Review(ReviewType style, ReviewType content, ReviewType niceness, ReviewType originality, ReviewType edition, Collection<String> books, String creator_uuid) {
        this.style = style;
        this.content = content;
        this.niceness = niceness;
        this.originality = originality;
        this.edition = edition;
        this.creator_uuid = creator_uuid;
        this.books_uuid = books;
    }

    public float getFinalVote() {
        return ((float) (style.getValue() + content.getValue() + niceness.getValue() + originality.getValue() + edition.getValue()) / 5);
    }

    public ReviewType getStyle() {
        return style;
    }

    public void setStyle(ReviewType style) {
        this.style = style;
    }

    public ReviewType getContent() {
        return content;
    }

    public void setContent(ReviewType content) {
        this.content = content;
    }

    public ReviewType getNiceness() {
        return niceness;
    }

    public void setNiceness(ReviewType niceness) {
        this.niceness = niceness;
    }

    public ReviewType getOriginality() {
        return originality;
    }

    public void setOriginality(ReviewType originality) {
        this.originality = originality;
    }

    public ReviewType getEdition() {
        return edition;
    }

    public void setEdition(ReviewType edition) {
        this.edition = edition;
    }

    public Collection<String> getBooks_uuid() {
        return books_uuid;
    }

    public void setBooks_uuid(Collection<String> books_uuids) {
        this.books_uuid = books_uuids;
    }

    public String getCreator_uuid() {
        return creator_uuid;
    }

    public UUID getCreatorUuidAsObj() {
        return UUID.fromString(creator_uuid);
    }

    public void setCreator_uuid(String creator_uuid) {
        this.creator_uuid = creator_uuid;
    }

}
