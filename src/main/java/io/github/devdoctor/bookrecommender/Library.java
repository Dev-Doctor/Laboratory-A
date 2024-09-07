/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.bookrecommender;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Library.
 *
 * @author DevDoctor
 * @since 1.0
 */
public class Library {
    String name;
    List<String> books_uuids;

    public Library(String name) {
        this.name = name;
        this.books_uuids = new ArrayList<String>();
    }

    public Library(String name, List<String> books_uuids) {
        this.name = name;
        this.books_uuids = books_uuids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getBooks_uuids() {
        return books_uuids;
    }

    public void setBooks_uuids(List<String> books_uuids) {
        this.books_uuids = books_uuids;
    }

    public boolean addBook(String uuid) {
        if(doesContainBook(uuid)) {
            return false;
        }
        books_uuids.add(uuid);
        return true;
    }

    public boolean doesContainBook(String uuid) {
        return books_uuids.contains(uuid);
    }
}
