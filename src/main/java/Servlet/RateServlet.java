package Servlet;

import Database.DBSingleton;
import Database.DBSingletonFactory;
import entity.CallingCode;
import entity.Customer;
import entity.RateTmp;
import entity.Service;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "RateServlet")
public class RateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String exportBtn = request.getParameter("export");
        String importBtn = request.getParameter("import");
        List<String> errorList = new ArrayList<String>();

//        if (importBtn != null) {
//            String rateFile = request.getParameter("rateFile");
//            String path = "d:\\data\\import\\";
//            String fullpath = path + rateFile;
//            DBSingletonFactory.getInstanceDB().maintainRate();
//            try {
//                // Creating a Workbook from an Excel file (.xls or .xlsx)
//                Workbook workbook = WorkbookFactory.create(new File(fullpath));
//                for (Sheet sheet : workbook) {
//                    System.out.println("=> " + sheet.getSheetName());
//                    String result = DBSingletonFactory.getInstanceDB().updateRate(fullpath, "rate", sheet.getSheetName());
//                    if (result == null) {
//                        errorList.add(result);
//                        request.setAttribute("importError", "There is no records found");
//                        request.setAttribute("importErrorType", 1);
//                    } else if (result.equals("success")) {
//                        errorList.add(result);
//                        request.setAttribute("importError", "File exported successfully!!");
//                        request.setAttribute("importErrorType", 0);
//                    } else {
//                        errorList.add(result);
//                        request.setAttribute("importError", result);
//                    }
//                }
//                System.out.println(errorList);
//            } catch (InvalidFormatException e) {
//                e.printStackTrace();
//            }
//            doGet(request, response);
//        }

        if (exportBtn != null) {
            String fromCountry = request.getParameter("country");
            String service = request.getParameter("service");
            Integer fromCountryID = Integer.parseInt(fromCountry);
            Integer serviceID = Integer.parseInt(service);
            String serviceName = DBSingletonFactory.getInstanceDB().getService(service).getServiceName();
            String fromCountryName = DBSingletonFactory.getInstanceDB().getCountry(fromCountry).getCountryName();
            String lastFile = "d:/data/export/" + serviceName + "_" + fromCountryName + ".xlsx";
            String absoluteFile = "d:/data/export/rate.xlsx";
            if (fromCountry != null && service != null) {
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
                String result = DBSingletonFactory.getInstanceDB().exportRate(serviceID, fromCountryID);
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
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<RateTmp> rateTmpList = DBSingletonFactory.getInstanceDB().getRates();
        request.setAttribute("rateTmpList", rateTmpList);
        List<Service> service = DBSingletonFactory.getInstanceDB().getServices();
        List<CallingCode> countries = DBSingletonFactory.getInstanceDB().getCountries();
        request.setAttribute("Service", service);
        request.setAttribute("countries", countries);
        RequestDispatcher dispatcher = request.getRequestDispatcher("rate.jsp");
        dispatcher.forward(request, response);
    }
}
