package question;

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

import shows.MenuSWFrame;
public class QuizSWFrame extends JFrame implements ActionListener {
    JButton b1, b2, btnBack;

    public QuizSWFrame() {
        // A Label
        JLabel label = new JLabel("Quizs");
        label.setForeground(Color.BLUE);
        label.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setAlignmentY(-100);
        // Add space
        // A Grid
        b1 = new JButton("1. Find Definition");
        b1.addActionListener(this);
        b1.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
//        b1.setBackground(Color.cyan);
        b1.setForeground(Color.red);
        JPanel panelCenter = new JPanel();

        panelCenter.setLayout(new GridLayout(1, 2, 10, 10));
        panelCenter.add(b1);
        b2 = new JButton("2. Find SlangWord");
        b2.addActionListener(this);
        b2.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
//        b2.setBackground(Color.cyan);
        b2.setForeground(Color.red);
        panelCenter.add(b2);
        Dimension size2 = new Dimension(500, 100);
        panelCenter.setMaximumSize(size2);
        panelCenter.setPreferredSize(size2);
        panelCenter.setMinimumSize(size2);

        // Button back

        btnBack = new JButton("Back");
        btnBack.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        Dimension size3 = new Dimension(100, 50);
        buttonPanel.setMaximumSize(size3);
        buttonPanel.setPreferredSize(size3);
        buttonPanel.setMinimumSize(size3);
        buttonPanel.setLayout(new GridLayout(1, 1));
        buttonPanel.add(btnBack);

        // Setting content
        Container con = this.getContentPane();
        con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
        con.add(Box.createRigidArea(new Dimension(0, 10)));
        con.add(label);
        con.add(Box.createRigidArea(new Dimension(0, 50)));
        con.add(panelCenter);
        con.add(Box.createRigidArea(new Dimension(0, 50)));
        con.add(buttonPanel);
        con.add(Box.createRigidArea(new Dimension(0, 30)));

        this.setTitle("Quiz choose mode");
        this.setVisible(true);
        this.setSize(700, 400);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == b1) {
            this.dispose();
            new QuestionSWFrame(1);
        } else if (e.getSource() == b2) {
            this.dispose();
            new QuestionSWFrame(2);
        } else if (e.getSource() == btnBack) {
            this.dispose();
            new MenuSWFrame();
        }
    }
}
