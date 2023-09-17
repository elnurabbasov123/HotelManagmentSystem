package enums;

import java.time.LocalDateTime;

public enum Exception {
    OPERATION_NOT_FOUND_EXCEPTION(402,"Choosed you operation is doesn't exist !",LocalDateTime.now()),
    ROOM_TYPE_IS_DONT_EXIST_EXCEPTION(403,"Your choice is not right !",LocalDateTime.now());

    private final int status;
    private final String message;
    private final LocalDateTime localDateTime;

    Exception(int status, String message, LocalDateTime localDateTime) {
        this.status = status;
        this.message = message;
        this.localDateTime = localDateTime;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
