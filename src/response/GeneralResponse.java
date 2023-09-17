package response;
import model.Room;

import java.time.LocalDate;
import java.util.List;

public class GeneralResponse<T> {
    String message;
    LocalDate nowDate;
    T data;

    public GeneralResponse() {
    }

    public GeneralResponse(LocalDate nowDate, String message, T data) {
        this.nowDate = nowDate;
        this.message = message;
        this.data = data;
    }

    public GeneralResponse(String message, LocalDate nowDate, T data) {

    }

    public GeneralResponse<T> of(String message, T data) {
        LocalDate dateNew = LocalDate.now();

        System.out.println("message : " + message + "\noperation date : " + dateNew + "\n data :" + data);
        return build(message, dateNew, data);
    }

    public GeneralResponse<T> of(String message) {
        LocalDate nowDate = LocalDate.now();
        System.out.println("message : " + message + "\ndate : " + nowDate);
        return build(message, nowDate, data);
    }

    private GeneralResponse<T> build(String message, LocalDate nowDate, T data) {
        return new GeneralResponse<>(message, nowDate, data);
    }
}
