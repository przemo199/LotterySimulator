import iocontroller.IOController;
import lotterycontroller.LotteryController;
import lotteryview.LotteryView;

public class LotterySimulator {

    public static void main(String[] args) {
        LotteryView view = new LotteryView();
        new LotteryController(view, new IOController());
        view.setVisible(true);
    }
}
