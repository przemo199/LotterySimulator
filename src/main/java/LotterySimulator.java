package main.java;

import main.java.iocontroller.IOController;
import main.java.lotterycontroller.LotteryController;
import main.java.lotteryview.LotteryView;

public class LotterySimulator {

    public static void main(String[] args) {
        LotteryView view = new LotteryView();
        new LotteryController(view, new IOController());
        view.setVisible(true);
    }
}
