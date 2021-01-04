package iocontroller.setio;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class SetWriter {
    String newLineSign = System.getProperty("line.separator");
    BufferedWriter bufferedWriter;

    /**
     * Creates a new object required to write sets to a specified file
     *
     * @param fileName String representing name of file or path to a file
     */
    public SetWriter(String fileName) {
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(
                    new File(fileName)), StandardCharsets.UTF_8);
            bufferedWriter = new BufferedWriter(writer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Writes to a file the arguments used to generate the random sets
     *
     * @param betSize   Integer representing amount of random numbers per set
     * @param poolSize  Integer representing range from which random numbers in a set were generated
     * @param betNumber Integer representing amount of generated random sets
     * @param seed      Array of doubles containing probability of occurrence for every number
     */
    public void writeHeader(int betSize, int poolSize, int betNumber, double[] seed) {
        try {
            bufferedWriter.write("Bet size: " + betSize + ", Pool size: " + poolSize + ", Number of bets: " + betNumber + newLineSign);
            bufferedWriter.write("Seed: " + Arrays.toString(seed) + newLineSign);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Writes set of numbers to a file and adds new line at the end
     *
     * @param set Array of ints representing one set
     */
    public void writeSet(int[] set) {
        String toWrite = Arrays.toString(set);

        try {
            bufferedWriter.write(toWrite.substring(1, toWrite.length() - 1) + newLineSign);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Closes BufferedWriter
     */
    public void closeWriter() {
        try {
            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
