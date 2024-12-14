# WordTracker Program User Guide

## Introduction
WordTracker is a Java-based program that extracts words from a text file and tracks the file names and line numbers where each word appears. It allows you to analyze and generate reports about the frequency and location of words in a given text.

This program reads a text file, extracts the words, and records the file names and line numbers where each word appears. It generates reports in various formats based on the options provided:
- `-pf`: Outputs words and the file names where they appear.
- `-pl`: Outputs words, file names, and the line numbers where they appear.
- `-po`: Outputs words, file names, line numbers, and word frequencies.

The results can be displayed in the console or saved to an output file.

---

## Installation Instructions

1. **Install Java**:
   - To run WordTracker, you must have the Java Development Kit (JDK) installed.
   - We recommend JDK version 8 or higher. You can download the JDK from here: [JDK Download Link](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)

2. **Download WordTracker.jar**:
   - Download the WordTracker.jar file and save it to the directory of your choice.

3. **File Structure**:
   The following files should be placed in the same directory or specified paths:
   - `WordTracker.jar`
   - `input.txt` (the input text file you want to analyze)
   - Output file (optional, if you want to save the report).

---

## Usage Instructions

### 1. Running the Program

The program is executed using the following command:

```bash
java -jar WordTracker.jar <input.txt> <option> [-f <output.txt>]
