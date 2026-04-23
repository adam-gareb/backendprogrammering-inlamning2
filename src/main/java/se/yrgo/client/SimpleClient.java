package se.yrgo.client;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.domain.Action;
import se.yrgo.domain.Call;
import se.yrgo.domain.Customer;
import se.yrgo.services.calls.CallHandlingService;
import se.yrgo.services.customers.CustomerManagementMockImpl;
import se.yrgo.services.customers.CustomerManagementService;
import se.yrgo.services.customers.CustomerNotFoundException;
import se.yrgo.services.diary.DiaryManagementService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

public class SimpleClient {

    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext container =
                     new ClassPathXmlApplicationContext("application.xml")) {

        // Del 1
        System.out.println("Del 1:\n");
//        CustomerManagementService customerManagement = container.getBean(CustomerManagementMockImpl.class);

        System.out.println("(har kommenterat bean ut för CustomerManagementMockImpl i application.xml och kommenterat ut koden här inne i SimpleClient)");
//        for(Customer customer : customerManagement.getAllCustomers()){
//            System.out.println(customer);
//        }

        System.out.println("\n---------------------------\n");

        //Del 2
        System.out.println("Del 2: \n");

            CustomerManagementService customerService = container.getBean("customerManagementService", CustomerManagementService.class);
            CallHandlingService callService = container.getBean(CallHandlingService.class);
            DiaryManagementService diaryService = container.getBean(DiaryManagementService.class);

            customerService.newCustomer(new Customer("CS03939", "Acme", "Good Customer"));

            Call newCall = new Call("Larry Wall called from Acme Corp");
            Action action1 = new Action("Call back Larry to ask how things are going", new GregorianCalendar(2016, 0, 0), "rac");
            Action action2 = new Action("Check our sales dept to make sure Larry is being tracked", new GregorianCalendar(2016, 0, 0), "rac");

            List<Action> actions = new ArrayList<Action>();
            actions.add(action1);
            actions.add(action2);

            try{
                callService.recordCall("CS03939", newCall, actions);
            }catch (CustomerNotFoundException e){
                System.out.println("That customer doesn't exist");
            }

            System.out.println("Here are the outstanding actions:");
            Collection<Action> incompleteActions = diaryService.getAllIncompleteActions("rac");
            for (Action next: incompleteActions){
                System.out.println(next);
            }

            System.out.println("\n---------------------------\n");

            // Del 3
            System.out.println("Del 3: \n");

            Customer newCustomer = new Customer("1", "Bradford Clinic", "brad@outlook.com", "0730000000", "Gotta call back!");

            customerService.newCustomer(newCustomer);
            System.out.println("Skapade kund: " + newCustomer);

            System.out.println("\nAlla kunder:");
            for (Customer c : customerService.getAllCustomers()) {
                System.out.println(c);
            }

            try {
                Customer found = customerService.findCustomerById("1");
                System.out.println("\nHittade kund med id " + found);
            } catch (CustomerNotFoundException e) {
                System.out.println("Kund hittades inte");
            }

            newCustomer.setNotes("Uppdaterade notes");
            customerService.updateCustomer(newCustomer);
            System.out.println("\nUppdaterade kund: " + customerService.findCustomerById("1").getNotes());

            // Testa findCustomersByName
            System.out.println("\nSök på namn:");
            for (Customer c : customerService.findCustomersByName("Bradford Clinic")) {
                System.out.println(c);
            }

            // Testa getFullCustomerDetail
            Customer fullDetail = customerService.getFullCustomerDetail("1");
            System.out.println("\nFull detalj: " + fullDetail);

            // Testa deleteCustomer
            customerService.deleteCustomer(newCustomer);
            System.out.println("\nRaderade kund, alla kunder kvar:");
            for (Customer c : customerService.getAllCustomers()) {
                System.out.println(c);
            }

            container.close();
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}