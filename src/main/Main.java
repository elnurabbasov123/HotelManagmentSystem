package main;
import enums.Exception;
import exception.GeneralException;
import helper.RezervationServiceHelper;
import helper.RoomServiceHelper;
import service.impl.CustomerServiceImpl;
import service.impl.RezervationServiceImpl;
import service.impl.RoomServiceImpl;
import service.inter.CustomerService;
import service.inter.RezervationService;
import service.inter.RoomService;
import util.InputUtil;
import util.MenuUtil;


public class Main {
    public static void main(String[] args) {
        CustomerService customerService=new CustomerServiceImpl();
        RoomService roomService=new RoomServiceImpl();
        RezervationService rezervationService=new RezervationServiceImpl();

        while (true) {
        int option=MenuUtil.entryMenu();
            switch (option){
                case 0:System.exit(0); break;
                case 1:RoomServiceHelper.checkRoom();RezervationServiceHelper.endRezerv();rezervationService.addRezerv();break;
                case 2:
                    int show=InputUtil.getInstance().inputInt("[1]->Show Customers\n" +
                            "[2]->Show Rooms\n" +
                            "[3]->Show Rezervations\n" +
                            "Select:");
                    switch (show){
                        case 1:customerService.showCustomers();break;
                        case 2:roomService.showRooms();break;
                        case 3:RezervationServiceHelper.endRezerv();rezervationService.showRezervations();break;
                        default: throw new GeneralException(Exception.OPERATION_NOT_FOUND_EXCEPTION);
                    }break;
                case 3:
                    int search=InputUtil.getInstance().inputInt("[1]->Search Room\n" +
                            "[2]->Search Customer\n" +
                            "[3]->Search Rezervation\n" +
                            "Select:");
                    switch (search){
                        case 1:roomService.searchRoom();break;
                        case 2:customerService.searchCustomer();break;
                        case 3:rezervationService.searchRezervation();break;
                        default:throw new GeneralException(Exception.OPERATION_NOT_FOUND_EXCEPTION);
                    }break;
                case 4:
                    int update=InputUtil.getInstance().inputInt("[1]->Update Room\n" +
                            "[2]->Update Customer\n" +
                            "[3]->Update Rezervation\n" +
                            "Select:");
                    switch (update){
                        case 1:roomService.updateRoom();break;
                        case 2:customerService.updateCustomer();break;
                        case 3:rezervationService.updateRezervation();break;
                        default:throw new GeneralException(Exception.OPERATION_NOT_FOUND_EXCEPTION);
                    }break;
                case 5:
                    int cancel=InputUtil.getInstance().inputInt("[1]->Cancel Room\n" +
                            "[2]->Cancel Customer\n" +
                            "[3]->Cancel All Rezervations\n" +
                            "[4]->Cancel one rezervation\n" +
                            "Select:");
                    switch (cancel){
                        case 1:roomService.deleteRoom();break;
                        case 2:customerService.deleteCustomer();break;
                        case 3:rezervationService.cancelAllRezervations();break;
                        case 4:rezervationService.cancelRezervationById();break;
                        default:throw new GeneralException(Exception.OPERATION_NOT_FOUND_EXCEPTION);
                    }break;
                default:throw new GeneralException(Exception.OPERATION_NOT_FOUND_EXCEPTION);
            }
        }
    }
}
