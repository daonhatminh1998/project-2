package edu.hccs.project2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {
    @GetMapping("/students")
    public List<Student> students() throws IOException {
        return readData();
    }

    public List<Student> readData() throws IOException {
        FileReader fileReader = new FileReader("student.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        List<Student> studentList = new ArrayList();
        String header = bufferedReader.readLine();
        String line = bufferedReader.readLine();

        while ( line != null){
            String data[] = line.split(",");// split each read line by comma

            Student student = new Student(Integer.parseInt(data[0]),data[1],Double.parseDouble(data[2]),data[3],data[4]);
            studentList.add(student);
            line = bufferedReader.readLine();
        }
        return studentList;
    }

    @GetMapping("/name/{searchName}")
    public List<Student> name(@PathVariable String searchName) throws IOException {
        System.out.println("search student name: "+searchName);

        List<Student>  studentList = readData();
        List<Student> result = new ArrayList<>();

        for(Student student : studentList){
            if( student.getFirst_name().equalsIgnoreCase(searchName)){
                System.out.println("found student name "+searchName);
                System.out.println();
                result.add(student);
            }
        }
        if(result.isEmpty())
        System.out.println("Not found student name "+searchName);
        System.out.println();
        return result;
    }

    @GetMapping("/student")
    public List<Student> student(@RequestParam double gpa, @RequestParam String gender) throws IOException {
        System.out.println("Search by gpa " + gpa + " gender " +gender);
        List<Student> studentList = readData();
        List<Student> result = new ArrayList<>();

        for(Student student : studentList ) {
            if(student.getGpa() == gpa & student.getGender().equalsIgnoreCase(gender.trim())) {
                result.add(student);
                System.out.println("Found Student has gpa " + gpa + " gender " + gender);
                System.out.println();
            }
        }
        if(result.isEmpty())
               System.out.println("Not Found Student has gpa " + gpa + " gender " + gender);
               System.out.println();
        return result;
    }

    @GetMapping("/gpa")
    public double averageGPA() throws IOException {
        List<Student> studentList = readData();
        double total = 0 ;

        for(Student student : studentList ) {
            total+=student.getGpa();
        }
        System.out.println("Total gpa " + total/studentList.size());
        System.out.println();
        return total/studentList.size();
    }
}
