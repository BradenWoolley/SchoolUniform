package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Student {
    private String id;
    private String name;
    private String surname;

    public Student(String id, String name, String surname) {
        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
    }

    public Student() {
    }

    public Student Login(String studentID) {
        Student student = new Student();

        try {
            File studentList = new File("Students.txt");
            Scanner reader = new Scanner(studentList);

            while(reader.hasNext()) {
                String data = reader.nextLine();
                String[] splitData = data.split("#");
                if (studentID.equals(splitData[0])) {
                    student = new Student(splitData[0], splitData[1], splitData[2]);
                }
            }

            reader.close();
        } catch (FileNotFoundException var7) {
            System.out.println(var7.getMessage());
        }

        return student != null ? student : null;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
