package com.company;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        //Enter a student number from 1 - 4

        boolean canContinue = true;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your Student Number");
        String id = sc.next();
        Student loggedInStudent = (new Student()).Login(id);
        if (loggedInStudent.getId() != null) {
            PlaceOrder pl = new PlaceOrder();
            Order order = new Order(loggedInStudent);

            while(canContinue) {
                optionsUI();
                int choice = sc.nextInt();
                String buyAgain;
                if (choice != 1 && choice != 2) {
                    buyAgain = "N";
                } else {
                    order.AddUniform(choice);
                    System.out.println("Would you like to make another purchase? Y/N");
                    buyAgain = sc.next();
                }

                if (!buyAgain.equalsIgnoreCase("Y")) {
                    canContinue = false;
                }
            }

            if (order.getUniforms().size() > 0) {
                pl.PlaceNewOrder(order);
                System.out.println("Order successfully placed!");
                System.out.println("Would you like your file in JSON or XML format?");
                System.out.println("1.\tJSON");
                System.out.println("2.\tXML");
                int conversion = sc.nextInt();
                Context context;
                switch(conversion) {
                    case 1:
                        context = new Context(new ConvertJSON());
                        context.executeConversion(pl.LogInvoice(order), order);
                        break;
                    case 2:
                        context = new Context(new ConvertXML());
                        context.executeConversion(pl.LogInvoice(order), order);
                        break;
                    default:
                        System.out.println("Invalid display option chosen!");
                }
            }
        } else {
            System.out.println("Invalid Student ID");
        }
    }

    static void optionsUI() {
        System.out.println("Which uniform would you like to buy?");
        System.out.println("1.\tSummer");
        System.out.println("2.\tWinter");
        System.out.println("0.\tExit");
    }
}
