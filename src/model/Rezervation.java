package model;
import service.impl.CustomerServiceImpl;
import service.impl.RezervationServiceImpl;
import service.impl.RoomServiceImpl;
import service.inter.CustomerService;
import service.inter.RezervationService;
import service.inter.RoomService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Rezervation {
    public static int rezervId=0;

    int id;
    int customerId;
    int roomNumber;
    double roomPrice;
    LocalDateTime rezervDate;
    LocalDateTime rezervCancelDate;

    public Rezervation(int id, int customerId, int roomNumber, double roomPrice,LocalDateTime rezervDate,LocalDateTime rezervCancelDate) {
        this.id = id;
        this.customerId = customerId;
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.rezervDate=rezervDate;
        this.rezervCancelDate=rezervCancelDate;
    }

    public Rezervation(int customerId, int roomNumber, double roomPrice,LocalDateTime rezervDate,LocalDateTime rezervCancelDate) {
        this.customerId = customerId;
        this.roomNumber = roomNumber;
        this.roomPrice=roomPrice;
        this.rezervDate=rezervDate;
        this.rezervCancelDate=rezervCancelDate;
    }
    public Rezervation() {
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getRezervCancelDate() {
        return rezervCancelDate;
    }

    public void setRezervCancelDate(LocalDateTime rezervCancelDate) {
        this.rezervCancelDate = rezervCancelDate;
    }

    public int getRezervId() {
        return id;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public LocalDateTime getRezervDate() {
        return rezervDate;
    }

    public void setRezervDate(LocalDateTime rezervDate) {
        this.rezervDate = rezervDate;
    }

    RoomService roomService=new RoomServiceImpl();
    CustomerService customerService=new CustomerServiceImpl();
    RezervationService rezervationService=new RezervationServiceImpl();

        @Override
    public String toString() {
            Customer customer=new Customer();
            for (int i = 0; i < customerService.allCustomers().size(); i++) {
            if(customerService.allCustomers().get(i).getId()==customerId){
                customer=customerService.allCustomers().get(i);
            }
            }
            Room room=new Room();
            for (int i = 0; i < roomService.getRooms().size(); i++) {
                if(roomService.getRooms().get(i).getRoomNumber()==roomNumber){
                    room=roomService.getRooms().get(i);
                }
            }
            LocalDateTime cancelDt = null;
            for (int i = 0; i < rezervationService.getAllRezervations().size(); i++) {
                if(rezervationService.getAllRezervations().get(i).getRoomNumber()==roomNumber){
                    cancelDt=rezervationService.getAllRezervations().get(i).getRezervCancelDate();
                }
            }
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy-HH':'mm");

        return
                "rezervId="+id+"\n"
                        +customer+"\n"+
                        "Room{"+"roomNumber="+room.getRoomNumber()+ ",roomType="+room.getRoomType()+"}\n" +
                        "the price of your rezervation :"+roomPrice+"$\n" +
                        "rezervDate  : "+formatter.format(rezervDate)+"\n" +
                        "cancelDate  : "+cancelDt.format(formatter);
    }

}
