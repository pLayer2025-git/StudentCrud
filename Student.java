import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.*;

class Student {
    String name;
    int marks;
    int id;
    public Student(int id,String name, int marks) {
     this.id=id;
        this.name = name;
        this.marks = marks;
    }

    public String toString() {
        return "UID: "+id+", Name: " + name + ", Marks: " + marks;
    }
}

