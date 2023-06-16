package feature;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import read.SlangWord;
import shows.MenuSWFrame;

public class RandomSWFrame extends JFrame implements ActionListener {
    JButton btnBack, btnReset;
    SlangWord slangWord = SlangWord.getInstance();
    JTable slangTable;
    DefaultTableModel tableModel;

    public RandomSWFrame() {
        // Get Content
        Container con = this.getContentPane();

        // Title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Random Slang Words");
        titleLabel.setFont(new Font("Gill Sans MT", Font.BOLD, 35));
        titlePanel.add(titleLabel);
        titlePanel.setBackground(new Color(0, 153, 255));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.setMaximumSize(new Dimension(700, 100));

        // Slang word table
        String[] columns = { "Slang", "Definition" };
        String[][] data = { slangWord.random() };
        tableModel = new DefaultTableModel(data, columns);
        slangTable = new JTable(tableModel);
        slangTable.setFont(new Font("Gill Sans MT", Font.PLAIN, 14));
        slangTable.setRowHeight(30);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        slangTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        slangTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        slangTable.getColumnModel().getColumn(0).setPreferredWidth(200); // Adjust the width of the "Slang Word" column
        slangTable.getColumnModel().getColumn(1).setPreferredWidth(500);

        // Bottom btnBack btnReset
        btnReset = new JButton("Reset");
        btnBack = new JButton("Back");
        btnBack.addActionListener(this);
        btnReset.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnReset);
        buttonPanel.add(btnBack);

        // Setting con
        con.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 0, 10, 0);

        c.gridx = 0;
        c.gridy = 0;
        con.add(titlePanel, c);

        c.gridx = 0;
        c.gridy = 1;
        con.add(slangTable.getTableHeader(), c);

        c.gridx = 0;
        c.gridy = 2;
        con.add(slangTable, c);

        c.gridx = 0;
        c.gridy = 3;
        con.add(buttonPanel, c);

        // Setting JFrame
        setTitle("Random Slang Words");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
        setSize(800, 300);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            dispose();
            new MenuSWFrame();
        } else if (e.getSource() == btnReset) {
            tableModel.setRowCount(0);
            tableModel.addRow(slangWord.random());
        }
    }
}
