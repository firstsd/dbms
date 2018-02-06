package Servlet;

import Database.DBSingleton;
import Database.DBSingletonFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "RateServlet")
public class RateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("rate.jsp");
        dispatcher.forward(request, response);
        String export = request.getParameter("export");
//        DBSingletonFactory.getInstanceDB()
        String filename = null;
        if (export.equals("rate")) {
            // workbook
            Workbook wb = new XSSFWorkbook();
            // sheet
            Sheet sheet1 = wb.createSheet("Sheet1");
            // row
            Row row1 = sheet1.createRow(0);
            // column
            Cell cell1 = row1.createCell(0);
            Cell cell2 = row1.createCell(1);
            Cell cell3 = row1.createCell(2);
            Cell cell4 = row1.createCell(3);
            Cell cell5 = row1.createCell(4);
            Cell cell6 = row1.createCell(5);
            Cell cell7 = row1.createCell(6);
            cell1.setCellValue("rateID");
            cell2.setCellValue("serviceID");
            cell3.setCellValue("fromCode");
            cell4.setCellValue("destCode");
            cell5.setCellValue("peak");
            cell6.setCellValue("offPeak");
            cell7.setCellValue("changeDate");
            try {
                filename = "c:/Users/SD/Desktop/Project/export/rate.xlsx";
                FileOutputStream fileOut = new FileOutputStream(filename);
                wb.write(fileOut);
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(filename);
            String result = DBSingletonFactory.getInstanceDB().exportRate();
            System.out.println(result);
            if (result.equals("success")) {
                request.setAttribute("error", "File exported successfully!!");
            } else {
                request.setAttribute("error", result);
            }
        } else {

        }
    }
}
