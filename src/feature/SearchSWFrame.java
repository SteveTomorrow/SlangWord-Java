package feature;

import read.SlangWord;
import shows.MenuSWFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class SearchSWFrame extends JFrame implements ActionListener, TableModelListener, ListSelectionListener {
    JButton btnBack, btnFind;
    JTextField textField;
    JTable tableshow;
    JLabel titleLabel1;
    DefaultTableModel model;
    SlangWord slangword;
    String[][] result;
    final JOptionPane optionPane = new JOptionPane("The only way to close this dialog is by\n"
            + "pressing one of the following buttons.\n" + "Do you understand?", JOptionPane.QUESTION_MESSAGE,
            JOptionPane.YES_NO_OPTION);
    String data[][] = { { "", "", "" } };

    public SearchSWFrame() throws Exception {
        Container con = this.getContentPane();
        slangword = SlangWord.getInstance();
        // Title Label
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Find Slang Words ");
        titleLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
        titlePanel.add(titleLabel);
        titlePanel.setBackground(Color.blue);
        titleLabel.setForeground(Color.white);
        titlePanel.setMaximumSize(new Dimension(700, 300));

        // Result Label
        titleLabel1 = new JLabel();
        titleLabel1.setText("Enter slang word to find out Definition ");
        titleLabel1.setForeground(Color.black);
        titleLabel1.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
        titleLabel1.setAlignmentX(CENTER_ALIGNMENT);

        // Form
        JPanel form = new JPanel();
        // form.setBackground(Color.black);
        JLabel formLabel = new JLabel("Input Slang Word");
        textField = new JTextField();
        btnFind = new JButton("Find");
        btnFind.addActionListener(this);
        btnFind.setMnemonic(KeyEvent.VK_ENTER);
        btnFind.setBackground(Color.cyan);
        // SpringLayout layout = new SpringLayout();
        form.setLayout(new BorderLayout(10, 10));

        form.add(formLabel, BorderLayout.LINE_START);
        form.add(textField, BorderLayout.CENTER);
        form.add(btnFind, BorderLayout.LINE_END);
        Dimension size = new Dimension(700, 50);
        form.setMaximumSize(size);
        form.setPreferredSize(size);
        form.setMinimumSize(size);
        // Table result
        JPanel panelTable = new JPanel();
        panelTable.setBackground(Color.black);

        String column[] = { "STT", "Slang Word", "Definition" };

        tableshow = new JTable(new DefaultTableModel(column, 0));
        tableshow.setRowHeight(30);
        model = (DefaultTableModel) tableshow.getModel();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableshow.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableshow.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableshow.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableshow.getColumnModel().getColumn(0).setMaxWidth(50); // Adjust the width of the "STT" column
        tableshow.getColumnModel().getColumn(1).setPreferredWidth(200); // Adjust the width of the "Slang Word" column
        tableshow.getColumnModel().getColumn(2).setPreferredWidth(500); // Adjust the width of the "Definition" column
        tableshow.getModel().addTableModelListener(this);
//        ListSelectionModel selectionModel = tableshow.getSelectionModel();
//
//        selectionModel.addListSelectionListener(this);

        JScrollPane sp = new JScrollPane(tableshow);

        panelTable.setLayout(new GridLayout(1, 1));
        panelTable.add(sp);
        Dimension size2 = new Dimension(650, 530);
        panelTable.setMaximumSize(size2);
        panelTable.setPreferredSize(size2);
        panelTable.setMinimumSize(size2);
        // Button Back
        JPanel bottonPanel = new JPanel();
        btnBack = new JButton("Back ");
        // btnBack.addActionListener(this);
        btnBack.setFocusable(false);
        bottonPanel.add(btnBack);
        btnBack.addActionListener(this);
        btnBack.setAlignmentX(CENTER_ALIGNMENT);

        // Setting Content
        con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
        con.add(Box.createRigidArea(new Dimension(0, 10)));
        con.add(titlePanel);
        con.add(Box.createRigidArea(new Dimension(0, 20)));
        con.add(titleLabel1);
        con.add(Box.createRigidArea(new Dimension(0, 10)));
        con.add(form);
        con.add(Box.createRigidArea(new Dimension(0, 10)));
        con.add(panelTable);
        con.add(Box.createRigidArea(new Dimension(0, 10)));
        con.add(btnBack);
        con.add(Box.createRigidArea(new Dimension(0, 20)));
        // Setting JFrame
        this.setTitle("Find Slang Words");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(800, 850);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == btnFind) {
            String key = textField.getText();
            // System.out.println("a" + key + "a");
            if (key.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please input slang word you want to find", "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            Object[] options = { "Find Flow Slang Word", "Find Slang Flow definition" };
            int n = JOptionPane.showOptionDialog(this, "Choose mode " + "you want to excute?", "Choose mode find",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
            String[][] temp = null;
            if (n == 0) {
                this.clearTable();
                long startTime = System.currentTimeMillis();
                temp = slangword.getDefinition(key);
                long endTime = System.currentTimeMillis();
                long timeElapsed = endTime - startTime;
                if (temp != null)
                    titleLabel1.setText("Execution time in milliseconds: " + temp.length + " Slang Words/ "
                            + String.valueOf(timeElapsed) + " ms");
                else {
                    titleLabel1.setText("Can't not find that Slang Word");
                    return;
                }
                result = temp;
                for (int i = 0; i < result.length; i++) {
                    String ss[] = result[i];
                    model.addRow(ss);
                }

            } else if (n == 1) {
                this.clearTable();
                long startTime = System.currentTimeMillis();
                temp = slangword.findDefinition(key);
                long endTime = System.currentTimeMillis();
                long timeElapsed = endTime - startTime;
                if (temp != null)
                    titleLabel1.setText("Execution time in millisecond: " + temp.length + " Slang Words/ "
                            + String.valueOf(timeElapsed) + " ms");
                else {
                    titleLabel1.setText("Can't not find that Slang Word");
                    return;
                }
                result = temp;
                for (int i = 0; i < result.length; i++) {
                    String ss[] = result[i];
                    model.addRow(ss);
                }
            }
            try {
                for (int ii = 0; ii < temp.length; ii++)
                    slangword.saveHistory(temp[ii][1], temp[ii][2]);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else if (e.getSource() == btnBack) {
            this.dispose();
            new MenuSWFrame();
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = tableshow.getSelectedRow();
        int col = tableshow.getSelectedColumn();
        if (row == col && row == -1)
            return;
        String Data = (String) tableshow.getValueAt(row, col);
        System.out.println("Table element selected is: " + row + col + " : " + Data);
        if (col == 1 || col == 2) {
            String oldSlang = result[row][1];  // Lấy giá trị Slang cũ từ biến result
            String newSlang = (String) tableshow.getValueAt(row, 1); // Lấy giá trị Slang mới từ bảng
            String oldValue = result[row][2];  // Lấy giá trị Definition cũ từ biến result
            String newValue = (String) tableshow.getValueAt(row, 2); // Lấy giá trị Definition mới từ bảng
            result[row][1] = newSlang;
            result[row][2] = newValue;

            slangword.set(oldSlang, newSlang, oldValue, newValue);
            JOptionPane.showMessageDialog(this, "Updated a row.");
        }
        tableshow.setFocusable(false);
        // TODO Auto-generated method stub
    }
    public void valueChanged(ListSelectionEvent e) {
        // TODO Auto-generated method stub
//        int row = tableshow.getSelectedRow();
//        int col = tableshow.getSelectedColumn();
//        if (row == -1 || col == -1)
//            return;
//        String Data = (String) tableshow.getValueAt(row, 1);
//
//        System.out.println("Table element selected is: " + Data);
//        int n = JOptionPane.showConfirmDialog(this, "Would you like to delete this slang word?", "An Inane Question",
//                JOptionPane.YES_NO_OPTION);
//        if (n == 0) {
//            slangword.delete(Data, (String) tableshow.getValueAt(row, 2));
//            // default title and icon
//            model.removeRow(row);
//            JOptionPane.showMessageDialog(this, "Deleted success");
//
//        }
    }

    void clearTable() {
        int rowCount = model.getRowCount();
        // Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }
}