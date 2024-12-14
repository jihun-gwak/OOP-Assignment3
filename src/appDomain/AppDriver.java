package appDomain;

import java.io.*;
import java.util.*;

import implementations.*;


public class WordTracker {
    private static final String REPOSITORY_FILE = "repository.ser";
    private BSTree<String> wordTree; 
    private Map<String, Map<String, List<Integer>>> metadata; 

    public WordTracker() {
        wordTree = loadRepositoryTree();
        metadata = loadRepositoryMetadata();
    }

    @SuppressWarnings("unchecked")
    private BSTree<String> loadRepositoryTree() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(REPOSITORY_FILE))) {
            return (BSTree<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No existing repository found. Creating a new tree...");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading repository tree: " + e.getMessage());
        }
        return new BSTree<>();
    }

 
    @SuppressWarnings("unchecked")
    private Map<String, Map<String, List<Integer>>> loadRepositoryMetadata() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(REPOSITORY_FILE + ".meta"))) {
            return (Map<String, Map<String, List<Integer>>>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No existing metadata found. Creating a new map...");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading repository metadata: " + e.getMessage());
        }
        return new HashMap<>();
    }

    private void saveRepository() {
        try (ObjectOutputStream oosTree = new ObjectOutputStream(new FileOutputStream(REPOSITORY_FILE));
             ObjectOutputStream oosMeta = new ObjectOutputStream(new FileOutputStream(REPOSITORY_FILE + ".meta"))) {
            oosTree.writeObject(wordTree);
            oosMeta.writeObject(metadata);
        } catch (IOException e) {
            System.err.println("Error saving repository: " + e.getMessage());
        }
    }


    public void processFile(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            int lineNumber = 0;

            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine();

                for (String word : line.split("\\W+")) { 
                    if (!word.isEmpty()) {
                        word = word.toLowerCase(); 
                        if (!wordTree.contains(word)) {
                            wordTree.add(word); 
                            metadata.put(word, new HashMap<>()); 
                        }
                        metadata.putIfAbsent(word, new HashMap<>());
                        metadata.get(word).putIfAbsent(fileName, new ArrayList<>());
                        metadata.get(word).get(fileName).add(lineNumber);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
        }
    }

    public void generateReport(String option, String outputFile) {
        try (PrintWriter writer = (outputFile != null) ? new PrintWriter(new FileOutputStream(outputFile)) : new PrintWriter(System.out)) {
            switch (option) {
                case "-pf":
                    generateReportFileOnly(writer);
                    break;
                case "-pl":
                    generateReportFileAndLine(writer);
                    break;
                case "-po":
                    generateReportFileLineAndFrequency(writer);
                    break;
                default:
                    System.err.println("Invalid option. Use -pf, -pl, or -po.");
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error opening output file: " + outputFile);
        }
    }

    private void generateReportFileOnly(PrintWriter writer) {
        utilities.Iterator<String> iterator = wordTree.inorderIterator();
        while (iterator.hasNext()) {
            String word = iterator.next();
            writer.println(word + ": " + metadata.get(word).keySet());
        }
    }

    private void generateReportFileAndLine(PrintWriter writer) {
        utilities.Iterator<String> iterator = wordTree.inorderIterator();
        while (iterator.hasNext()) {
            String word = iterator.next();
            writer.print(word + ": ");
            for (Map.Entry<String, List<Integer>> entry : metadata.get(word).entrySet()) {
                writer.print(entry.getKey() + " (lines " + entry.getValue() + "), ");
            }
            writer.println();
        }
    }

    private void generateReportFileLineAndFrequency(PrintWriter writer) {
        utilities.Iterator<String> iterator = wordTree.inorderIterator();
        while (iterator.hasNext()) {
            String word = iterator.next();
            writer.print(word + ": ");
            for (Map.Entry<String, List<Integer>> entry : metadata.get(word).entrySet()) {
                writer.print(entry.getKey() + " (" + entry.getValue().size() + " occurrences at lines " + entry.getValue() + "), ");
            }
            writer.println();
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java WordTracker <input.txt> -pf/-pl/-po [-f <output.txt>]");
            return;
        }

        WordTracker tracker = new WordTracker();
        tracker.processFile(args[0]);
        tracker.saveRepository();   

        String outputFile = (args.length == 4 && args[2].equals("-f")) ? args[3] : null;
        tracker.generateReport(args[1], outputFile); 
    }
}
