package src.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import src.interfaces.ILibrarian;
import src.model.dao.LibrarianDAO;
import src.model.pojo.Librarian;

public class LibrarianService implements ILibrarian {

    private final ILibrarian librarianDAO;

    public LibrarianService() {
        this.librarianDAO = new LibrarianDAO();
    }

    @Override
    public int createLibrarian(int userId, Librarian librarian) throws SQLException {
        Objects.requireNonNull(librarian, "Librarian cannot be null");
        if (userId <= 0)
            throw new IllegalArgumentException("Invalid user ID");
        if (librarian.getHireDate() == null)
            librarian.setHireDate(LocalDate.now());

        if (((LibrarianDAO) librarianDAO).librarianExists(userId)) {
            throw new IllegalArgumentException("Librarian already exists with ID " + userId);
        }

        return librarianDAO.createLibrarian(userId, librarian);
    }

    @Override
    public Librarian getLibrarianById(int librarianId) throws SQLException {
        if (librarianId <= 0)
            throw new IllegalArgumentException("Invalid librarian ID");
        return librarianDAO.getLibrarianById(librarianId);
    }

    @Override
    public List<Librarian> getAllLibrarians() throws SQLException {
        return librarianDAO.getAllLibrarians();
    }

    @Override
    public boolean updateLibrarian(Librarian librarian) throws SQLException {
        Objects.requireNonNull(librarian, "Librarian cannot be null");
        if (librarian.getLibrarianId() <= 0)
            throw new IllegalArgumentException("Invalid librarian ID");

        if (!((LibrarianDAO) librarianDAO).librarianExists(librarian.getLibrarianId())) {
            throw new IllegalArgumentException("Librarian not found with ID: " + librarian.getLibrarianId());
        }

        return librarianDAO.updateLibrarian(librarian);
    }

    @Override
    public boolean deleteLibrarian(int librarianId) throws SQLException {
        if (librarianId <= 0)
            throw new IllegalArgumentException("Invalid librarian ID");
        if (!((LibrarianDAO) librarianDAO).librarianExists(librarianId)) {
            throw new IllegalArgumentException("Librarian not found with ID: " + librarianId);
        }
        return librarianDAO.deleteLibrarian(librarianId);
    }

    public List<Librarian> getLibrariansHiredBetween(LocalDate start, LocalDate end) throws SQLException {
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);
        return ((LibrarianDAO) librarianDAO).getLibrariansHiredBetween(start, end);
    }

    @Override
    public boolean librarianExists(int librarianId) throws SQLException {
        if (librarianId <= 0) {
            throw new IllegalArgumentException("Invalid Librarian id: " + librarianId);
        }
        return getLibrarianById(librarianId) != null;
    }

}
