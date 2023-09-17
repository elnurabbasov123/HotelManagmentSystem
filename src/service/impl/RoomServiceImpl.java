package service.impl;

import com.mysql.cj.jdbc.ConnectionImpl;
import connection.ConnectionConfig;
import connection.Rs;
import enums.Exception;
import exception.GeneralException;
import globalData.GlobalData;
import model.Room;
import response.GeneralResponse;
import service.inter.RoomService;
import util.InputUtil;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomServiceImpl implements RoomService {
   static final RoomService roomService=new RoomServiceImpl();
    @Override
    public GeneralResponse<List<Room>> addRoom() {
        return null;
    }

    @Override
    public List<Room> getRooms() {
        List<Room> rooms=new ArrayList<>();
        try {
            String sql="select * from room";
            ResultSet resultSet= Rs.dbResponse(sql);

            while (resultSet.next()){
                int roomNumber=resultSet.getInt(1);
                double room24price=resultSet.getDouble(2);
                double room12price=resultSet.getDouble(3);
                String roomType=resultSet.getString(4);
                boolean isFull=resultSet.getBoolean(5);

                Room room=new Room(roomNumber,room12price,room24price,roomType,isFull);

                GlobalData.rooms.add(room);
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    @Override
    public GeneralResponse<List<Room>> showRooms() {
        List<Room> rooms=roomService.getRooms();
        return new GeneralResponse<List<Room>>().of("Rooms showed succesfully",rooms);
    }

    @Override
    public GeneralResponse<Room> updateRoom() {
        for (int i = 0; i < getRooms().size(); i++) {
            System.out.println(getRooms().get(i));
        }
        int roomNum= InputUtil.getInstance().inputInt("Which room do you want to update ? Please Enter Room Number :");
        Room room=new Room();
        for (int i = 0; i < getRooms().size(); i++) {
            if(getRooms().get(i).getRoomNumber()==roomNum){
                room=getRooms().get(i);
            }
        }
        int option=InputUtil.getInstance().inputInt("What do you want to Update ? :\n" +
                "[1]->Room Number\n" +
                "[2]->Room 24 Hours Price\n" +
                "[3]->Room 12 Hours Price\n" +
                "[4]->Room Type\n" +
                "[5]->IS Full\n" +
                "Select:");
        int roomNumber=room.getRoomNumber();
        try {
            Connection connection= ConnectionConfig.connection();
            switch (option){
                case 1:
                    int number=InputUtil.getInstance().inputInt("Please enter new Room Number :");
                    String sql="update hotel_data.room set room_number=? where room_number=? ";
                    PreparedStatement preparedStatement=connection.prepareStatement(sql);
                    preparedStatement.setInt(2,roomNumber);
                    preparedStatement.setInt(1,number);
                    preparedStatement.execute();
                    room.setRoomNumber(number);
                    break;
                case 2:
                    double price24=InputUtil.getInstance().inputDouble("Please enter new Price :");
                    String sql2="update hotel_data.room set room_price_24hours=? where room_number=? ";
                    PreparedStatement preparedStatement2=connection.prepareStatement(sql2);
                    preparedStatement2.setInt(2,roomNumber);
                    preparedStatement2.setDouble(1,price24);
                    preparedStatement2.execute();
                    room.setRoomPrice24Hours(price24);
                    break;
                case 3:
                    double price12=InputUtil.getInstance().inputDouble("Please enter new Price :");
                    String sql3="update hotel_data.room set room_price_12hours=? where room_number=? ";
                    PreparedStatement preparedStatement3=connection.prepareStatement(sql3);
                    preparedStatement3.setInt(2,roomNumber);
                    preparedStatement3.setDouble(1,price12);
                    preparedStatement3.execute();
                    room.setRoomPrice12Hours(price12);
                    break;
                case 4:
                    String type=InputUtil.getInstance().inputString("Please enter new Room Type, Example(standart,delux,triple,vip) :");
                    String sql4="update hotel_data.room set room_type=? where room_number=? ";
                    PreparedStatement preparedStatement4=connection.prepareStatement(sql4);
                    preparedStatement4.setInt(2,roomNumber);
                    preparedStatement4.setString(1,type);
                    preparedStatement4.execute();
                    room.setRoomType(type);
                    break;
                case 5:
                    int otvet=InputUtil.getInstance().inputInt("Please enter Full or Empty ? :\n" +
                            "[1]->Full\n" +
                            "[2]->Empty\n" +
                            "Select:");
                    boolean isFull=false;
                    String sql5="update hotel_data.room set is_full=? where room_number=? ";
                    PreparedStatement preparedStatement5=connection.prepareStatement(sql5);
                    preparedStatement5.setInt(2,roomNumber);
                    switch (otvet){
                        case 1:isFull=true;break;
                        case 2:isFull=false;break;
                    }
                    preparedStatement5.setBoolean(1,isFull);
                    preparedStatement5.execute();
                    room.setFull(isFull);
                    break;
                default:throw new GeneralException(Exception.OPERATION_NOT_FOUND_EXCEPTION);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new GeneralResponse<Room>().of("Room Updated Succesfully",room);
    }

    @Override
    public GeneralResponse<Room> deleteRoom() {
        for (int i = 0; i < getRooms().size(); i++) {
            System.out.println(getRooms().get(i));
        }
        int roomNum= InputUtil.getInstance().inputInt("Which room do you want to Delete ? Please Enter Room Number :");
        Room room=new Room();
        for (int i = 0; i < getRooms().size(); i++) {
            if(getRooms().get(i).getRoomNumber()==roomNum){
                room=getRooms().get(i);
            }
        }

        try {
            Connection connection=ConnectionConfig.connection();
            String sql="delete hotel_data.room where room_number=?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,room.getRoomNumber());
            preparedStatement.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new GeneralResponse<Room>().of("Room Deleted Succesfully",room);
    }

    @Override
    public GeneralResponse<Room> searchRoom() {
        int num=InputUtil.getInstance().inputInt("Please enter Room number which do you want to see :");
        Room room=new Room();
        for (int i = 0; i < getRooms().size(); i++) {
            if(getRooms().get(i).getRoomNumber()==num){
                room=getRooms().get(i);
            }
        }

        return new GeneralResponse<Room>().of("Search Operation Comleted Succesfully",room);
    }
}
