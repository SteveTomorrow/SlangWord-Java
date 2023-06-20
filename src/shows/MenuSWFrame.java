package shows;

import feature.*;
import question.QuestionSWFrame;
import handle.SlangWord;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuSWFrame extends JFrame implements ActionListener {
    JMenuItem exitMenuItem;
    SlangWord slangWord;
    JMenu menu, extraMenu, gameMenu, exitMenu;
    JMenuItem addMenuItem, editMenuItem, deleteMenuItem, historyMenuItem, randomMenuItem, searchMenuItem,
            resetMenuItem, findDefinitionMenuItem, findSlangWordMenuItem;

    JTable slangWordTable;
    DefaultTableModel model;

    public MenuSWFrame() {
        // Label
        JLabel titleLabel = new JLabel("Slang Words");
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        JMenuBar menuBar = new JMenuBar();

        // JMenu 1: Menu
        menu = new JMenu("Menu");
        menu.setFont(new Font("Gill Sans MT", Font.PLAIN, 24));
        menu.setFocusable(false);
        menu.setForeground(Color.BLACK);

        addMenuItem = createMenuItem("Add", 18);
        editMenuItem = createMenuItem("Edit", 18);
        deleteMenuItem = createMenuItem("Delete", 18);

        menu.add(addMenuItem);
        menu.add(editMenuItem);
        menu.add(deleteMenuItem);
        menuBar.add(menu);

        // JMenu 2: Extra Features
        extraMenu = new JMenu("Extra Features");
        extraMenu.setFont(new Font("Gill Sans MT", Font.PLAIN, 24));
        extraMenu.setForeground(Color.BLACK);

        randomMenuItem = createMenuItem("Random", 18);
        searchMenuItem = createMenuItem("Search", 18);
        historyMenuItem = createMenuItem("History", 18);
        resetMenuItem = createMenuItem("Reset", 18);

        extraMenu.add(searchMenuItem);
        extraMenu.add(randomMenuItem);
        extraMenu.add(historyMenuItem);
        extraMenu.add(resetMenuItem);
        menuBar.add(extraMenu);

        // JMenu 3: Game
        gameMenu = new JMenu("Game");
        gameMenu.setFont(new Font("Gill Sans MT", Font.PLAIN, 24));
        gameMenu.setForeground(Color.BLACK);

        findDefinitionMenuItem = createMenuItem("Definition", 18);
        findSlangWordMenuItem = createMenuItem("SlangWord", 18);

        gameMenu.add(findSlangWordMenuItem);
        gameMenu.add(findDefinitionMenuItem);
        menuBar.add(gameMenu);

        slangWord = SlangWord.getInstance();
        // JMenu 4: Exit

        exitMenu = new JMenu("Exit");
        exitMenu.setFont(new Font("Gill Sans MT", Font.PLAIN, 24));
        exitMenu.setFocusable(false);
        exitMenu.setForeground(Color.BLACK);

        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
        exitMenuItem.addActionListener(this);
        exitMenu.add(exitMenuItem);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(exitMenu);

        JLabel resultLabel = new JLabel();
        resultLabel.setForeground(Color.BLACK);
        resultLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
        resultLabel.setAlignmentX(CENTER_ALIGNMENT);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.GREEN);
        String[][] data = slangWord.getData();
        String[][] dataCopy = slangWord.getData();
        String[] column = {"STT", "Slang Word", "Definition"};
        resultLabel.setText("Total: " + data.length + " slang words");
        model = new DefaultTableModel(data, column);
        slangWordTable = new JTable(model);
        slangWordTable.setRowHeight(30);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        slangWordTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        slangWordTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        slangWordTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        slangWordTable.putClientProperty("terminateEditOnFocusLost", true);

        // Set column widths
        slangWordTable.getColumnModel().getColumn(0).setMaxWidth(50); // Adjust the width of the "STT" column
        slangWordTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Adjust the width of the "Slang Word" column
        slangWordTable.getColumnModel().getColumn(2).setPreferredWidth(500); // Adjust the width of the "Definition" column

        JScrollPane scrollPane = new JScrollPane(slangWordTable);
        centerPanel.add(scrollPane);

        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));

        JPanel buttonPanel = new JPanel();


        Dimension panelSize = new Dimension(650, 530);
        centerPanel.setMaximumSize(panelSize);
        centerPanel.setPreferredSize(panelSize);
        centerPanel.setMinimumSize(panelSize);

        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(titleLabel);
        container.add(Box.createRigidArea(new Dimension(0, 20)));
        container.add(resultLabel);
        container.add(Box.createRigidArea(new Dimension(0, 30)));
        container.add(centerPanel);
        container.add(Box.createRigidArea(new Dimension(0, 30)));
        container.add(buttonPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Menu Window");
        setJMenuBar(menuBar);
        setVisible(true);
        setSize(800, 850);
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenDimension.width / 2 - getSize().width / 2, screenDimension.height / 2 - getSize().height / 2);
    }

    private JMenuItem createMenuItem(String text, int fontSize) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(this);
        menuItem.setFont(new Font("Gill Sans MT", Font.PLAIN, fontSize));
        menuItem.setFocusable(false);
        menuItem.setForeground(Color.RED);
        return menuItem;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addMenuItem) {
            dispose();
            new AddSWFrame();
        } else if (e.getSource() == editMenuItem) {
            dispose();
            try {
                new EditSWFrame();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == deleteMenuItem) {
            dispose();
            try {
                new DeleteSWFrame();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == historyMenuItem) {
            dispose();
            new HistorySWFrame();
        } else if (e.getSource() == randomMenuItem) {
            dispose();
            new RandomSWFrame();
        } else if (e.getSource() == searchMenuItem) {
            System.out.println("Change Activity");
            dispose();
            try {
                new SearchSWFrame();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == resetMenuItem) {
            int choice = JOptionPane.showConfirmDialog(this, "Do you really want to reset Slang Word?", "An Inane Question",
                    JOptionPane.YES_NO_OPTION);
            if (choice == 0) {
                slangWord.reset();
                JOptionPane.showMessageDialog(this, "Reset success.");
            }
        } else if (e.getSource() == findDefinitionMenuItem) {
            dispose();
            new QuestionSWFrame(1);
        } else if (e.getSource() == findSlangWordMenuItem) {
            dispose();
            new QuestionSWFrame(2);
        } else if (e.getSource() == exitMenuItem) {
            int choice = JOptionPane.showConfirmDialog(this, "Do you really want to exit?", "Confirm Exit",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }        }
    }
}
