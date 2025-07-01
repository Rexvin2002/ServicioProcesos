package com.mycompany.createparsedisplay;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author kgv17
 */
public class CreateFiles {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        createJSONFile();
        createXMLFile();
        createMoodleFile();
        createCSVFile();
    }
    
    public static void createJSONFile() {
        String jsonContent = "{ \"name\": \"John\", \"age\": 30, \"city\": \"New York\" }";
        createFile("src/main/java/com/mycompany/createparsedisplay/example.json", jsonContent);
    }

    public static void createXMLFile() {
        String xmlContent = "<person><name>John</name><age>30</age><city>New York</city></person>";
        createFile("src/main/java/com/mycompany/createparsedisplay/example.xml", xmlContent);
    }

    public static void createMoodleFile() {
        String moodleContent = "<quiz><question type=\"multiplechoice\"><name><text>Question 1</text></name></question></quiz>";
        createFile("src/main/java/com/mycompany/createparsedisplay/example.moodle", moodleContent);
    }

    public static void createCSVFile() {
        String csvContent = "name,age,city\nJohn,30,New York\nJane,25,Boston";
        createFile("src/main/java/com/mycompany/createparsedisplay/example.csv", csvContent);
    }

    private static void createFile(String fileName, String content) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(content);
            System.out.println(fileName + " created successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while creating the file " + fileName);
        }
    }
}
