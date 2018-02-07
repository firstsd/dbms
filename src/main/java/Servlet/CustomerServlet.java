package Servlet;

import Database.DBSingleton;
import Database.DBSingletonFactory;
import entity.CallingCode;
import entity.Customer;
import entity.Service;
import entity.Staff;
import org.omg.CORBA.Request;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CustomerServlet")
public class CustomerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer staffID = 2;
        String firstName = request.getParameter("fName");
        String lastName = request.getParameter("lName");
        String callingCode = request.getParameter("country");
        CallingCode country = DBSingletonFactory.getInstanceDB().getCountry(callingCode);
        String phone = request.getParameter("phone");
        String serviceID = request.getParameter("service");
        Service service = DBSingletonFactory.getInstanceDB().getService(serviceID);
        String address = request.getParameter("address");

        Customer customer = new Customer(staffID, firstName, lastName, phone, service, address, country);
        String checkStatus = DBSingletonFactory.getInstanceDB().addCustomer(customer);
        System.out.println(checkStatus);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Staff> staffs = DBSingletonFactory.getInstanceDB().getStaffs();
        System.out.println(staffs);

        //DBtest db = new DBtest();
        //List<Staff> staffs = db.test();
        //System.out.println(staffs);

//        System.out.println(staffs);

        List<Service> service = DBSingletonFactory.getInstanceDB().getServices();
        List<CallingCode> countries = DBSingletonFactory.getInstanceDB().getCountries();
        request.setAttribute("Service", service);
        request.setAttribute("countries", countries);

        RequestDispatcher dispatcher = request.getRequestDispatcher("customer.jsp");
        dispatcher.forward(request, response);
    }
}
