package Servlet;

import Database.DBSingleton;
import Database.DBSingletonFactory;
import Database.DBTasks;
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
        String firstName = request.getParameter("fName");
        String lastName = request.getParameter("lname");
        String phone = request.getParameter("phone");
        String service = request.getParameter("service");
        String address = request.getParameter("address");
        System.out.println(firstName);
        System.out.println(service);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Staff> staffs = DBSingletonFactory.getInstanceDB().getStaffs();
        System.out.println(staffs);

        //DBtest db = new DBtest();
        //List<Staff> staffs = db.test();
        //System.out.println(staffs);

//        System.out.println(staffs);

        List<String> service = new ArrayList<String>();
        service.add("Spectra");
        service.add("VOIP");
        service.add("Delux");
        service.add("GACB");
        service.add("Budget");

        request.setAttribute("Service", service);

        RequestDispatcher dispatcher = request.getRequestDispatcher("customer.jsp");
        dispatcher.forward(request, response);
    }
}
