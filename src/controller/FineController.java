package src.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import src.model.pojo.Fine;
import src.model.pojo.Fine.FineStatus;
import src.service.FineService;

public class FineController {

    private final FineService fineService;

    public FineController() {
        this.fineService = new FineService();
    }

    public int createFine(Fine fine) {
        try {
            return fineService.createFine(fine);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error creating fine: " + e.getMessage());
            return 0;
        }
    }

    public Fine getFineById(int fineId) {
        try {
            return fineService.getFineById(fineId);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error fetching fine: " + e.getMessage());
            return null;
        }
    }

    public List<Fine> getAllFines() {
        try {
            return fineService.getAllFines();
        } catch (SQLException e) {
            System.err.println("Error fetching fines: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public boolean updateFineStatus(int fineId, FineStatus status) {
        try {
            return fineService.updateFineStatus(fineId, status);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error updating fine status: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteFine(int fineId) {
        try {
            return fineService.deleteFine(fineId);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error deleting fine: " + e.getMessage());
            return false;
        }
    }
}
