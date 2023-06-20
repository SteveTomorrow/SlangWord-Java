package feature;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import handle.SlangWord;
import shows.MenuSWFrame;

public class HistorySWFrame extends JFrame implements ActionListener {
    JButton btnBack;
    SlangWord slangWord = SlangWord.getInstance();

    public HistorySWFrame() {
        Container con = this.getContentPane();

        // History
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("History Slang Words");
        titleLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
        titlePanel.add(titleLabel);
        titlePanel.setBackground(Color.BLUE);
        titleLabel.setForeground(Color.WHITE);
        titlePanel.setMaximumSize(new Dimension(700, 300));

        // Table
        JPanel panelTable = new JPanel();
        panelTable.setBackground(Color.BLACK);

        JLabel resultLabel = new JLabel();
        resultLabel.setForeground(Color.BLACK);
        resultLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
        resultLabel.setAlignmentX(CENTER_ALIGNMENT);

        String data[][] = slangWord.readHistory();
        String column[] = { "STT", "Slang Word", "Definition" };
        resultLabel.setText("Total: " + data.length + " slang words");
        JTable tableShow = new JTable(data, column);
        tableShow.setRowHeight(30);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableShow.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableShow.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableShow.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableShow.getColumnModel().getColumn(0).setMaxWidth(50); // Adjust the width of the "STT" column
        tableShow.getColumnModel().getColumn(1).setPreferredWidth(200); // Adjust the width of the "Slang Word" column
        tableShow.getColumnModel().getColumn(2).setPreferredWidth(500); // Adjust the width of the "Definition" column
        tableShow.setEnabled(false);
        JScrollPane sp = new JScrollPane(tableShow);
        panelTable.setLayout(new GridLayout(1, 1));
        panelTable.add(sp);
        Dimension size2 = new Dimension(650, 530);
        panelTable.setMaximumSize(size2);
        panelTable.setPreferredSize(size2);
        panelTable.setMinimumSize(size2);

        // Buttons
        JPanel buttonPanel = new JPanel();
        btnBack = new JButton("Back");
        btnBack.addActionListener(this);
        buttonPanel.add(btnBack);

        con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
        con.add(Box.createRigidArea(new Dimension(0, 10)));
        con.add(titlePanel);
        con.add(Box.createRigidArea(new Dimension(0, 20)));
        con.add(resultLabel);
        con.add(Box.createRigidArea(new Dimension(0, 20)));
        con.add(panelTable);
        con.add(Box.createRigidArea(new Dimension(0, 50)));
        con.add(buttonPanel);
        con.add(Box.createRigidArea(new Dimension(0, 20)));

        // Setting JFrame
        this.setTitle("History Window");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(800, 800);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            this.dispose();
            new MenuSWFrame();
        }
    }
}
