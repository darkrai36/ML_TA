package ru.vsu.cs.sibirko_i_s.ML_TA;

import ru.vsu.cs.sibirko_i_s.ML_TA.util.ArrayUtils;
import ru.vsu.cs.sibirko_i_s.ML_TA.util.JTableUtils;
import ru.vsu.cs.sibirko_i_s.ML_TA.util.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.io.File;

public class FrameMain extends JFrame {
    private JPanel panelMain;
    private JButton createRandomMatrixButton;
    private JButton loadMatrixFromFileButton;
    private JButton saveMatrixToFileButton;
    private JButton solveButton;
    private JButton saveResultIntoFileButton;
    private JTable tableForMatrix;
    private JTable tableForResult;
    private JLabel mainLabel;
    private JButton buttonClear;

    private final JFileChooser fileChooserOpen;
    private final JFileChooser fileChooserSave;

    public FrameMain () {
        this.setTitle("Matrix solver");
        this.setContentPane(panelMain);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();

        JTableUtils.initJTableForArray(tableForMatrix, 40, true, true, true, true);
        JTableUtils.initJTableForArray(tableForResult, 100, false, true, false, true);
        tableForResult.setEnabled(false);
        tableForMatrix.setRowHeight(25);
        tableForResult.setRowHeight(25);

        fileChooserOpen = new JFileChooser();
        fileChooserSave = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File(Program.defaultFileDirectory));
        fileChooserSave.setCurrentDirectory(new File(Program.defaultFileDirectory));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserSave.addChoosableFileFilter(filter);
        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserOpen.setAcceptAllFileFilterUsed(false);

        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");

        JTableUtils.writeArrayToJTable(tableForMatrix, new int[5][5]);

        this.pack();

        loadMatrixFromFileButton.addActionListener(actionEvent -> {
            try {
                if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    int[][] arr = ArrayUtils.readIntArray2FromFile(fileChooserOpen.getSelectedFile().getPath());
                    JTableUtils.writeArrayToJTable(tableForMatrix, arr);
                }
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });

        saveMatrixToFileButton.addActionListener(actionEvent -> {
            try {
                if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    int[][] arr = JTableUtils.readIntMatrixFromJTable(tableForMatrix);
                    String file = fileChooserSave.getSelectedFile().getPath();
                    ArrayUtils.writeArrayToFile(file, arr);
                }
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
        saveResultIntoFileButton.addActionListener(actionEvent -> {
            try {
                if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    double[] arr = JTableUtils.readDoubleArrayFromJTable(tableForResult);
                    String file = fileChooserSave.getSelectedFile().getPath();
                    ArrayUtils.writeArrayToFile(file, arr);
                }
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
        createRandomMatrixButton.addActionListener(actionEvent -> {
            try {
                int[][] arr = ArrayUtils.createRandomIntMatrix(5, 6, -10, 15);
                JTableUtils.writeArrayToJTable(tableForMatrix, arr);
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
        buttonClear.addActionListener(actionEvent -> {
            try {
                ((DefaultTableModel) tableForMatrix.getModel()).setRowCount(0);
                for (int r = 0; r < tableForMatrix.getRowCount(); r++) {
                    for (int c = 0; c < tableForMatrix.getColumnCount(); c++) {
                        tableForMatrix.setValueAt(null, r, c);
                    }
                }
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
        solveButton.addActionListener(actionEvent -> {
            try {
                int[][] arr = JTableUtils.readIntMatrixFromJTable(tableForMatrix);
                double[] result = MatrixSolver.matrixSolver(arr);
                JTableUtils.writeArrayToJTable(tableForResult, result);
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
    }
}