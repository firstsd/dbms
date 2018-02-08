package Servlet;

import Database.DBSingletonFactory;
import entity.Customer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BillServlet")
public class BillServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phoneNo = request.getParameter("phoneNo");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        DBSingletonFactory.getInstanceDB().createBill(phoneNo,startDate, endDate);
        Customer customer = DBSingletonFactory.getInstanceDB().getCustomer(phoneNo);
        Double amountDue = DBSingletonFactory.getInstanceDB().amountDue(phoneNo, startDate, endDate);

        System.out.println(customer.getfName());
        System.out.println( phoneNo +" " +startDate +" "+ endDate);

        System.out.println(amountDue);
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/bill.jsp");
        dispatcher.forward(request, response);
    }
}
