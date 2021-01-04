package lotteryview;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class LotteryView extends JFrame {
    final private JLabel filePathLabel = new JLabel("Select file path:");
    final private JTextField filePathTextField = new JTextField(20);
    final private JButton filePathButton = new JButton("...");

    final private JLabel setSizeLabel = new JLabel("Bet size:");
    final private JTextField setSizeTextField = new JTextField("6", 3);

    final private JLabel poolSizeLabel = new JLabel("Pool size:");
    final private JTextField poolSizeTextField = new JTextField("49", 3);

    final private JLabel setNumberLabel = new JLabel("Number of bets:");
    final private JTextField setNumberTextField = new JTextField("10000000", 6);

    final private JProgressBar progressBar = new JProgressBar();
    final private JButton buttonGenerate = new JButton("Generate");

    private ProbabilityTableModel tableModel = new ProbabilityTableModel(49);
    private JTable table = new JTable(tableModel);

    JTabbedPane tabbedPane = new JTabbedPane();

    public LotteryView() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //this.setBounds(200, 100, 600, 400);
        this.setTitle("Lottery Simulator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon img = new ImageIcon("icon.png");
        this.setIconImage(img.getImage());

        JPanel basePane = new JPanel();

        basePane.setLayout(new GridBagLayout());
        Insets insets = new Insets(5, 5, 5, 5);
        GridBagConstraints gbc = newGridBagConstraints(insets);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        basePane.add(filePathLabel, gbc);
        gbc = newGridBagConstraints(insets);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        gbc.weighty = 0.0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        basePane.add(filePathTextField, gbc);
        gbc = newGridBagConstraints(insets);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        basePane.add(filePathButton, gbc);
        gbc = newGridBagConstraints(insets);

        //tabbedPane.setPreferredSize(new Dimension(400, 400));

        JPanel simulatorPane = new JPanel();
        simulatorPane.setLayout(new GridBagLayout());
        String betSizeToolTipText = "Must be integer greater than 0 and smaller than pool size";

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        setSizeLabel.setToolTipText(betSizeToolTipText);
        simulatorPane.add(setSizeLabel, gbc);
        gbc = newGridBagConstraints(insets);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        setSizeTextField.setToolTipText(betSizeToolTipText);
        simulatorPane.add(setSizeTextField, gbc);
        gbc = newGridBagConstraints(insets);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        simulatorPane.add(poolSizeLabel, gbc);
        gbc = newGridBagConstraints(insets);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        simulatorPane.add(poolSizeTextField, gbc);
        gbc = newGridBagConstraints(insets);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        simulatorPane.add(setNumberLabel, gbc);
        gbc = newGridBagConstraints(insets);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        simulatorPane.add(setNumberTextField, gbc);
        gbc = newGridBagConstraints(insets);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        simulatorPane.add(buttonGenerate, gbc);
        gbc = newGridBagConstraints(insets);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        progressBar.setMaximum(1000);
        simulatorPane.add(progressBar, gbc);
        gbc = newGridBagConstraints(insets);

        tabbedPane.addTab("Generate", simulatorPane);

        JPanel customisePane = new JPanel();
        customisePane.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        customisePane.add(scrollPane, gbc);
        gbc = newGridBagConstraints(insets);
        tabbedPane.addTab("Customise", customisePane);

        JPanel detailPane = new JPanel();
        tabbedPane.addTab("Check", detailPane);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        basePane.add(tabbedPane, gbc);

        this.setContentPane(basePane);
        SwingUtilities.updateComponentTreeUI(this);
        this.pack();
    }

    private static GridBagConstraints newGridBagConstraints(Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = insets;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        return gbc;
    }

    public String getBetSize() {
        return setSizeTextField.getText();
    }

    public String getPoolSize() {
        return poolSizeTextField.getText();
    }

    public String getBetNumber() {
        return setNumberTextField.getText();
    }

    public String getFilePath() {
        return filePathTextField.getText();
    }

    public int getColumnCount() {
        return table.getColumnCount();
    }

    public int getSelectedPane() {
        return tabbedPane.getSelectedIndex();
    }

    public double[] getCustomSeed() {
        double[] seed = new double[table.getColumnCount() - 1];
        for (int i = 1; i < table.getColumnCount(); i++) {
            seed[i - 1] = Double.parseDouble(table.getValueAt(1, i).toString());
        }
        return seed;
    }

    public void setProgressBarValue(int value) {
        progressBar.setValue(value);
    }

    public void setProgressBarString(String value) {
        progressBar.setString(value);
    }

    public void setProgressBarPainted(boolean value) {
        progressBar.setStringPainted(value);
    }

    public void setButtonGenerateEnabled(Boolean value) {
        buttonGenerate.setEnabled(value);
    }

    public void setTableValueAt(String value, int rowIndex, int columnIndex) {
        tableModel.setValueAt(value, rowIndex, columnIndex);
    }

    public void setNewTableModel(int poolSize) {
        tableModel = new ProbabilityTableModel(poolSize);
        table.setModel(tableModel);
    }

    public void updateProbabilityTable(double[] probability) {
        tableModel.updateProbability(probability);
    }

    public void showErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }

    public void addListenerToButtonGenerate(ActionListener listener) {
        buttonGenerate.addActionListener(listener);
    }

    public void addListenerToTabbedPane(ChangeListener listener) {
        tabbedPane.addChangeListener(listener);
    }

    public void addListenerToTable(TableModelListener listener) {
        tableModel.addTableModelListener(listener);
    }

    private static String[] generateColumnNames(int poolSize) {
        String[] columnNames = new String[poolSize + 1];
        columnNames[0] = "";
        for (int i = 1; i < columnNames.length; i++) {
            columnNames[i] = Integer.toString(i);
        }
        return columnNames;
    }
}
