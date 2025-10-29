package src.interfaces;

import java.sql.SQLException;
import java.util.List;

import src.model.pojo.Librarian;

public interface ILibrarian {

    int createLibrarian(int librarian_id, Librarian librarian) throws SQLException;

    Librarian getLibrarianById(int librarianId) throws SQLException;

    List<Librarian> getAllLibrarians() throws SQLException;

    boolean updateLibrarian(Librarian librarian) throws SQLException;

    boolean deleteLibrarian(int librarianId) throws SQLException;

    boolean librarianExists(int librarianId) throws SQLException;

}