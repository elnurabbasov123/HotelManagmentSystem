package main;

import helper.RezervationServiceHelper;
import model.Rezervation;
import service.impl.RezervationServiceImpl;
import service.impl.RoomServiceImpl;
import service.inter.RezervationService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import helper.RoomServiceHelper;
import service.inter.RoomService;

public class Test {
    static  RezervationService rezervationService = new RezervationServiceImpl();
    static RoomService roomService=new RoomServiceImpl();

    public static void main(String[] args) {

    }
}