import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentData {
    Scanner sc1 = new Scanner(System.in);
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> id = new ArrayList<>();
    ArrayList<Double> marks = new ArrayList<>();

    static final String FILE_NAME = "student.xlsx";
     void loadFromExcel() {
        try (FileInputStream fis = new FileInputStream(FILE_NAME);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            this.name.clear();
            this.id.clear();
            this.marks.clear();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                int id = (int) row.getCell(0).getNumericCellValue();
                String name = row.getCell(1).getStringCellValue();
                double marks = row.getCell(2).getNumericCellValue();

                this.id.add(id);
                this.name.add(name);
                this.marks.add(marks);
            }
            workbook.close();
            System.out.println(" Data loaded from Excel.");
        } catch (FileNotFoundException e) {
            System.out.println("Excel file not found. Starting with empty list.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void saveToExcel() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Students");

        // Header
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Marks");

        // Data
        int rowNum = 1;
        for (int i = 0; i < this.id.size(); i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(this.id.get(i));
            row.createCell(1).setCellValue(this.name.get(i));
            row.createCell(2).setCellValue(this.marks.get(i));
        }

        try (FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
            workbook.write(fos);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



        public void add ( int id, String name,double marks){
        if (this.id.indexOf(id) == -1) {
            this.name.add(name);
            this.id.add(id);
            this.marks.add(marks);
        } else {
            System.out.println("Dublicate ID not allowed");
        }
    }

        public void updateMarks ( int id){
        int i = this.id.indexOf(id);
        if (i > -1) {
            int t=0;
            while(t!=-1){
                try{
            double m = this.marks.get(i);
            System.out.println("Present marks- " + m);
            System.out.print("New marks ");
            double b = sc1.nextDouble();
            System.out.println("Done");
            this.marks.set(i, b);
            t=-1;}
                catch (InputMismatchException e) {
                    System.out.println("❌ Please enter a valid data!");
                    sc1.nextLine();

                }
            }

        } else {
            System.out.println("No student with this id found");
        }
    }


        public void viewAll () {
        if (!name.isEmpty()) {
            System.out.printf("%-10s %-15s %-10s\n", "UID", "Name", "Marks");
            for (int i = 0; i < name.size(); i++) {
                System.out.printf("%-10d %-15s %-10.2f \n", this.id.get(i), this.name.get(i), this.marks.get(i));
            }
        } else {
            System.out.println("No Student Data found!");
        }
    }
    }




