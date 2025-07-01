package com.mycompany.createparsedisplay;

import java.awt.HeadlessException;
import org.json.JSONObject;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.regex.*;
import javax.swing.*;
import org.json.JSONException;
import org.xml.sax.SAXException;

public class ParseAndDisplay {

    public static void main(String[] args) {
        createExampleFiles();
        parseJSON();
        parseXML();
        parseMoodle();
        parseCSV();
    }

    // Crear archivos de ejemplo antes de parsear
    private static void createExampleFiles() {
        CreateFiles.createJSONFile();
        CreateFiles.createXMLFile();
        CreateFiles.createMoodleFile();
        CreateFiles.createCSVFile();
    }

    // Método para parsear JSON usando la librería org.json
    private static void parseJSON() {
        try {
            File file = new File("src/main/java/com/mycompany/createparsedisplay/example.json");
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder jsonData = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonData.append(line);
            }
            JSONObject jsonObject = new JSONObject(jsonData.toString());

            // Crear el formulario para mostrar JSON
            JFrame jsonFrame = new JFrame("JSON Data");
            jsonFrame.setLocationRelativeTo(null);
            jsonFrame.setSize(400, 200);
            JTextArea textArea = new JTextArea();
            textArea.setText("Name: " + jsonObject.getString("name") + "\n" +
                             "Age: " + jsonObject.getInt("age") + "\n" +
                             "City: " + jsonObject.getString("city"));
            jsonFrame.add(textArea);
            jsonFrame.setVisible(true);

        } catch (HeadlessException | IOException | JSONException e) {
            System.err.println("\nERROR: "+e.getMessage());
        }
    }

    // Método para parsear XML usando expresiones regulares
    private static void parseXML() {
        try {
            File inputFile = new File("src/main/java/com/mycompany/createparsedisplay/example.xml");
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            StringBuilder xmlData = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                xmlData.append(line);
            }
            String xmlString = xmlData.toString();

            // Usar regex para extraer datos
            Pattern pattern = Pattern.compile("<name>(.*?)</name>.*?<age>(.*?)</age>.*?<city>(.*?)</city>", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(xmlString);

            String name = "", age = "", city = "";
            if (matcher.find()) {
                name = matcher.group(1);
                age = matcher.group(2);
                city = matcher.group(3);
            }

            // Crear el formulario para mostrar XML
            JFrame xmlFrame = new JFrame("XML Data");
            xmlFrame.setLocationRelativeTo(null);
            xmlFrame.setSize(400, 200);
            JTextArea textArea = new JTextArea();
            textArea.setText("Name: " + name + "\n" +
                             "Age: " + age + "\n" +
                             "City: " + city);
            xmlFrame.add(textArea);
            xmlFrame.setVisible(true);

            // También parsear con la librería
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

        } catch (HeadlessException | IOException | ParserConfigurationException | SAXException e) {
            System.err.println("\nERROR: "+e.getMessage());
        }
    }

    // Método para parsear MOODLE (similar a XML)
    private static void parseMoodle() {
        try {
            File inputFile = new File("src/main/java/com/mycompany/createparsedisplay/example.moodle");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            String question = doc.getElementsByTagName("text").item(0).getTextContent();

            // Crear el formulario para mostrar MOODLE
            JFrame moodleFrame = new JFrame("MOODLE Data");
            moodleFrame.setLocationRelativeTo(null);
            moodleFrame.setSize(400, 200);
            JTextArea textArea = new JTextArea();
            textArea.setText("Question: " + question);
            moodleFrame.add(textArea);
            moodleFrame.setVisible(true);

        } catch (HeadlessException | IOException | ParserConfigurationException | DOMException | SAXException e) {
            System.err.println("\nERROR: "+e.getMessage());
        }
    }

    // Método para parsear CSV usando regex
    private static void parseCSV() {
        try {
            File file = new File("src/main/java/com/mycompany/createparsedisplay/example.csv");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder csvData = new StringBuilder();
            Pattern pattern = Pattern.compile("(.*?),(.*?),(.*)");

            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    csvData.append("Name: ").append(matcher.group(1))
                           .append(", Age: ").append(matcher.group(2))
                           .append(", City: ").append(matcher.group(3)).append("\n");
                }
            }

            // Crear el formulario para mostrar CSV
            JFrame csvFrame = new JFrame("CSV Data");
            csvFrame.setLocationRelativeTo(null);
            csvFrame.setSize(400, 200);
            JTextArea textArea = new JTextArea(csvData.toString());
            csvFrame.add(textArea);
            csvFrame.setVisible(true);

        } catch (HeadlessException | IOException e) {
            System.err.println("\nERROR: "+e.getMessage());
        }
    }
}
