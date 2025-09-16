package ruledesigner;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

    /**
     * Reads a JSON file and constructs an OritatamiFromJson object.
     * @param filePath the path to the JSON file
     * @return an OritatamiFromJson object containing the data from the JSON file
     */
    public OritatamiFromJson readJson(String filePath) {
        File file = new File(filePath);
        ObjectMapper mapper = new ObjectMapper();
        OritatamiFromJson oritatamiFromJson;

        try {
            oritatamiFromJson = mapper.readValue(file, OritatamiFromJson.class);
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

        return oritatamiFromJson;
    }

    // Construct the transcript from the transcript file
    public Transcript readTranscript(String fileName) {
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        Transcript transcript = new Transcript();

        // Transcript construction process
        // 1. Print compactTranscriptPrefix, if exists
        // 2. Print compactTranscriptPeriod for defined times by periodCount
        // 2.1. Look for 'declare' fields and store them as arrays of beads
        // 2.2. 

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