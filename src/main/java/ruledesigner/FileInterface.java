package ruledesigner;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileInterface {
//    public static void main(String[] args) {
//        // attempt to open the conformation file
//        String file_name = "test.txt";
//        try {
//            File file = new File(file_name);
//            FileReader fileReader = new FileReader(file);
//        } catch(FileNotFoundException e) {
//            System.out.println(e);
//        }
//
//        // define axis and the fundamental coordinates on the triangular grid plane
//        String[] direction = {"NE", "E", "SE", "SW", "W", "NW"};
//        Point[] directionCord = {new Point(0, 1), new Point(1, 0), new Point(1, -1),
//                new Point(0, -1), new Point(-1, 0), new Point(-1, 1)};
//        Map<String, Point> directionMap = new HashMap<>();
//        for(int i = 0; i<6; i++) {
//            directionMap.put(direction[i], directionCord[i]);
//        }
//    }

    public readJsonIntoOritatami(String filePath) {
        File file = new File(filePath);
        ObjectMapper mapper = new ObjectMapper();
        OritatamiFromJson oritatamiFromJson;

        try {
            oritatamiFromJson = mapper.readValue(file, OritatamiJson.class);
            System.out.println("Name: " + oritatamiFromJson.name);
            System.out.println("Delay: " + oritatamiFromJson.delay);
            System.out.println("Period Count: " + oritatamiFromJson.periodCount);
            System.out.println("Category Colors: " + oritatamiFromJson.categoryColors);
            System.out.println("Seed Conformation: " + oritatamiFromJson.seedConformation);
            System.out.println("Rule: " + oritatamiFromJson.rule);
            System.out.println("Transcript Period: " + oritatamiFromJson.transcriptPeriod);
        } catch (IOException e) {
            e.printStackTrace();
            oritatamiFromJson = null;
        }
    }

    // Construct the transcript from the transcript file
    public Transcript readTranscript(String fileName) {
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
        } catch(FileNotFoundException e) {
            System.out.println(e);
        }
        Transcript transcript = new Transcript();

        // Transcript constructiong process

        return transcript;
    }

    // Construct the target conformation from the conformation file
    public Conformation readTargetConformation(String fileName) {
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
        } catch(FileNotFoundException e) {
            System.out.println(e);
        }
        Conformation conformation = new Conformation();
        return conformation;
    }
}