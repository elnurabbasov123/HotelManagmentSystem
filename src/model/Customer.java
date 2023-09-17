package model;

public class Customer {
    public static int idCount=0;

    int id;
    String name;
    String surname;
    String passwordFin;
    String phoneNumber;

    public Customer() {
    }

    public Customer(int id, String name, String surname, String passwordFin, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.passwordFin = passwordFin;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String name, String surname, String passwordFin, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.passwordFin = passwordFin;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPasswordFin() {
        return passwordFin;
    }

    public void setPasswordFin(String passwordFin) {
        this.passwordFin = passwordFin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +"id="+id+
                " name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", passwordFin='" + passwordFin + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';

    }
}
