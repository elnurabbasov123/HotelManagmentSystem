package helper;
import connection.ConnectionConfig;
import model.Rezervation;
import service.impl.RezervationServiceImpl;
import service.inter.RezervationService;
import util.InputUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RezervationServiceHelper {
    static RezervationService rezervationService=new RezervationServiceImpl();

    public static int chooseRoomType(){
        int roomType= InputUtil.getInstance().inputInt(
                "[1]->standart.\n" +
                "[2]->delux.\n" +
                "[3]->triple.\n" +
                "[4]->vip.\n"+
                        "which type of room do you want to reserve ? :");
        if(roomType<1&&roomType>4){
            System.out.println("Please select correct roomType");
            chooseRoomType();
        }
        return roomType;
    }
    public static void endRezerv(){
        LocalDateTime nowDate=LocalDateTime.now();

        List<Rezervation> rezervationsList=new ArrayList<>();

        for (int i = 0; i <RezervationServiceHelper.rezervations().size() ; i++) {
            if(RezervationServiceHelper.rezervations().get(i).getRezervCancelDate().isBefore(nowDate)){
                rezervationsList.add(RezervationServiceHelper.rezervations().get(i));
            }
        }
        for (Rezervation rezervation:rezervationsList) {
            rezervationService.cancelRezervationById(rezervation.getId());
        }
    }
//    public static void endRezerv() {
//        LocalDateTime nowDate = LocalDateTime.now();
//        List<Rezervation> rezervations = RezervationServiceHelper.rezervations();
//
//        for (int i = 0; i < rezervations.size(); i++) {
//            if (rezervations.get(i).getRezervCancelDate().isBefore(nowDate)) {
//                rezervationService.cancelRezervationById(rezervations.get(i).getId());
//                // Silinen rezervasyonları listeden kaldır
//                rezervations.remove(i);
//                // Döngüde bir geri gitmek için i'yi azalt
//                i--;
//            }
//        }
//    }

    public static List<Rezervation> rezervations(){

        List<Rezervation> rezervations=new ArrayList<>();

        String sql="select * from hotel_data.rezervation";
        try {
            Connection connection= ConnectionConfig.connection();
            Statement statement=connection.createStatement();
            statement.execute(sql);
            ResultSet resultSet=statement.getResultSet();
            while (resultSet.next()){

                int rezervId=resultSet.getInt(1);
                int csId=resultSet.getInt(2);
                int csRoomNumber=resultSet.getInt(3);
                double csRoomPrice=resultSet.getDouble(4);

                String rezervdate=resultSet.getString(5);
                String[] arr=rezervdate.split("-");
                int day=Integer.parseInt(arr[0]);
                int month=Integer.parseInt(arr[1]);
                int year=Integer.parseInt(arr[2]);
                String hoursAndMinutes=arr[3];
                String[] arrDM=hoursAndMinutes.split(":");
                int hours=Integer.parseInt(arrDM[0]);
                int minutes=Integer.parseInt(arrDM[1]);
                LocalDateTime rezervDate=LocalDateTime.of(year,month,day,hours,minutes);

                String rezervCancelDate=resultSet.getString(6);
                String[] arr2=rezervCancelDate.split("-");
                int day2=Integer.parseInt(arr2[0]);
                int month2=Integer.parseInt(arr2[1]);
                int year2=Integer.parseInt(arr2[2]);

                String hoursAndMinutes2=arr2[3];
                String[] arrDM2=hoursAndMinutes2.split(":");
                int hours2=Integer.parseInt(arrDM2[0]);
                int minutes2=Integer.parseInt(arrDM2[1]);

                LocalDateTime cancelDate=LocalDateTime.of(year2,month2,day2,hours2,minutes2);

                Rezervation rezervation=new Rezervation(rezervId,csId,csRoomNumber,csRoomPrice,rezervDate,cancelDate);

                rezervations.add(rezervation);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezervations;
    }
}
