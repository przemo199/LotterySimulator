package iocontroller.setio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SetReader {
    BufferedReader bufferedReader;

    /**
     * Creates a new object required to read sets from a specified file
     *
     * @param fileName String representing name of file or path to a file
     */
    public SetReader(String fileName) {
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Reads next line from the file specified in SetReader
     *
     * @return String representing content of the next line from a file
     * @throws IOException
     */
    public String readSet() throws IOException {
        return bufferedReader.readLine();
    }

    /**
     * Closes BufferedReader
     */
    public void closeReader() {
        try {
            bufferedReader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
