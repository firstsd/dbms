package Servlet;

import Database.DBSingletonFactory;
import entity.Customer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@WebServlet(name = "BillServlet")
public class BillServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phoneNo = request.getParameter("phoneNo");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        String lastFile = "D:/data/export/customerBill_" + phoneNo + "_" + startDate + "_" + endDate + ".xlsx";
        String absoluteFile = "D:/data/export/bill.xlsx";
        if (startDate != null && endDate != null && phoneNo != null) {
            // workbook
            Workbook wb = new XSSFWorkbook();
            // sheet
            Sheet sheet1 = wb.createSheet("CustomerDetail");
            // row
            Row row1 = sheet1.createRow(0);
            // column
            Cell cell1 = row1.createCell(0);
            Cell cell2 = row1.createCell(1);
            Cell cell3 = row1.createCell(2);
            Cell cell4 = row1.createCell(3);
            cell1.setCellValue("countryName");
            cell2.setCellValue("duration");
            cell3.setCellValue("callTime");
            cell4.setCellValue("cost");
            try {
                FileOutputStream fileOut = new FileOutputStream(absoluteFile);
                wb.write(fileOut);
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(absoluteFile);
            String result = DBSingletonFactory.getInstanceDB().createBill(phoneNo, startDate, endDate);
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

            Customer customer = DBSingletonFactory.getInstanceDB().getCustomer(phoneNo);
            Double amountDue = DBSingletonFactory.getInstanceDB().amountDue(phoneNo, startDate, endDate);

            try {
                FileInputStream file = new FileInputStream(new File(lastFile));
                Workbook workbook2 = WorkbookFactory.create(file);

                // Getting the Sheet at index zero
                Sheet sheet = workbook2.createSheet("CustomerInfo");
                Row r1 = sheet.createRow(0);
                Cell c1_1 = r1.createCell(0);
                Cell c1_2 = r1.createCell(1);
                c1_1.setCellValue("FirstName: ");
                c1_2.setCellValue(customer.getfName());
                Row r2 = sheet.createRow(1);
                Cell c2_1 = r2.createCell(0);
                Cell c2_2 = r2.createCell(1);
                c2_1.setCellValue("LastName: ");
                c2_2.setCellValue(customer.getlName());
                Row r3 = sheet.createRow(2);
                Cell c3_1 = r3.createCell(0);
                Cell c3_2 = r3.createCell(1);
                c3_1.setCellValue("Address:");
                c3_2.setCellValue(customer.getAddress());
                Row r4 = sheet.createRow(3);
                Cell c4_1 = r4.createCell(0);
                Cell c4_2 = r4.createCell(1);
                c4_1.setCellValue("PhoneNo:");
                c4_2.setCellValue(customer.getPhoneNo());
                Row r5 = sheet.createRow(4);
                Cell c5_1 = r5.createCell(0);
                Cell c5_2 = r5.createCell(1);
                c5_1.setCellValue("AmountDue:");
                c5_2.setCellValue(amountDue);
                FileOutputStream outputStream = new FileOutputStream(lastFile);
                workbook2.write(outputStream);
                workbook2.close();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
        }
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/bill.jsp");
        dispatcher.forward(request, response);
    }
}
