package lotterycontroller;

import iocontroller.IOController;
import lotteryview.LotteryView;
import randomset.RandomSet;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class LotteryController {
    private final LotteryView view;
    private final IOController controller;

    public LotteryController(LotteryView view, IOController controller) {
        this.view = view;
        this.controller = controller;
        view.addListenerToButtonGenerate(new ButtonGenerateListener());
        view.addListenerToTabbedPane(new TabbedPaneListener());
        view.addListenerToTable(new TableListener());
    }

    class LotteryWorker extends SwingWorker<Long, Integer> {
        String filePath;
        int setSize;
        int poolSize;
        int betNumber;
        double[] seed;

        public LotteryWorker(String filePath, int setSize, int poolSize, int betNumber, double[] seed) {
            this.filePath = filePath;
            this.setSize = setSize;
            this.poolSize = poolSize;
            this.betNumber = betNumber;
            this.seed = seed;
        }

        @Override
        protected Long doInBackground() {
            view.setButtonGenerateEnabled(false);
            long startTime = System.currentTimeMillis();
            RandomSet randomSet = new RandomSet(setSize, seed);
            controller.prepareWriter(filePath, setSize, poolSize, betNumber, seed);
            view.setProgressBarPainted(true);

            int previousProgress = 0;
            for (int i = 0; i < betNumber; i++) {
                controller.writeNextSet(randomSet.nextRandomSet());
                int progress = (int) (((double) (i + 1) / betNumber) * 1000);
                if (progress > previousProgress) {
                    previousProgress = progress;
                    publish(progress);
                }
            }

            return startTime;
        }

        @Override
        protected void process(List<Integer> chunks) {
            int progress = chunks.get(chunks.size() - 1);
            view.setProgressBarValue(progress);
            view.setProgressBarString(progress / 10.0 + "%");
        }

        @Override
        protected void done() {
            try {
                long startTime = get();
                controller.closeWriter();
                long endTime = System.currentTimeMillis();
                view.setProgressBarString("Total time: " + (endTime - startTime) / 1000.0 + "s");
                view.setProgressBarValue(1000);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            view.setButtonGenerateEnabled(true);
        }
    }

    class ButtonGenerateListener implements ActionListener {
        String filePath;
        int setSize;
        int poolSize;
        int setNumber;
        double[] seed = new double[poolSize];

        @Override
        public void actionPerformed(ActionEvent e) {
            Pattern pattern = Pattern.compile("\\d+");
            boolean isBetNum = pattern.matcher(view.getBetSize()).matches();
            boolean isPoolNum = pattern.matcher(view.getPoolSize()).matches();
            boolean isNumNum = pattern.matcher(view.getBetSize()).matches();

            if (isBetNum && isPoolNum && isNumNum) {
                filePath = view.getFilePath();
                setSize = Integer.parseInt(view.getBetSize());
                poolSize = Integer.parseInt(view.getPoolSize());
                setNumber = Integer.parseInt(view.getBetNumber());
                Arrays.fill(seed, 1.0);
            }

            if (!(filePath.equals("")) && setSize > 0 && setSize < poolSize && setNumber > 0) {
                new LotteryWorker(filePath, setSize, poolSize, setNumber, seed).execute();
            } else {
                view.showErrorMessage("Incorrect arguments");
            }
        }
    }

    class TabbedPaneListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            try {
                if (view.getSelectedPane() == 1 && Integer.parseInt(view.getPoolSize()) != view.getColumnCount() - 1) {
                    view.setNewTableModel(Integer.parseInt(view.getPoolSize()));
                    view.addListenerToTable(new TableListener());
                }
            } catch (Exception ex) {
                view.showErrorMessage("Entered pool size is incorrect");
            }
        }
    }

    class TableListener implements TableModelListener {
        @Override
        public void tableChanged(TableModelEvent e) {
            if (e.getFirstRow() == 1) {
                double[] seed = view.getCustomSeed();
                view.updateProbabilityTable(seed);
            }
        }
    }
}
