package Servlet;

import Database.DBSingletonFactory;
import entity.ProcessCall;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProcessCallServlet")
public class ProcessCallServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String processFile = request.getParameter("processFile");
        String path = "d:\\data\\import\\";
        String fullpath = path + processFile;
        String test = path + "call_200711.xlsx";
        System.out.println(test);
        try {
            // Creating a Workbook from an Excel file (.xls or .xlsx)
            Workbook workbook = WorkbookFactory.create(new File(test));
            // Getting the Sheet at index zero
            Sheet sheet = workbook.getSheetAt(0);
            String result = DBSingletonFactory.getInstanceDB().updateRate(test, "calls", sheet.getSheetName());
            if (result == null) {
                request.setAttribute("error", "Import");
                request.setAttribute("errorType", 1);
            } else if (result.equals("success")) {
                request.setAttribute("error", "File imported successfully!!");
                request.setAttribute("errorType", 0);
            } else {
                request.setAttribute("error", result);
            }
            workbook.close();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("processcall.jsp");
        List<ProcessCall> processCalls = DBSingletonFactory.getInstanceDB().getCalls();
        request.setAttribute("processCall", processCalls);
        dispatcher.forward(request, response);
    }
}
