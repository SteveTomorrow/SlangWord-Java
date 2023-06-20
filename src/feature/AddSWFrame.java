package feature;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import handle.SlangWord;
import shows.MenuSWFrame;

public class AddSWFrame extends JFrame implements ActionListener {
    SlangWord slangWord;
    JButton btnBack, btnAdd;
    JTextField textFieldDefinition, textFieldSlang;

    public AddSWFrame() {
        // Get container & slang word
        slangWord = SlangWord.getInstance();
        Container con = this.getContentPane();
        con.setBackground(Color.WHITE);

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Add Slang Words");
        titleLabel.setFont(new Font("Gill Sans MT", Font.BOLD, 35));
        titlePanel.add(titleLabel);
        titlePanel.setBackground(new Color(0, 153, 255));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.setMaximumSize(new Dimension(700, 100));

        // Form
        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0, 153, 255), 2));
        form.setMaximumSize(new Dimension(400, 100));

        JPanel slangPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        slangPanel.setLayout(layout);
        JLabel labelForSlang = new JLabel("Slang word:");
        textFieldSlang = new JTextField("", 20);
        labelForSlang.setPreferredSize(new Dimension(100, 20));
        slangPanel.add(labelForSlang);
        slangPanel.add(textFieldSlang);
        layout.putConstraint(SpringLayout.WEST, labelForSlang, 10, SpringLayout.WEST, slangPanel);
        layout.putConstraint(SpringLayout.NORTH, labelForSlang, 10, SpringLayout.NORTH, slangPanel);
        layout.putConstraint(SpringLayout.WEST, textFieldSlang, 10, SpringLayout.EAST, labelForSlang);
        layout.putConstraint(SpringLayout.NORTH, textFieldSlang, 10, SpringLayout.NORTH, slangPanel);
        layout.putConstraint(SpringLayout.EAST, slangPanel, 10, SpringLayout.EAST, textFieldSlang);
        layout.putConstraint(SpringLayout.SOUTH, slangPanel, 10, SpringLayout.SOUTH, textFieldSlang);

        JPanel definitionPanel = new JPanel();
        SpringLayout layout1 = new SpringLayout();
        definitionPanel.setLayout(layout1);
        JLabel labelForDefinition = new JLabel("Definition:");
        labelForDefinition.setPreferredSize(new Dimension(100, 20));
        textFieldDefinition = new JTextField("", 20);
        definitionPanel.add(labelForDefinition);
        definitionPanel.add(textFieldDefinition);
        layout1.putConstraint(SpringLayout.WEST, labelForDefinition, 10, SpringLayout.WEST, definitionPanel);
        layout1.putConstraint(SpringLayout.NORTH, labelForDefinition, 10, SpringLayout.NORTH, definitionPanel);
        layout1.putConstraint(SpringLayout.WEST, textFieldDefinition, 10, SpringLayout.EAST, labelForDefinition);
        layout1.putConstraint(SpringLayout.NORTH, textFieldDefinition, 10, SpringLayout.NORTH, definitionPanel);
        layout1.putConstraint(SpringLayout.EAST, definitionPanel, 10, SpringLayout.EAST, textFieldDefinition);
        layout1.putConstraint(SpringLayout.SOUTH, definitionPanel, 10, SpringLayout.SOUTH, textFieldDefinition);

        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.add(Box.createRigidArea(new Dimension(0, 20)));
        form.add(slangPanel);
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        form.add(definitionPanel);
        form.add(Box.createRigidArea(new Dimension(0, 20)));

        // Button Back and button Add
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        btnBack = new JButton("Back");
        btnBack.setFocusable(false);
        btnBack.addActionListener(this);
        btnBack.setAlignmentX(CENTER_ALIGNMENT);
        btnAdd = new JButton("Add");
        btnAdd.setFocusable(false);
        btnAdd.addActionListener(this);
        btnAdd.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.add(btnBack);
        buttonPanel.add(btnAdd);

        // Setting content
        con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
        con.add(Box.createRigidArea(new Dimension(0, 20)));
        con.add(titlePanel);
        con.add(Box.createRigidArea(new Dimension(0, 20)));
        con.add(form);
        con.add(Box.createRigidArea(new Dimension(0, 20)));
        con.add(buttonPanel);

        // Setting Frame
        this.setTitle("Add Slang Words");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setSize(500, 400);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            this.dispose();
            new MenuSWFrame();
        } else if (e.getSource() == btnAdd) {
            String slang = textFieldSlang.getText();
            String definition = textFieldDefinition.getText();
            if (slang.isEmpty() || definition.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Slang and Definition must not be empty", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (slangWord.checkSlang(slang)) {
                Object[] options = { "Overwrite", "Duplicate" };
                int choice = JOptionPane.showOptionDialog(this,
                        "The slang word `" + slang + "` already exists in the SlangWord list.", "Confirmation",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (choice == 0) {
                    slangWord.addOverwrite(slang, definition);
                    JOptionPane.showMessageDialog(this, "Slang Word overwritten successfully.");
                } else if (choice == 1) {
                    slangWord.addDuplicate(slang, definition);
                    JOptionPane.showMessageDialog(this, "Slang Word duplicated successfully.");
                }
            } else {
                slangWord.addNew(slang, definition);
                JOptionPane.showMessageDialog(this, "Slang Word added successfully.");
            }

            textFieldSlang.setText("");
            textFieldDefinition.setText("");
        }
    }

}
