package src.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import src.interfaces.IFine;
import src.model.dao.FineDAO;
import src.model.pojo.Fine;
import src.model.pojo.Fine.FineStatus;
import src.utils.Validation;

public class FineService implements IFine {

    FineDAO fineDAO;

    public FineService() {
        this.fineDAO = new FineDAO();
    }

    @Override

    public int createFine(Fine fine) throws SQLException {
        Objects.requireNonNull(fine, "Fine cannot be null");
        if (fine.getBorrowedId() <= 0) {
            throw new IllegalArgumentException("Borrowed ID cannot be empty or negative");
        }
        if (fine.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount cannot be empty or negative");
        }
        Objects.requireNonNull(fine.getIssueDate(), "Issue Data cannot be null");
        Fine f = fineDAO.getFineById(fine.getFineId());
        if (f != null) {
            throw new IllegalArgumentException("Fine already exist.");
        }
        int fineId = fineDAO.createFine(fine);
        return fineId;
    }

    @Override
    public Fine getFineById(int fineId) throws SQLException {
        if (fineId <= 0) {
            throw new IllegalArgumentException("Fine id cannot be null");
        }
        Fine f = fineDAO.getFineById(fineId);
        return f;

    }

    @Override
    public List<Fine> getAllFines() throws SQLException {
        return fineDAO.getAllFines();
    }

    @Override
    public boolean updateFineStatus(int fineId, FineStatus status) throws SQLException {
        if (fineId <= 0) {
            throw new IllegalArgumentException("Fine id cannot be null");
        }
        Validation.requireNonEmpty(status.toString(), "Fine Status");
        Fine fine = fineDAO.getFineById(fineId);
        if (fine == null) {
            throw new IllegalArgumentException("Fine with ID " + fineId + " does not exist");
        }
        return fineDAO.updateFineStatus(fineId, status);

    }

    @Override
    public boolean deleteFine(int fineId) throws SQLException {
        if (fineId <= 0) {
            throw new IllegalArgumentException("Fine id cannot be null");
        }
        Fine fine = fineDAO.getFineById(fineId);
        if (fine == null) {
            throw new IllegalArgumentException("Fine with ID " + fineId + " does not exist");
        }
        return fineDAO.deleteFine(fineId);
    }

}
