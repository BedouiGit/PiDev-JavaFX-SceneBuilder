package services;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import models.user;

import java.io.FileOutputStream;
import java.io.IOException;



public class userExcel {
    public static void exportToExcel(TableView<user> table, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("First Name");
        headerRow.createCell(2).setCellValue("Last Name");
        headerRow.createCell(3).setCellValue("Email");
        headerRow.createCell(4).setCellValue("Phone");
        headerRow.createCell(5).setCellValue("Age");
        headerRow.createCell(6).setCellValue("Address");
        headerRow.createCell(7).setCellValue("Sexe");


        // Populate data rows
        ObservableList<user> data = table.getItems();
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i + 1);
            user event = data.get(i);
            row.createCell(0).setCellValue(event.getId());
            row.createCell(1).setCellValue(event.getFirst_name());
            row.createCell(2).setCellValue(event.getLast_name());
            row.createCell(3).setCellValue(event.getEmail());
            row.createCell(4).setCellValue(event.getTel());
            row.createCell(5).setCellValue(event.getAge());
            row.createCell(6).setCellValue(event.getAddress());
            row.createCell(7).setCellValue(event.getGender());
        }

        // Write workbook to file
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            workbook.close();
            System.out.println("Excel file exported successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
