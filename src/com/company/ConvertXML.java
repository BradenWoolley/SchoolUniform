package com.company;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ConvertXML implements IConvertFile {
    public ConvertXML() {
    }

    public void importFile(File invoice, Order order) {
        try {
            List<String> data = new ArrayList();
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("Order");
            document.appendChild(root);
            Scanner reader = new Scanner(invoice);

            while(reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                data.add(currentLine);
            }

            Element student = document.createElement("Student");
            root.appendChild(student);
            Attr attr = document.createAttribute("id");
            Element firstName = document.createElement("firstname");
            Element lastname = document.createElement("lastname");

            String[] firstLine;
            for(String line:data){
                firstLine = line.split("#");
                attr.setValue(firstLine[2]);
                firstName.appendChild(document.createTextNode(firstLine[3]));
                lastname.appendChild(document.createTextNode(firstLine[4]));
                break;
            }

            student.setAttributeNode(attr);
            student.appendChild(firstName);
            student.appendChild(lastname);
            int count = 0;

            for(String line:data){
                if(count < 1)
                    count++;
                else{

                    if(!line.contains("Details#")){
                        Element uniform = document.createElement("uniform");
                        Element uniformPrice = document.createElement("price");
                        String[] uniforms = line.split("#");
                        uniform.appendChild(document.createTextNode(uniforms[1]));
                        uniformPrice.appendChild(document.createTextNode(uniforms[2]));
                        student.appendChild(uniform);
                        student.appendChild(uniformPrice);
                    }
                    else{
                        Element details = document.createElement("details");
                        Element price = document.createElement("total");
                        Element quantity = document.createElement("quantity");
                        String[] info = line.split("#");
                        quantity.appendChild(document.createTextNode(info[1]));
                        price.appendChild(document.createTextNode(info[2]));
                        details.appendChild(quantity);
                        details.appendChild(price);
                        student.appendChild(details);

                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(invoice.getName().replace("txt", "xml")));
            transformer.transform(domSource, streamResult);
            System.out.println("Done creating XML File");
        } catch (FileNotFoundException | TransformerException | ParserConfigurationException var21) {
            var21.printStackTrace();
        }

        printFile(invoice);

    }

    @Override
    public void printFile(File invoice) {

        try
        {
            File file = new File(invoice.getName().replace("txt", "xml"));
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            //System.out.println("Root element: " + doc.getDocumentElement().getNodeName());


            NodeList names = doc.getElementsByTagName("firstname");
            NodeList surnames = doc.getElementsByTagName("lastname");
            NodeList uniforms = doc.getElementsByTagName("uniform");
            NodeList prices = doc.getElementsByTagName("price");
            NodeList quantities = doc.getElementsByTagName("quantity");
            NodeList totals = doc.getElementsByTagName("total");

            for(int i = 0; i < names.getLength(); i++){
                Node name = names.item(i);
                Node surname = surnames.item(i);
                if (name.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eName= (Element) name;
                    Element eSurname = (Element)surname;
                    System.out.println("Student: "+ eName.getTextContent() + " " + eSurname.getTextContent());
                }
            }

            for (int i = 0; i < uniforms.getLength(); i++)
            {
                Node uniform = uniforms.item(i);
                Node price = prices.item(i);

                if (uniform.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eUniform = (Element) uniform;
                    Element ePrice = (Element) price;
                    System.out.println("Uniform: "+ eUniform.getTextContent() + " " + ePrice.getTextContent());
                }
            }

            for(int i = 0; i < quantities.getLength(); i++){
                Node quantity = quantities.item(i);
                Node total = totals.item(i);
                if (quantity.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eName= (Element) quantity;
                    Element eSurname = (Element)total;
                    System.out.println("Quantity: "+ eName.getTextContent() + " Total: " + eSurname.getTextContent());
                }
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
