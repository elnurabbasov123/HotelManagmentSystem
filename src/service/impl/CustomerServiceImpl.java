package service.impl;

import connection.ConnectionConfig;
import connection.Rs;
import globalData.GlobalData;
import helper.CustomerServiceHelper;
import model.Customer;
import response.GeneralResponse;
import service.inter.CustomerService;
import util.InputUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    static final CustomerService customerService=new CustomerServiceImpl();

    @Override
    public List<Customer> allCustomers() {
        List<Customer> customers=new ArrayList<>();

        try {
            String sql="select * from hotel_data.customer";
            ResultSet resultSet= Rs.dbResponse(sql);

            while (resultSet.next()){
                int csId=resultSet.getInt(1);
                String csName=resultSet.getString(2);
                String csSurname=resultSet.getString(3);
                String csPasswordFin=resultSet.getString(4);
                String csPhoneNumber=resultSet.getString(5);

                Customer customer=new Customer(csId,csName,csSurname,csPasswordFin,csPhoneNumber);

                customers.add(customer);

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    @Override
    public GeneralResponse<Customer> registerCustomer(Customer customer) {


        String sql="insert into hotel_data.customer(name,surname,password_fin,phone_number) " +
                "values(?,?,?,?)";
        try {
            Connection connection=ConnectionConfig.connection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql);

            preparedStatement.setString(1,customer.getName());
            preparedStatement.setString(2,customer.getSurname());
            preparedStatement.setString(3,customer.getPasswordFin());
            preparedStatement.setString(4,customer.getPhoneNumber());
            preparedStatement.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Customer cstmr=new Customer();
        for (int i = 0; i <customerService.allCustomers().size(); i++) {
            if(customerService.allCustomers().get(i).getPasswordFin().equals(customer.getPasswordFin())){
                cstmr.setId(customerService.allCustomers().get(i).getId());
                cstmr.setName(customerService.allCustomers().get(i).getName());
                cstmr.setSurname(customerService.allCustomers().get(i).getSurname());
                cstmr.setPasswordFin(customerService.allCustomers().get(i).getPasswordFin());
                cstmr.setPhoneNumber(customerService.allCustomers().get(i).getPhoneNumber());
            }
        }
        return new GeneralResponse<Customer>().of("Customer Registered Succesfully",cstmr);
    }

    @Override
    public GeneralResponse<List<Customer>> showCustomers() {
        List<Customer> customers=new ArrayList<>();

        try {
            String sql="select * from hotel_data.customer";
            ResultSet resultSet= Rs.dbResponse(sql);

            while (resultSet.next()){
                int csId=resultSet.getInt(1);
                String csName=resultSet.getString(2);
                String csSurname=resultSet.getString(3);
                String csPasswordFin=resultSet.getString(4);
                String csPhoneNumber=resultSet.getString(5);

                Customer customer=new Customer(csId,csName,csSurname,csPasswordFin,csPhoneNumber);

                customers.add(customer);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new GeneralResponse<List<Customer>>().of("Customers Showed Succesfully",customers);
    }

    @Override
    public GeneralResponse<Customer> updateCustomer() {
        Customer customer=new Customer();
        for (int i = 0; i < allCustomers().size(); i++) {
            System.out.println(allCustomers().get(i));
        }
        int id= InputUtil.getInstance().inputInt("Which customer do you want to update ? Please enter Id Customer :");
        for (int i = 0; i < allCustomers().size(); i++) {
            if (allCustomers().get(i).getId()==id){
                customer=allCustomers().get(i);
            }
        }
        int cstId=customer.getId();
        int select=InputUtil.getInstance().inputInt("What do you want to update ?\n" +
                "[1]->Id\n" +
                "[2]->Name\n" +
                "[3]->Surname\n" +
                "[4]->PasswordFin\n" +
                "[5]->Phone Number\n" +
                "Select:");
        try {
            Connection connection=ConnectionConfig.connection();
            switch (select){
                case 1:
                    int id1=InputUtil.getInstance().inputInt("Please Enter New Id :");
                    String sql="update hotel_data.customer set id=? where id=?";
                    PreparedStatement preparedStatement=connection.prepareStatement(sql);
                    preparedStatement.setInt(2,cstId);
                    preparedStatement.setInt(1,id1);
                    preparedStatement.execute();
                    customer.setId(id1);
                    break;
                case 2:
                    String name=InputUtil.getInstance().inputString("Please Enter New Name :");
                    String sql2="update hotel_data.customer set name=? where id=?";
                    PreparedStatement preparedStatement2=connection.prepareStatement(sql2);
                    preparedStatement2.setInt(2,cstId);
                    preparedStatement2.setString(1,name);
                    preparedStatement2.execute();
                    customer.setName(name);
                    break;
                case 3:
                    String surname=InputUtil.getInstance().inputString("Please Enter New Surname :");
                    String sql3="update hotel_data.customer set surname=? where id=?";
                    PreparedStatement preparedStatement3=connection.prepareStatement(sql3);
                    preparedStatement3.setInt(2,cstId);
                    preparedStatement3.setString(1,surname);
                    preparedStatement3.execute();
                    customer.setSurname(surname);
                    break;
                case 4:
                    String fin=InputUtil.getInstance().inputString("Please Enter New Password Fin :");
                    String sql4="update hotel_data.customer set password_fin=? where id=?";
                    PreparedStatement preparedStatement4=connection.prepareStatement(sql4);
                    preparedStatement4.setInt(2,cstId);
                    preparedStatement4.setString(1,fin);
                    preparedStatement4.execute();
                    customer.setPasswordFin(fin);
                    break;
                case 5:
                    String phone=InputUtil.getInstance().inputString("Please Enter New Phone Number");
                    String sql5="update hotel_data.customer set name=? where id=?";
                    PreparedStatement preparedStatement5=connection.prepareStatement(sql5);
                    preparedStatement5.setInt(2,cstId);
                    preparedStatement5.setString(1,phone);
                    preparedStatement5.execute();
                    customer.setPhoneNumber(phone);
                    break;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new GeneralResponse<Customer>().of("Customer Updated Succesfully",customer);
    }

    @Override
    public GeneralResponse<Customer> deleteCustomer() {
        for (int i = 0; i < allCustomers().size(); i++) {
            System.out.println(allCustomers().get(i));
        }
        Customer customer=new Customer();
        int id=InputUtil.getInstance().inputInt("Which customer do you want to delete ? Please Enter Customer Id :");
        for (int i = 0; i < allCustomers().size(); i++) {
            if(allCustomers().get(i).getId()==id){
                customer=allCustomers().get(i);
            }
        }
        try {
            String sql="delete from hotel_data.customer where id=?";
            Connection connection=ConnectionConfig.connection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new GeneralResponse<Customer>().of("Customer Deleted Succesfully",customer);
    }

    @Override
    public GeneralResponse<List<Customer>> searchCustomer() {
        System.out.println("----Searching Customer----");
        String name=InputUtil.getInstance().inputString("Please Enter Customer Name :");
        String surname=InputUtil.getInstance().inputString("Please Enter Customer Surname :");
        List<Customer> customers=new ArrayList<>();
        Customer customer=new Customer();
        for (int i = 0; i < allCustomers().size(); i++) {
            if(allCustomers().get(i).getName().equals(name)&&allCustomers().get(i).getSurname().equals(surname)){
                customer=allCustomers().get(i);
                customers.add(customer);
            }
        }
        return new GeneralResponse<List<Customer>>().of("Customer Searching Comleted Succesfully",customers);
    }
}
