package helper;

import model.Customer;
import util.InputUtil;

public class CustomerServiceHelper {
    public static Customer fillCustomer(){
        String name= InputUtil.getInstance().inputString("Enter the name :");
        String surname= InputUtil.getInstance().inputString("Enter the surname :");
        String passwordFin= InputUtil.getInstance().inputString("Enter the passwordFin :");
        String phoneNumber= InputUtil.getInstance().inputString("Enter the phoneNumber :");

        Customer customer=new Customer(name,surname,passwordFin,phoneNumber);

        return customer;
    }
}
