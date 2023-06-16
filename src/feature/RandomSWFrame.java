package feature;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import shows.MenuSWFrame;
import read.SlangWord;
public class RandomSWFrame extends JFrame implements ActionListener {
    JButton btnBack, btnReset;
    SlangWord slangWord = SlangWord.getInstance();
    JLabel slangLabel, definitionLabel;

    public RandomSWFrame() {
        // Get Content
        Container con = this.getContentPane();

        // Title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Random Slang Words");
        titleLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
        titlePanel.add(titleLabel);
        titlePanel.setBackground(Color.blue);
        titleLabel.setForeground(Color.white);
        titlePanel.setMaximumSize(new Dimension(700, 300));

        // Slang word
        String s[] = slangWord.random();
        JPanel slangPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel definitionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel slangDefinitionPanel = new JPanel();
        JLabel slangHeaderLabel = new JLabel("Slang:");
        slangLabel = new JLabel(s[0]);
        JLabel definitionHeaderLabel = new JLabel("Definition:");
        definitionLabel = new JLabel(s[1]);
        slangLabel.setForeground(Color.GREEN);
        definitionLabel.setForeground(Color.RED);
        slangHeaderLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
        slangLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
        definitionHeaderLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
        definitionLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
        slangPanel.add(slangHeaderLabel);
        slangPanel.add(slangLabel);
        definitionPanel.add(definitionHeaderLabel);
        definitionPanel.add(definitionLabel);

        // Bottom btnback btnRenew
        btnReset = new JButton("Reset");
        btnBack = new JButton("Back");
        btnBack.addActionListener(this);
        btnReset.addActionListener(this);
        JPanel buttonPane = new JPanel();
        slangDefinitionPanel.setLayout(new BoxLayout(slangDefinitionPanel, BoxLayout.Y_AXIS));
        slangDefinitionPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        slangDefinitionPanel.add(slangPanel);
        slangDefinitionPanel.add(definitionPanel);
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(btnReset);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(btnBack);

        // Setting con
        con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
        con.add(Box.createRigidArea(new Dimension(0, 10)));
        con.add(titlePanel);
        con.add(Box.createRigidArea(new Dimension(0, 10)));
        con.add(slangPanel);
        con.add(Box.createRigidArea(new Dimension(0, 10)));
        con.add(definitionPanel);
        con.add(Box.createRigidArea(new Dimension(0, 10)));
        con.add(buttonPane);
        // Setting JFrame
        this.setTitle("Random Slang Words");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(800, 300);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            this.dispose();
            new MenuSWFrame();
        } else if (e.getSource() == btnReset) {
            String s[] = slangWord.random();
            slangLabel.setText(s[0]);
            definitionLabel.setText(s[1]);
        }
    }

}
