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
import java.util.Scanner;


public class StudentList {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        StudentData ob = new StudentData();
        ob.loadFromExcel();
        while (true) {
            int t = 0;int choice=0;
            try {
                System.out.println("\n1. View All\n2. Add Student\n3. Update Marks\n4. Exit");
                choice = sc.nextInt();


                switch (choice) {
                    case 1:
                        ob.viewAll();
                        break;
                    case 2:


                        sc.nextLine();
                        System.out.println("Student Name");
                        String n = sc.nextLine();
                        System.out.println("Marks");
                        double m = sc.nextDouble();
                        System.out.println("unique id (uid)");
                        int u = sc.nextInt();
                        ob.add(u, n, m);
                        break;
                    case 3:
                        System.out.println("Enter unique ID of student");
                        ob.updateMarks(sc.nextInt());
                        break;
                    case 4:
                        t = -1;
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Please enter a valid data!");
                sc.nextLine();
                //choice = sc.nextInt();
            }


            if (t == -1) {
                ob.saveToExcel();
                System.out.println("Saved to Excel. Exiting...");
                return;
            }
        }
    }
}
