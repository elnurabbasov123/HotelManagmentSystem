package service.inter;
import model.Room;
import response.GeneralResponse;

import java.util.List;

public interface RoomService {
    GeneralResponse<List<Room>> addRoom();

    GeneralResponse<List<Room>> showRooms();

    GeneralResponse<Room> updateRoom();

    GeneralResponse<Room> deleteRoom();

    GeneralResponse<Room> searchRoom();

     List<Room> getRooms();
}
