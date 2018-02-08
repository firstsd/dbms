package Servlet;

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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@WebServlet(name = "Commision")
public class Commision extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        String lastFile = "D:/data/export/salesRep_" + startDate + "_" + endDate + ".xlsx";
        String absoluteFile = "D:/data/export/salesRepCommission.xlsx";
        if (startDate != null && endDate != null) {
            // workbook
            Workbook wb = new XSSFWorkbook();
            // sheet
            Sheet sheet1 = wb.createSheet("Sheet1");
            // row
            Row row1 = sheet1.createRow(0);
            // column
            Cell cell1 = row1.createCell(0);
            Cell cell2 = row1.createCell(1);
            cell1.setCellValue("saleRepID");
            cell2.setCellValue("commission");
            try {
                FileOutputStream fileOut = new FileOutputStream(absoluteFile);
                wb.write(fileOut);
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(absoluteFile);
            String result = DBSingletonFactory.getInstanceDB().exportSalesRepCommission(startDate, endDate);
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
            wb.close();
        }
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/calcCommision.jsp");
        dispatcher.forward(request, response);
    }
}
