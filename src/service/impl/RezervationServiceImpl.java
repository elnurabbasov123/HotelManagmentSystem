package service.impl;
import connection.ConnectionConfig;
import enums.Exception;
import exception.GeneralException;
import helper.CustomerServiceHelper;
import helper.RezervationServiceHelper;
import helper.RoomServiceHelper;
import model.Customer;
import model.Rezervation;
import model.Room;
import response.GeneralResponse;
import service.inter.CustomerService;
import service.inter.RezervationService;
import service.inter.RoomService;
import util.InputUtil;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RezervationServiceImpl implements RezervationService {
    CustomerService customerService = new CustomerServiceImpl();
    RoomService roomService = new RoomServiceImpl();
    static final RezervationService rezervationService = new RezervationServiceImpl();

    @Override
    public List<Rezervation> getAllRezervations() {

        List<Rezervation> rezervations = new ArrayList<>();

        String sql = "select * from hotel_data.rezervation";
        try {
            Connection connection = ConnectionConfig.connection();
            Statement statement = connection.createStatement();
            statement.execute(sql);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {

                int rezervId = resultSet.getInt(1);
                int csId = resultSet.getInt(2);
                int csRoomNumber = resultSet.getInt(3);
                double csRoomPrice = resultSet.getDouble(4);

                String rezervdate = resultSet.getString(5);
                String[] arr = rezervdate.split("-");
                int day = Integer.parseInt(arr[0]);
                int month = Integer.parseInt(arr[1]);
                int year = Integer.parseInt(arr[2]);
                String hoursAndMinutes = arr[3];
                String[] arrDM = hoursAndMinutes.split(":");
                int hours = Integer.parseInt(arrDM[0]);
                int minutes = Integer.parseInt(arrDM[1]);
                LocalDateTime rezervDate = LocalDateTime.of(year, month, day, hours, minutes);

                String rezervCancelDate = resultSet.getString(6);
                String[] arr2 = rezervCancelDate.split("-");
                int day2 = Integer.parseInt(arr2[0]);
                int month2 = Integer.parseInt(arr2[1]);
                int year2 = Integer.parseInt(arr2[2]);

                String hoursAndMinutes2 = arr2[3];
                String[] arrDM2 = hoursAndMinutes2.split(":");
                int hours2 = Integer.parseInt(arrDM2[0]);
                int minutes2 = Integer.parseInt(arrDM2[1]);

                LocalDateTime cancelDate = LocalDateTime.of(year2, month2, day2, hours2, minutes2);

                Rezervation rezervation = new Rezervation(rezervId, csId, csRoomNumber, csRoomPrice, rezervDate, cancelDate);

                rezervations.add(rezervation);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezervations;
    }

    @Override
    public GeneralResponse<Rezervation> addRezerv() {
        Customer customer = CustomerServiceHelper.fillCustomer();
        customerService.registerCustomer(customer);

        Room selectRoom = RoomServiceHelper.selectRoom();
        int roomNumber = selectRoom.getRoomNumber();
        try {
            String sql = "update hotel_data.room set is_full=true where room_number=?";
            Connection connection1 = ConnectionConfig.connection();
            PreparedStatement preparedStatement = connection1.prepareStatement(sql);
            preparedStatement.setInt(1, roomNumber);
            preparedStatement.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String roomHoursOrDays = RoomServiceHelper.roomHours();
        double roomPrice = 0;
        if (roomHoursOrDays.equals("12h")) {
            roomPrice = selectRoom.getRoomPrice12Hours();
        } else {
            int roomDays = Integer.valueOf(roomHoursOrDays);
            roomPrice = roomDays * selectRoom.getRoomPrice24Hours();
        }
        int cstId = 0;
        for (int i = 0; i < customerService.allCustomers().size(); i++) {
            if (customerService.allCustomers().get(i).getPasswordFin().equals(customer.getPasswordFin())) {
                cstId = customerService.allCustomers().get(i).getId();
            }
        }
        LocalDateTime rezervDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH':'mm");
        String strDate = formatter.format(rezervDate);
        LocalDateTime rezervCancelDate;

        if (roomHoursOrDays.equals("12h")) {
            rezervCancelDate = rezervDate.plusSeconds(12 * 60 * 60);
        } else {
            int roomDays = Integer.valueOf(roomHoursOrDays);
            rezervCancelDate = rezervDate.plusSeconds(roomDays * 24 * 60 * 60);
        }

        String cancelDate = formatter.format(rezervCancelDate);
        Rezervation rezervation = new Rezervation(cstId, selectRoom.getRoomNumber(), roomPrice, rezervDate, rezervCancelDate);

        String sql = "insert into hotel_data.rezervation(customer_id,room_number,room_price,rezervation_date,rezerv_cancel_date)" +
                " values(?,?,?,?,?)";

        try {
            Connection connection = ConnectionConfig.connection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, cstId);
            preparedStatement.setInt(2, selectRoom.getRoomNumber());
            preparedStatement.setDouble(3, roomPrice);
            preparedStatement.setString(4, strDate);
            preparedStatement.setString(5, cancelDate);
            preparedStatement.execute();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Rezervation rzrz = new Rezervation();
        for (int i = 0; i < rezervationService.getAllRezervations().size(); i++) {
            if (rezervationService.getAllRezervations().get(i).getRoomNumber() == roomNumber) {
                rzrz.setId(rezervationService.getAllRezervations().get(i).getRezervId());
                rzrz.setCustomerId(rezervationService.getAllRezervations().get(i).getCustomerId());
                rzrz.setRoomPrice(rezervationService.getAllRezervations().get(i).getRoomPrice());
                rzrz.setRoomNumber(rezervationService.getAllRezervations().get(i).getRoomNumber());
                rzrz.setRezervDate(rezervationService.getAllRezervations().get(i).getRezervDate());
                rzrz.setRezervCancelDate(rezervationService.getAllRezervations().get(i).getRezervCancelDate());
            }
        }
        return new GeneralResponse<Rezervation>().of("Rezervation Comleted Cuccesfully", rzrz);
    }

    @Override
    public GeneralResponse<?> showRezervations() {
        List<Rezervation> rezervations = rezervationService.getAllRezervations();
        for (int i = 0; i < rezervations.size(); i++) {
            System.out.println(rezervations.get(i));
        }
        return new GeneralResponse<>().of("Rezevations Showed Succesfully");
    }

    @Override
    public GeneralResponse<?> cancelAllRezervations() {
        try {
            String sql1 = "truncate table hotel_data.rezervation";
            String sql2 = "update hotel_data.room set is_full=false";
            String sql3 = "truncate table hotel_data.customer";
            Connection connection = ConnectionConfig.connection();
            Statement statement = connection.createStatement();
            statement.execute(sql1);
            statement.execute(sql2);
            statement.execute(sql3);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new GeneralResponse<>().of("All Rezervations canceled succesfully");
    }

    @Override
    public GeneralResponse<Rezervation> updateRezervation() {

        List<Rezervation> rezervations = new ArrayList<>();
        for (int i = 0; i < rezervationService.getAllRezervations().size(); i++) {
            System.out.println(rezervationService.getAllRezervations().get(i));
            rezervations.add(rezervationService.getAllRezervations().get(i));
        }
        int idRzrv = InputUtil.getInstance().inputInt("Whic rezervation do you want to update ?" +
                "Please enter Rezervation Id :");

        Rezervation rezervation = new Rezervation();
        for (int i = 0; i < rezervations.size(); i++) {
            if (rezervations.get(i).getRezervId() == idRzrv) {
                rezervation = rezervations.get(i);
            }
        }
        try {

            Connection connection = ConnectionConfig.connection();

            int option = InputUtil.getInstance().inputInt("What do you want to update in rezervation?\n" +
                    "[1]->Rezervation Id\n" +
                    "[2]->Customer Id\n" +
                    "[3]->Room Number\n" +
                    "[4]->Room Price\n" +
                    "[5]->Rezervation Date\n" +
                    "[6]->Rezervation Cancel Date\n" +
                    "Select:");
            switch (option) {
                case 1:
                    int newId = InputUtil.getInstance().inputInt("Please enter New Id :");
                    String sql = "update hotel_data.rezervation set id=? where id=?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(2, idRzrv);
                    preparedStatement.setInt(1, newId);
                    preparedStatement.execute();
                    rezervation.setId(newId);
                    break;
                case 2:
                    int cstId = InputUtil.getInstance().inputInt("Please Enter Customer new Id :");
                    String sql2 = "update hotel_data.rezervation set customer_id=? where id=?";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                    preparedStatement2.setInt(2, idRzrv);
                    preparedStatement2.setInt(1, cstId);
                    preparedStatement2.execute();
                    rezervation.setCustomerId(cstId);
                    break;
                case 3:
                    int newRoomNum = InputUtil.getInstance().inputInt("Please enter New Room Number :");
                    String sql3 = "update hotel_data.rezervation set room_number=? where id=?";
                    PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
                    preparedStatement3.setInt(2, idRzrv);
                    preparedStatement3.setInt(1, newRoomNum);
                    preparedStatement3.execute();
                    rezervation.setRoomNumber(newRoomNum);
                    break;
                case 4:
                    double price = InputUtil.getInstance().inputDouble("Please enter new Price :");
                    String sql4 = "update hotel_data.rezervation set room_price=? where id=?";
                    PreparedStatement preparedStatement4 = connection.prepareStatement(sql4);
                    preparedStatement4.setInt(2, idRzrv);
                    preparedStatement4.setDouble(1, price);
                    preparedStatement4.execute();
                    rezervation.setRoomPrice(price);
                    break;
                case 5:
                    String rezervDate = InputUtil.getInstance().inputString("Please enter new date (for Example->08-09-2002-18:53) :");
                    String sql5 = "update hotel_data.rezervation set rezervation_date=? where id=?";
                    PreparedStatement preparedStatement5 = connection.prepareStatement(sql5);
                    preparedStatement5.setInt(2, idRzrv);
                    preparedStatement5.setString(1, rezervDate);
                    preparedStatement5.execute();
                    String[] sArr = rezervDate.split("-");
                    int day = Integer.parseInt(sArr[0]);
                    int month = Integer.parseInt(sArr[1]);
                    int year = Integer.parseInt(sArr[2]);
                    String hoursMinutes = sArr[3];
                    String[] arr2 = hoursMinutes.split(":");
                    int hours = Integer.parseInt(arr2[0]);
                    int minutes = Integer.parseInt(arr2[1]);

                    LocalDateTime newDate = LocalDateTime.of(year, month, day, hours, minutes);
                    rezervation.setRezervDate(newDate);
                    break;
                case 6:
                    String cancelDate = InputUtil.getInstance().inputString("Please enter new cancel date (for Example->08-09-2002-18:53) :");
                    String sql6 = "update hotel_data.rezervation set rezerv_cancel_date=? where id=?";
                    PreparedStatement preparedStatement6 = connection.prepareStatement(sql6);
                    preparedStatement6.setInt(2, idRzrv);
                    preparedStatement6.setString(1, cancelDate);
                    preparedStatement6.execute();

                    String[] ss = cancelDate.split("-");
                    int dd = Integer.parseInt(ss[0]);
                    int mm = Integer.parseInt(ss[1]);
                    int yy = Integer.parseInt(ss[2]);
                    String hM = ss[3];
                    String[] arr = hM.split(":");
                    int hh = Integer.parseInt(arr[0]);
                    int mmm = Integer.parseInt(arr[1]);

                    LocalDateTime date = LocalDateTime.of(yy, mm, dd, hh, mmm);
                    rezervation.setRezervCancelDate(date);
                    break;
                default:
                    throw new GeneralException(Exception.OPERATION_NOT_FOUND_EXCEPTION);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new GeneralResponse<Rezervation>().of("Rezervation Updated Succesfully", rezervation);
    }

    @Override
    public void cancelRezervationById(int id) {
        Rezervation rezervation = new Rezervation();
        for (int i = 0; i < RezervationServiceHelper.rezervations().size(); i++) {
            if (RezervationServiceHelper.rezervations().get(i).getId() == id) {
                rezervation = RezervationServiceHelper.rezervations().get(i);
            }
        }
        try {
            String sql = "delete from hotel_data.rezervation where id=?";
            String sql2="update hotel_data.room set is_full=false where room_number=?";
            Connection connection = ConnectionConfig.connection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            PreparedStatement preparedStatement1=connection.prepareStatement(sql2);
            preparedStatement.setInt(1, id);
            preparedStatement1.setInt(1,rezervation.getRoomNumber());
            preparedStatement.execute();
            preparedStatement1.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

}
    @Override
    public GeneralResponse<?> cancelRezervationById() {
        for (int i = 0; i < RezervationServiceHelper.rezervations().size(); i++) {
            System.out.println(RezervationServiceHelper.rezervations().get(i));
        }
        int rzrzId= InputUtil.getInstance().inputInt("Which rezervation do you want cancel ? Please enter Rezervation Id :");
        Rezervation rezervation=new Rezervation();
        for (int i = 0; i < RezervationServiceHelper.rezervations().size(); i++) {
            if (RezervationServiceHelper.rezervations().get(i).getRezervId()==rzrzId){
                rezervation=RezervationServiceHelper.rezervations().get(i);
            }
        }
        try {
            String sql="delete from hotel_data.rezervation where id=?";
            String sql2="update hotel_data.room set is_full=false where room_number=?";
            Connection connection=ConnectionConfig.connection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            PreparedStatement preparedStatement1=connection.prepareStatement(sql2);
            preparedStatement.setInt(1,rzrzId);
            preparedStatement1.setInt(1,rezervation.getRoomNumber());
            preparedStatement.execute();
            preparedStatement1.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new GeneralResponse<>().of("Rezervation Deleted Succesfully");
    }

    @Override
    public GeneralResponse<List<Rezervation>> searchRezervation() {

        System.out.println("---- Searching Rezervation ----");
        String name=InputUtil.getInstance().inputString("Please Enter you name :");
        String surname=InputUtil.getInstance().inputString("Please Enter you surname :");
        Customer customer=new Customer();
        List<Customer> customers=new ArrayList<>();
        for (int i = 0; i < customerService.allCustomers().size(); i++) {
            if(customerService.allCustomers().get(i).getName().equals(name)
                    &&customerService.allCustomers().get(i).getSurname().equals(surname)){
                customer=customerService.allCustomers().get(i);
                customers.add(customer);
            }
        }

        List<Rezervation> rezervations=new ArrayList<>();
        Rezervation searchedRzrz=new Rezervation();

        for (int i = 0; i < rezervationService.getAllRezervations().size(); i++) {
           int id= rezervationService.getAllRezervations().get(i).getCustomerId();
            for (int j = 0; j < customers.size(); j++) {
                if(id==customers.get(j).getId()){
                    searchedRzrz=rezervationService.getAllRezervations().get(i);
                    rezervations.add(searchedRzrz);
                }
            }
        }
        return new GeneralResponse<List<Rezervation>>().of("The search operation has been succesfully comleted",rezervations);
    }
}
