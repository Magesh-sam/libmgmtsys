package src.controller;

import java.sql.SQLException;
import java.util.List;

import src.interfaces.ILibrarian;
import src.model.pojo.Librarian;
import src.service.LibrarianService;

public class LibrarianController implements ILibrarian {
    private final LibrarianService librarianService;

    public LibrarianController() {
        this.librarianService = new LibrarianService();
    }

    @Override
    public int createLibrarian(int librarianId, Librarian librarian) {
        try {
         
            if (librarian == null) {
                throw new IllegalArgumentException("Librarian data cannot be null.");
            }
            return librarianService.createLibrarian(librarianId, librarian);
        } 
        catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
            return -1;
        } 
        catch (SQLException e) {
            System.out.println("Error creating librarian: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Librarian getLibrarianById(int librarianId) {
        try {
            if (librarianId <= 0) {
                throw new IllegalArgumentException("Invalid librarian ID.");
            }
            return librarianService.getLibrarianById(librarianId);
        } 
        catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
            return null;
        } 
        catch (SQLException e) {
            System.out.println("Error fetching librarian by ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Librarian> getAllLibrarians() {
        try {
            return librarianService.getAllLibrarians();
        } 
        catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
            return null;
        } 
        catch (SQLException e) {
            System.out.println("Error fetching all librarians: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateLibrarian(Librarian librarian) {
        try {
            if (librarian == null) {
                throw new IllegalArgumentException("Librarian object cannot be null.");
            }
            return librarianService.updateLibrarian(librarian);
        } 
        catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
            return false;
        } 
        catch (SQLException e) {
            System.out.println("Error updating librarian: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteLibrarian(int librarianId) {
        try {
            if (librarianId <= 0) {
                throw new IllegalArgumentException("Invalid librarian ID.");
            }
            return librarianService.deleteLibrarian(librarianId);
        } 
        catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
            return false;
        } 
        catch (SQLException e) {
            System.out.println("Error deleting librarian: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
