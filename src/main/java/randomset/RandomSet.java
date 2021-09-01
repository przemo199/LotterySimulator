package main.java.randomset;

import java.util.Arrays;
import java.util.Random;

public class RandomSet {
    int setSize;
    double[] seed;
    double sum;
    Random random = new Random();

    /**
     * Creates object required to generate set of random numbers
     *
     * @param setSize Integer representing amount of random numbers generated per set
     * @param seed    Array of doubles containing probability of occurrence for every number
     */
    public RandomSet(int setSize, double[] seed) {
        this.setSize = setSize;
        this.seed = seed;
        sum = 0.0;

        for (double i : seed) {
            sum = sum + i;
        }
    }

    /**
     * Generates set of random numbers
     *
     * @return Array of ints of size specified as betSize in RandomSet constructor
     */
    public int[] nextRandomSet() {
        double[] localSeed = seed.clone();
        double localSum = sum;
        int[] randomSet = new int[setSize];

        for (int i = 0; i < setSize; i++) {
            double randomDouble = random.nextDouble() * localSum;
            double threshold = 0.0;

            for (int j = 0; j < localSeed.length; j++) {
                threshold = threshold + localSeed[j];

                if (randomDouble < threshold || j == localSeed.length - 1) {
                    randomSet[i] = j + 1;
                    localSum -= localSeed[j];
                    localSeed[j] = 0.0;
                    break;
                }
            }
        }

        assert randomSet.length == setSize : "Created random set has incorrect size";

        Arrays.sort(randomSet);
        return randomSet;
    }
}
