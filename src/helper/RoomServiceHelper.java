package helper;

import connection.ConnectionConfig;
import enums.Exception;
import exception.GeneralException;
import model.Rezervation;
import model.Room;
import service.impl.RezervationServiceImpl;
import service.impl.RoomServiceImpl;
import service.inter.RezervationService;
import service.inter.RoomService;
import util.InputUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomServiceHelper {

     static RoomService roomService=new RoomServiceImpl();
     static RezervationService rezervationService=new RezervationServiceImpl();

    public static List<Room> choosedRooms(String roomType){
        List<Room> allRooms=roomService.getRooms();
    List<Room> choosedRooms=new ArrayList<>();

        for (int i = 0; i < allRooms.size(); i++) {
            if(allRooms.get(i).getRoomType().equals(roomType)&&allRooms.get(i).isFull()==false){
                choosedRooms.add(allRooms.get(i));
            }
        }
        return choosedRooms;
    }
    public static Room selectRoom(){
        int rezervNum=RezervationServiceHelper.chooseRoomType();//1std , 2delux, 3triple . 4 vip
        List<Room> rooms=new ArrayList<>();
        if (rezervNum == 1) {
            rooms=RoomServiceHelper.choosedRooms("standart");
        } else if (rezervNum == 2) {
            rooms=RoomServiceHelper.choosedRooms("delux");
        } else if (rezervNum == 3) {
            rooms=RoomServiceHelper.choosedRooms("triple");
        } else if (rezervNum == 4) {
            rooms=RoomServiceHelper.choosedRooms("vip");
        }else{
            throw new GeneralException(Exception.ROOM_TYPE_IS_DONT_EXIST_EXCEPTION);
        }
        for (int i = 0; i < rooms.size(); i++) {
            if(!rooms.get(i).isFull())
                System.out.println(rooms.get(i));
        }
        Room searchedRoom=new Room();
        int roomNumber= InputUtil.getInstance().inputInt("Which room do you want ? Enter the room number :");
        for (int i=0;i<rooms.size();i++) {
            if(rooms.get(i).getRoomNumber()==roomNumber){
                searchedRoom= rooms.get(i);
                rooms.get(i).setFull(true);
            }
        }
        return searchedRoom;
    }
    public static String roomHours(){
        int hours=InputUtil.getInstance().inputInt("do you want to take a room for 12 hours" +
                " or for a few days ? :\n" +
                "[1]->12 hours\n" +
                "[2]->a few days\n" +
                "Select:");
        if (hours==1){
            return "12h";
        }else if(hours==2){
            int days=InputUtil.getInstance().inputInt("How many days do you want take the room ? :");
            return String.valueOf(days);
        }else
            throw new GeneralException(Exception.OPERATION_NOT_FOUND_EXCEPTION);
    }
    public static void checkRoom(){
        List<Integer> allRoomNums=new ArrayList<>();
        for (int i = 0; i < roomService.getRooms().size(); i++) {
            allRoomNums.add(roomService.getRooms().get(i).getRoomNumber());
        }
        List<Integer> fullRoomNums=new ArrayList<>();

        for (int i = 0; i <rezervationService.getAllRezervations().size() ; i++) {
            fullRoomNums.add(rezervationService.getAllRezervations().get(i).getRoomNumber());
        }
        for (int i = 0; i < fullRoomNums.size(); i++) {
            allRoomNums.remove(fullRoomNums.get(i));
        }
        try {
            Connection connection=ConnectionConfig.connection();
            String sql="update hotel_data.room set is_full=false where room_number=?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            for (int i = 0; i < allRoomNums.size(); i++) {
                preparedStatement.setInt(1,allRoomNums.get(i));
                preparedStatement.execute();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
