package main.java.lotteryview;

import javax.swing.table.AbstractTableModel;

public class ProbabilityTableModel extends AbstractTableModel {
    private int poolSize;
    private String[] columnNames;
    private String[][] data;

    public ProbabilityTableModel(int poolSize) {
        this.poolSize = poolSize;

        columnNames = new String[poolSize + 1];
        columnNames[0] = "";
        for (int i = 1; i < this.poolSize + 1; i++) {
            columnNames[i] = Integer.toString(i);
        }

        data = new String[2][this.poolSize + 1];
        data[0][0] = "Probability:";
        data[1][0] = "Modifier:";
        for (int i = 1; i < this.poolSize + 1; i++) {
            data[1][i] = "1.0";
        }

        for (int i = 1; i < this.poolSize + 1; i++) {
            data[0][i] = String.format("%.4f", (1.0 / poolSize) * 1000) + "‰";
        }
    }

    public void updateProbability(double[] seed) {
        double sum = 0;

        for (double i : seed) {
            sum += i;
        }

        for (int i = 0; i < seed.length; i++) {
            data[0][i + 1] = String.format("%.4f", seed[i]/sum * 1000) + "‰";
        }
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return 2;
    }

    @Override
    public int getColumnCount() {
        return poolSize + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex > 0 && rowIndex == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = value.toString();
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
