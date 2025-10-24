package src.model;

import java.time.LocalDate;

public class Librarian {
    private int librarianId;
    private LocalDate hireDate;

    public Librarian(int librarianId, LocalDate hireDate) {
        this.librarianId = librarianId;
        this.hireDate = hireDate;
    }

    public Librarian() {
    }

    public int getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(int librarianId) {
        this.librarianId = librarianId;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

}
