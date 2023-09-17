package service.inter;
import model.Customer;
import response.GeneralResponse;

import java.util.List;

public interface CustomerService {
    GeneralResponse<Customer> registerCustomer(Customer customer);

    GeneralResponse<List<Customer>> showCustomers();

    GeneralResponse<Customer> updateCustomer();

    GeneralResponse<Customer> deleteCustomer();

    GeneralResponse<List<Customer>> searchCustomer();

    List<Customer> allCustomers();
}
