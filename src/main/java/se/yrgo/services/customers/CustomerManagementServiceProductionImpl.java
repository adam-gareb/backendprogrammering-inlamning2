package se.yrgo.services.customers;

import se.yrgo.dataaccess.CustomerDao;
import se.yrgo.dataaccess.RecordNotFoundException;
import se.yrgo.domain.Call;
import se.yrgo.domain.Customer;

import java.util.List;

public class CustomerManagementServiceProductionImpl implements CustomerManagementService {
    private CustomerDao customerDao;

    public CustomerManagementServiceProductionImpl(CustomerDao customerDao){
        this.customerDao = customerDao;
    }

    @Override
    public void newCustomer(Customer newCustomer) {

    }

    @Override
    public void updateCustomer(Customer changedCustomer) {

    }

    @Override
    public void deleteCustomer(Customer oldCustomer) {

    }

    @Override
    public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
        try{
            return customerDao.getById(customerId);
        }
        catch (RecordNotFoundException e){
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        return List.of();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return List.of();
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
        try{
            return customerDao.getFullCustomerDetail(customerId);
        }
        catch (RecordNotFoundException e){
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
        try{
            customerDao.addCall(callDetails, customerId);
        }
        catch (RecordNotFoundException e){
            throw new CustomerNotFoundException();
        }
    }
}
