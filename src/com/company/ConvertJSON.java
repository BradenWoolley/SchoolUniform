package com.company;

import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sun.plugin.javascript.navig.Anchor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConvertJSON implements IConvertFile{

    public ConvertJSON(){

    }

    @Override
    public void importFile(File invoice, Order order) {
        try{
            List<String> data = new ArrayList();

            JSONObject details = new JSONObject();
            JSONArray uniforms = new JSONArray();
            JSONArray prices = new JSONArray();


            Scanner reader = new Scanner(invoice);

            while(reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                data.add(currentLine);
            }

            for(String orderData:data){

                String[] lineData = orderData.split("#");


                if(lineData[0].equalsIgnoreCase("uniform")){
                    uniforms.add(lineData[1]);
                    prices.add(lineData[2]);

                }

                else if(lineData[0].equalsIgnoreCase("details")){
                    details.put("quantity", lineData[1]);
                    details.put("total", lineData[2]);
                }

                else if(lineData[0].equalsIgnoreCase("student")){
                    details.put("id", lineData[2]);
                    details.put("name", lineData[3]);
                    details.put("surname", lineData[4]);
                }

            }

            details.put("uniform", uniforms);
            details.put("price", prices);


            FileWriter file = new FileWriter(invoice.getName().replace("txt", "json"));

            file.write(details.toJSONString());
            file.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        printFile(invoice);
    }

    @Override
    public void printFile(File invoice) {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(invoice.getName().replace("txt", "json"));

            Object obj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject)obj;

            String id = (String)jsonObject.get("id");
            String name = (String)jsonObject.get("name");
            String surname = (String)jsonObject.get("surname");
            String total = (String)jsonObject.get("total");
            String quantity = (String)jsonObject.get("quantity");
            System.out.println("Student: " + id + " " + name + " "+surname);
            JSONArray uniforms = (JSONArray)jsonObject.get("uniform");

            JSONArray prices = (JSONArray)jsonObject.get("price");

            for(int i = 0; i < uniforms.size(); i++){
                System.out.println("Uniform: "+ uniforms.get(i) + " R" + prices.get(i));
            }

            System.out.println("Quantity: " + quantity);
            System.out.println("Total: " + total);


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

}
