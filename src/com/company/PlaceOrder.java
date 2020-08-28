package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;

public class PlaceOrder {
    float total;
    int quantity;

    public PlaceOrder() {
    }

    public void PlaceNewOrder(Order order) {
        for(Iterator var2 = order.getUniforms().iterator(); var2.hasNext(); ++this.quantity) {
            Uniform uniform = (Uniform)var2.next();
            this.total += uniform.getPrice();
        }

        order.setTotal(this.total);
        order.setQuantity(this.quantity);
        this.LogInvoice(order);
    }

    public File LogInvoice(Order order) {
        LocalDate today = LocalDate.now();
        File invoice = new File(today + order.getStudent().getSurname() + "invoice.txt");

        try {
            if (invoice.createNewFile()) {
                System.out.println("File created: " + invoice.getName());
                FileWriter writer = new FileWriter(invoice, true);
                writer.write("Student#" + today + "#" + order.getStudent().getId() + "#" + order.getStudent().getName() + "#" + order.getStudent().getSurname() + "\n");
                Iterator var5 = order.getUniforms().iterator();

                while(var5.hasNext()) {
                    Uniform uniform = (Uniform)var5.next();
                    writer.write("Uniform#" + uniform.getName() + "#" + uniform.getPrice() + "\n");
                }

                writer.write("Details#" + this.quantity + "#" + this.total);
                writer.close();
            } else {
                System.out.println("Daily order already made: " + invoice.getName());
            }
        } catch (IOException var7) {
            System.out.println(var7.getMessage());
        }

        return invoice;
    }
}
