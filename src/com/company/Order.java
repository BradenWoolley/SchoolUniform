package com.company;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Student student;
    List<Uniform> uniforms = new ArrayList();
    private float total;
    private int quantity;

    public Order(Student student) {
        this.setStudent(student);
    }

    public void AddUniform(int uniformCode) {
        if (uniformCode == 1) {
            this.uniforms.add(new Uniform("Summer", 150.0F));
        } else if (uniformCode == 2) {
            this.uniforms.add(new Uniform("Winter", 300.0F));
        }

    }

    public List<Uniform> getUniforms() {
        return this.uniforms;
    }

    public float getTotal() {
        return this.total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
