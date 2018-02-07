package Servlet;

import Database.DBSingletonFactory;
import entity.RateTmp;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "RateServlet")
public class RateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fromCountry = request.getParameter("fromCountry");
        String serviceName = request.getParameter("serviceName");
        String lastFile = "c:/Users/SD/Desktop/Project/export/" + serviceName + "_" + fromCountry + ".xlsx";
        String absoluteFile = "c:/Users/SD/Desktop/Project/export/rate.xlsx";
        if (fromCountry != null && serviceName != null) {
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
            cell1.setCellValue("toCountry");
            cell2.setCellValue("peak");
            cell3.setCellValue("offPeak");
            try {
                FileOutputStream fileOut = new FileOutputStream(absoluteFile);
                wb.write(fileOut);
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(absoluteFile);
            String result = DBSingletonFactory.getInstanceDB().exportRate(serviceName, fromCountry);
            if (result == null) {
                File oldFile = new File(absoluteFile);
                oldFile.delete();
                request.setAttribute("error", "There is no records found");
                request.setAttribute("errorType", 1);
            } else if (result.equals("success")) {
                File oldFile = new File(absoluteFile);
                File destFile = new File(lastFile);
                oldFile.renameTo(destFile);
                request.setAttribute("error", "File exported successfully!!");
                request.setAttribute("errorType", 0);
                request.setAttribute("filepath", lastFile);
            } else {
                request.setAttribute("error", result);
            }
        }
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<RateTmp> rateTmpList = DBSingletonFactory.getInstanceDB().getRates();
        request.setAttribute("rateTmpList", rateTmpList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("rate.jsp");
        dispatcher.forward(request, response);
    }
}
