package io.github.devdoctor.deltabooks.utility;

import io.github.devdoctor.deltabooks.Library;
import io.github.devdoctor.deltabooks.LoadedData;
import io.github.devdoctor.deltabooks.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LibraryUtils {

    public static Boolean addNewLibrary(Library candidate) {
        return addNewLibrary(LoadedData.logged_user_libraries, candidate);
    }

    public static Boolean addNewLibrary(List<Library> libraries, Library candidate) {
        for (Library library : libraries) {
            if(library.getName().equals(candidate.getName())) {
                return false;
            }
        }
        libraries.add(candidate);
        saveLibraries();
        return true;
    }

    public static Boolean updateLibrary(Library candidate) {
        return updateLibrary(LoadedData.logged_user_libraries, candidate);
    }

    public static Boolean updateLibrary(List<Library> libraries, Library candidate) {
        for (Library library : libraries) {
            if(library.getName().equals(candidate.getName())) {
                library = candidate;
                LoadedData.logged_user_libraries = libraries;
                return true;
            }
        }
        return false;
    }

    public static List<Library> loadLibraries() {
        return loadLibraries(LoadedData.logged_user);
    }

    public static List<Library> loadLibraries(User user) {
        return FileUtils.loadLibrariesFromFile(user);
    }

    public static void deleteLibrary(Library selectedItem) {
        LoadedData.logged_user_libraries.remove(selectedItem);
        FileUtils.writeLibraryToFile(LoadedData.logged_user_libraries, LoadedData.logged_user);
    }

    public static void deleteLibraries(List<Library> selectedItems) {
        for(Library current : selectedItems) {
            LoadedData.logged_user_libraries.remove(current);
        }
        FileUtils.writeLibraryToFile(LoadedData.logged_user_libraries, LoadedData.logged_user);
    }

    public static void saveLibraries() {
        FileUtils.writeLibraryToFile(LoadedData.logged_user_libraries, LoadedData.logged_user);
    }
}
