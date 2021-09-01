package main.java.iocontroller;

import main.java.iocontroller.setio.SetWriter;

public class IOController {
    String filePath;
    int betSize;
    int poolSize;
    int betNumber;
    double[] seed;
    SetWriter writer;

    public void setFileName(String filePath) {
        this.filePath = filePath;
    }

    public void prepareWriter(String filePath, int betSize, int poolSize, int betNumber, double[] seed) {
        this.filePath = filePath;
        this.betSize = betSize;
        this.poolSize = poolSize;
        this.betNumber = betNumber;
        this.seed = seed;
        writer = new SetWriter(filePath);
        writer.writeHeader(betSize, poolSize, betNumber, seed);
    }

    public void prepareReader() {

    }

    public void writeNextSet(int[] set) {
        writer.writeSet(set);
    }

    public void closeWriter() {
        writer.closeWriter();
    }

}
