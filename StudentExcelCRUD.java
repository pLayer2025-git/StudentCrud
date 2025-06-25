import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentExcelCRUD {
    static final String FILE_NAME = "student.xlsx";
    static List<Student> studentList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        loadFromExcel();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. View All\n2. Add Student\n3. Update Marks\n4. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewAll();
                case 2 -> addStudent(sc);
                case 3 -> updateStudent(sc);
                case 4 -> {
                    saveToExcel();
                    System.out.println("Saved to Excel. Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void viewAll() {
        if (studentList.isEmpty()) {
            System.out.println("No records found.");
        } else {
            for (int i = 0; i < studentList.size(); i++) {
                System.out.println((i + 1) + ". " + studentList.get(i));
            }
        }
    }

    static void addStudent(Scanner sc) {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.println("Enter id");
        int u=sc.nextInt();
        System.out.print("Enter Marks: ");
        int marks = sc.nextInt();
        studentList.add(new Student(u,name, marks));
        System.out.println("Student added.");
    }

    static void updateStudent(Scanner sc) {
        viewAll();
        System.out.print("Enter student number to update: ");
        int index = sc.nextInt() - 1;
        if (index >= 0 && index < studentList.size()) {
            System.out.print("Enter new marks: ");
            int newMarks = sc.nextInt();
            studentList.get(index).marks = newMarks;
            System.out.println("Updated successfully.");
        } else {
            System.out.println("Invalid student number.");
        }
    }

    static void loadFromExcel() {
        try (FileInputStream fis = new FileInputStream(FILE_NAME);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            studentList.clear();
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header
                String name = row.getCell(1).getStringCellValue();
                int marks = (int) row.getCell(2).getNumericCellValue();
                int uid=(int)row.getCell(0).getNumericCellValue();
                studentList.add(new Student(uid,name, marks));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Excel file not found. Starting with empty list.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void saveToExcel() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Students");

        // Header
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("UID");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Marks");

        // Data
        int rowNum = 1;
        for (Student s : studentList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(s.id);
            row.createCell(1).setCellValue(s.name);
            row.createCell(2).setCellValue(s.marks);
        }

        try (FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
            workbook.write(fos);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
