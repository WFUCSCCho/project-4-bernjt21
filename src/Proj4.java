import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Proj4 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        // FINISH ME
        ArrayList<DogData> dogArrayList = new ArrayList<>();
        Scanner scnr = new Scanner(new File("src/dogbreeds.csv"));
        Scanner inputscnr = new Scanner(inputFileNameStream);
        //remove header in file
        if (scnr.hasNext()) {
            scnr.nextLine();
        }
        while (scnr.hasNext() && dogArrayList.size()<numLines) {
            String line = scnr.nextLine();
            String[] parts = line.split(","); //split the string into multiple parts
            //Check if the dogs match
            //if (parts[0].equals("Breed")) {
            //Create new DogData object
            DogData data = new DogData(
                    parts[0], //breed
                    parts[1], //originCountry
                    parts[2], //furColor
                    parts[3], //height
                    parts[4], //eyeColor
                    parts[5]  //longevity
            );
            dogArrayList.add(data);//add the data onto the ArrayList
            //}
        }
        //creating output files for the times and counts
        File file = new File("./analysis.csv");
        writeToFile("\nCommand, Number of Lines, Runtime", "./analysis.csv");
        long runTime;
        //performs collections sort on arrayList
        Collections.sort(dogArrayList);

        runTime = runHash(dogArrayList, "insert");
        writeToFile("insert sorted, " + numLines + " lines, " + runTime + " ns", "./analysis.csv");
        System.out.println("Inserting " + numLines + " lines into sorted Hash Table took:  " + runTime + " ns");

        runTime = runHash(dogArrayList, "search");
        writeToFile("search sorted, " + numLines + " lines, " + runTime + " ns", "./analysis.csv");
        System.out.println("Searching " + numLines + " lines into sorted Hash Table took:  " + runTime + " ns");

        runTime = runHash(dogArrayList, "delete");
        writeToFile("delete sorted, " + numLines + " lines, " + runTime + " ns", "./analysis.csv");
        System.out.println("Deleting " + numLines + " lines into sorted Hash Table took:  " + runTime + " ns");


        //performs collections shuffle on arrayList
        Collections.shuffle(dogArrayList);

        runTime = runHash(dogArrayList, "insert");
        writeToFile("insert shuffled, " + numLines + " lines, " + runTime + " ns", "./analysis.csv");
        System.out.println("Inserting " + numLines + " lines into shuffled Hash Table took:  " + runTime + " ns");

        runTime = runHash(dogArrayList, "search");
        writeToFile("search shuffled, " + numLines + " lines, " + runTime + " ns", "./analysis.csv");
        System.out.println("Searching " + numLines + " lines into shuffled Hash Table took:  " + runTime + " ns");

        runTime = runHash(dogArrayList, "delete");
        writeToFile("delete shuffled, " + numLines + " lines, " + runTime + " ns", "./analysis.csv");
        System.out.println("Deleting " + numLines + " lines into shuffled Hash Table took:  " + runTime + " ns");

        //performs collections sort of reverse order on arrayList
        Collections.sort(dogArrayList, Collections.reverseOrder());
        runTime = runHash(dogArrayList, "insert");
        writeToFile("insert reversed, " + numLines + " lines, " + runTime + " ns", "./analysis.csv");
        System.out.println("Inserting " + numLines + " lines into reversed Hash Table took:  " + runTime + " ns");

        runTime = runHash(dogArrayList, "search");
        writeToFile("search reversed, " + numLines + " lines, " + runTime + " ns", "./analysis.csv");
        System.out.println("Searching " + numLines + " lines into reversed Hash Table took:  " + runTime + " ns");

        runTime = runHash(dogArrayList, "delete");
        writeToFile("delete reversed, " + numLines + " lines, " + runTime + " ns", "./analysis.csv");
        System.out.println("Deleting " + numLines + " lines into reversed Hash Table took:  " + runTime + " ns");
    }
    private static long runHash(ArrayList<DogData> dogArrayList, String hashMethod){
        long startTime = System.nanoTime();
        long endTime = System.nanoTime();
        //implements Hash Table for sorted
        SeparateChainingHashTable<DogData> hash = new SeparateChainingHashTable<>();

        //takes the time and adds the arraylist to the hash
        startTime = System.nanoTime();
        for (DogData data : dogArrayList) {
            switch (hashMethod){
                case "insert":
                    hash.insert(data);
                    break;
                case "search":
                    hash.contains(data);
                    break;
                case "delete":
                    hash.remove(data);
                    break;
            }
        }
        endTime = System.nanoTime();
        return endTime - startTime;
    }

    public static void writeToFile(String content, String filePath) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true));
            boolean emptyFile = new File(filePath).length() == 0;
            if (!emptyFile) {
                bufferedWriter.newLine();
            }
            bufferedWriter.write(content);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
