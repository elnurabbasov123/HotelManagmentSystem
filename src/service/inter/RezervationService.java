package service.inter;

import model.Customer;
import model.Rezervation;
import response.GeneralResponse;

import java.sql.SQLException;
import java.util.List;

public interface RezervationService {
    GeneralResponse<Rezervation> addRezerv();

    GeneralResponse<?> showRezervations();

    GeneralResponse<Rezervation> updateRezervation();

    void cancelRezervationById(int id);

    GeneralResponse<?> cancelRezervationById();

    GeneralResponse<?> cancelAllRezervations();

    GeneralResponse<List<Rezervation>> searchRezervation();

    List<Rezervation> getAllRezervations();
}
