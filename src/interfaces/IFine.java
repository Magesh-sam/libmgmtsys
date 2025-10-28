package src.interfaces;

import java.sql.SQLException;
import java.util.List;

import src.model.pojo.Fine;
import src.model.pojo.Fine.FineStatus;

public interface IFine {

    int createFine(Fine fine) throws SQLException;

    Fine getFineById(int fineId) throws SQLException;

    List<Fine> getAllFines() throws SQLException;

    boolean updateFineStatus(int fineId, FineStatus status) throws SQLException;

    boolean deleteFine(int fineId) throws SQLException;

    List<Fine> getFinesByMemberId(int memberId) throws SQLException;

    List<Fine> getUnpaidFines() throws SQLException;

}