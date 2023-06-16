package feature;

import read.SlangWord;
import shows.MenuSWFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class EditSWFrame extends JFrame implements ActionListener, TableModelListener, ListSelectionListener {
    JButton btnBack, btnFind, btnClear;
    JTextField textField;
    JTable tableShow;
    JLabel titleLabel1;
    DefaultTableModel model;
    SlangWord slangWord;
    String[][] result;
    final JOptionPane optionPane = new JOptionPane("The only way to close this dialog is by\n"
            + "pressing one of the following buttons.\n" + "Do you understand?", JOptionPane.QUESTION_MESSAGE,
            JOptionPane.YES_NO_OPTION);
    String data[][] = { { "", "", "" } };
    String datatemp[][];

    public EditSWFrame() throws Exception {
        Container container = getContentPane();
        slangWord = SlangWord.getInstance();

        // Title Label
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Choose Slang Word you want to Edit ");
        titleLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
        titlePanel.add(titleLabel);
        titlePanel.setBackground(Color.BLUE);
        titleLabel.setForeground(Color.WHITE);
        titlePanel.setMaximumSize(new Dimension(700, 300));

        // Result Label
        titleLabel1 = new JLabel();
        titleLabel1.setText("Enter Slang Word you want to Edit ");
        titleLabel1.setForeground(Color.BLACK);
        titleLabel1.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
        titleLabel1.setAlignmentX(CENTER_ALIGNMENT);

        // Form
        JPanel formPanel = new JPanel();
        JLabel formLabel = new JLabel("Input Slang Word");
        textField = new JTextField();

        btnFind = new JButton("Find");
        btnFind.addActionListener(this);
        btnFind.setMnemonic(KeyEvent.VK_ENTER);
        btnFind.setBackground(Color.CYAN);

        formPanel.setLayout(new BorderLayout(10, 10));
        formPanel.add(formLabel, BorderLayout.LINE_START);
        formPanel.add(textField, BorderLayout.CENTER);
        formPanel.add(btnFind, BorderLayout.LINE_END);
        Dimension formSize = new Dimension(700, 50);
        formPanel.setMaximumSize(formSize);
        formPanel.setPreferredSize(formSize);
        formPanel.setMinimumSize(formSize);

        // Table result
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.BLACK);
        datatemp = slangWord.getData();

        String column[] = { "STT", "Slang Word", "Definition" };

        tableShow = new JTable(new DefaultTableModel(column, 0));
        tableShow.setRowHeight(30);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableShow.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableShow.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableShow.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableShow.getColumnModel().getColumn(0).setMaxWidth(50); // Adjust the width of the "STT" column
        tableShow.getColumnModel().getColumn(1).setPreferredWidth(200); // Adjust the width of the "Slang Word" column
        tableShow.getColumnModel().getColumn(2).setPreferredWidth(500); // Adjust the width of the "Definition" column
        tableShow.getModel().addTableModelListener(this);
        model = (DefaultTableModel) tableShow.getModel(); // Initialize the model variable

        JScrollPane scrollPane = new JScrollPane(tableShow);

        tablePanel.setLayout(new GridLayout(1, 1));
        tablePanel.add(scrollPane);
        Dimension tableSize = new Dimension(650, 530);
        tablePanel.setMaximumSize(tableSize);
        tablePanel.setPreferredSize(tableSize);
        tablePanel.setMinimumSize(tableSize);

        // Button Back
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));
        Dimension buttonSize = new Dimension(200, 50);
        buttonPanel.setMaximumSize(buttonSize);
        buttonPanel.setPreferredSize(buttonSize);
        buttonPanel.setMinimumSize(buttonSize);
        btnClear = new JButton("Clear");
        btnBack = new JButton("Back");
        btnBack.setFocusable(false);
        btnClear.setFocusable(false);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnBack);
        btnBack.addActionListener(this);
        btnClear.addActionListener(this);

        // Setting Content
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(titlePanel);
        container.add(Box.createRigidArea(new Dimension(0, 20)));
        container.add(titleLabel1);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(formPanel);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(tablePanel);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(buttonPanel);
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        // Setting JFrame
        setTitle("Edit Slang Words");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(800, 850);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
        addRow();
    }

    // Khai báo biến cờ
    private boolean searchPerformed = false;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnFind) {
            // Đặt biến cờ thành true sau khi thực hiện tìm kiếm
            searchPerformed = true;
            String key = textField.getText();

            if (key.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please input slang word you want to find", "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Object[] options = { "Find Flow Slang Word", "Find Slang Flow definition" };
            int n = JOptionPane.showOptionDialog(this, "Choose mode " + "you want to execute?", "Choose mode find",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
            String[][] temp = null;
            if (n == 0) {
                clearTable();
                long startTime = System.currentTimeMillis();
                temp = slangWord.getDefinition(key);
                long endTime = System.currentTimeMillis();
                long timeElapsed = endTime - startTime;

                if (temp != null)
                    titleLabel1.setText("Execution time in milliseconds: " + temp.length + " Slang Words/ "
                            + String.valueOf(timeElapsed) + " ms");
                else {
                    titleLabel1.setText("Can't find that Slang Word");
                    return;
                }

                result = temp;
                for (int i = 0; i < result.length; i++) {
                    String ss[] = result[i];
                    model.addRow(ss);
                }
            } else if (n == 1) {
                clearTable();
                long startTime = System.currentTimeMillis();
                temp = slangWord.findDefinition(key);
                long endTime = System.currentTimeMillis();
                long timeElapsed = endTime - startTime;

                if (temp != null)
                    titleLabel1.setText("Execution time in milliseconds: " + temp.length + " Slang Words/ "
                            + String.valueOf(timeElapsed) + " ms");
                else {
                    titleLabel1.setText("Can't find that Slang Word");
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
                    slangWord.saveHistory(temp[ii][1], temp[ii][2]);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        } else if (e.getSource() == btnBack) {
            dispose();
            new MenuSWFrame();
        } else if (e.getSource() == btnClear) {
            // Kiểm tra xem đã thực hiện tìm kiếm hay chưa
            if (searchPerformed) {
                searchPerformed = false;
                clearTable();
                addRow();
            } else {
                // Hiển thị thông báo lỗi khi chưa thực hiện tìm kiếm
                JOptionPane.showMessageDialog(this, "Please perform a search first", "Inane error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void resetScreen() {
        textField.setText(""); // Xóa nội dung trường nhập liệu
        clearTable(); // Xóa bảng hiển thị
        addRow(); // Hiển thị dữ liệu mới
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = tableShow.getSelectedRow();
        int col = tableShow.getSelectedColumn();

        if (row == col && row == -1)
            return;

        String data = (String) tableShow.getValueAt(row, col);
        System.out.println("Table element selected is: " + row + col + " : " + data);

        if (col == 1 || col == 2) {
            String oldSlang = result[row][1];  // Lấy giá trị Slang cũ từ biến result
            String newSlang = (String) tableShow.getValueAt(row, 1); // Lấy giá trị Slang mới từ bảng
            String oldValue = result[row][2];  // Lấy giá trị Definition cũ từ biến result
            String newValue = (String) tableShow.getValueAt(row, 2); // Lấy giá trị Definition mới từ bảng
            result[row][1] = newSlang;
            result[row][2] = newValue;

            slangWord.set(oldSlang, newSlang, oldValue, newValue);
            JOptionPane.showMessageDialog(this, "Updated a row.");

        }


        tableShow.setFocusable(false);
    }

    public void valueChanged(ListSelectionEvent e) {
    }

    public void addRow() {
        result = datatemp;

        for (int i = 0; i < result.length; i++) {
            String ss[] = result[i];
            model.addRow(ss);
        }
    }

    void clearTable() {
        int rowCount = model.getRowCount();

        // Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }
}
