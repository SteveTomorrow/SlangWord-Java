package question;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import handle.SlangWord;
import shows.MenuSWFrame;

public class QuestionSWFrame extends JFrame implements ActionListener {
    private JButton answer1, answer2, answer3, answer4, btnBack, btnContinue;
    private String[] questionData;
    private int type;
    private Random random;
    private JLabel questionLabel;

    public QuestionSWFrame(int type) {
        this.type = type;
        random = new Random();
        questionData = new String[6];
        generateQuestion();

        // Label
        JLabel label = new JLabel("Choose the correct answer");
        label.setForeground(Color.BLUE);
        label.setFont(new Font("Gill Sans MT", Font.PLAIN, 35));
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setAlignmentY(-100);
        questionLabel = createQuestionLabel();
        questionLabel.setForeground(Color.black);
        questionLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 18));
        questionLabel.setAlignmentX(CENTER_ALIGNMENT);
        questionLabel.setAlignmentY(-100);

        // Grid Answers
        answer1 = createAnswerButton(questionData[1]);
        answer2 = createAnswerButton(questionData[2]);
        answer3 = createAnswerButton(questionData[3]);
        answer4 = createAnswerButton(questionData[4]);

        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new GridLayout(2, 2, 10, 10));
        panelCenter.add(answer1);
        panelCenter.add(answer2);
        panelCenter.add(answer3);
        panelCenter.add(answer4);
        Dimension size2 = new Dimension(600, 200);
        panelCenter.setMaximumSize(size2);
        panelCenter.setPreferredSize(size2);
        panelCenter.setMinimumSize(size2);

        // Buttons
        btnBack = new JButton("Back");
        btnBack.addActionListener(this);

        btnContinue = new JButton("Continue");
        btnContinue.addActionListener(this);
        btnContinue.setEnabled(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnBack);
        buttonPanel.add(btnContinue);

        Container container = this.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(label);
        container.add(Box.createRigidArea(new Dimension(0, 30)));
        container.add(questionLabel);
        container.add(Box.createRigidArea(new Dimension(0, 50)));
        container.add(panelCenter);
        container.add(Box.createRigidArea(new Dimension(0, 50)));
        container.add(buttonPanel);

        this.setTitle("Question Quiz");
        this.setVisible(true);
        this.setSize(700, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == answer1) {
            checkAnswer(answer1);
        } else if (e.getSource() == answer2) {
            checkAnswer(answer2);
        } else if (e.getSource() == answer3) {
            checkAnswer(answer3);
        } else if (e.getSource() == answer4) {
            checkAnswer(answer4);
        } else if (e.getSource() == btnBack) {
            this.dispose();
            new MenuSWFrame();
        } else if (e.getSource() == btnContinue) {
            resetQuestion();
        }
    }

    private void checkAnswer(JButton selectedAnswer) {
        disableAllButtons();
        String selectedText = selectedAnswer.getText().substring(3); // Remove the prefix "A. ", "B. ", "C. ", or "D. "
        if (selectedText.equals(questionData[5])) {
            selectedAnswer.setBackground(Color.GREEN);
            JOptionPane.showMessageDialog(this, "Correct Answer!");
        } else {
            selectedAnswer.setBackground(Color.RED);
            JOptionPane.showMessageDialog(this, "Wrong Answer!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        showCorrectAnswer();
        btnContinue.setEnabled(true);
    }

    private void showCorrectAnswer() {
        if (questionData[1].equals(questionData[5])) {
            answer1.setBackground(Color.YELLOW);
        }
        else if (questionData[2].equals(questionData[5])) {
            answer2.setBackground(Color.YELLOW);
        }
        else if (questionData[3].equals(questionData[5])) {
            answer3.setBackground(Color.YELLOW);
        }
        else if (questionData[4].equals(questionData[5])) {
            answer4.setBackground(Color.YELLOW);
        }
    }

    private void disableAllButtons() {
        answer1.setEnabled(false);
        answer2.setEnabled(false);
        answer3.setEnabled(false);
        answer4.setEnabled(false);
    }

    private void generateQuestion() {
        questionData = SlangWord.getInstance().quiz(type);
    }

    private void resetQuestion() {
        resetButtonColors();
        enableAllButtons();
        generateQuestion();
        updateQuestionLabel();
        updateAnswerButtons();
        btnContinue.setEnabled(false);
    }

    private void resetButtonColors() {
        answer1.setBackground(null);
        answer2.setBackground(null);
        answer3.setBackground(null);
        answer4.setBackground(null);
    }

    private void enableAllButtons() {
        answer1.setEnabled(true);
        answer2.setEnabled(true);
        answer3.setEnabled(true);
        answer4.setEnabled(true);
    }

    private void updateQuestionLabel() {
        questionLabel.setText("`" + questionData[0] + "`" + " has Slang Word is?");
    }

    private void updateAnswerButtons() {
        answer1.setText("A. " + questionData[1]);
        answer2.setText("B. " + questionData[2]);
        answer3.setText("C. " + questionData[3]);
        answer4.setText("D. " + questionData[4]);
    }

    private JButton createAnswerButton(String answerText) {
        JButton button = new JButton(answerText);
        button.addActionListener(this);
        button.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
        button.setForeground(Color.red);
        return button;
    }

    private JLabel createQuestionLabel() {
        if (type == 1) {
            return new JLabel("What does Slang " + "`" + questionData[0] + "`" + " mean?");
        } else {
            return new JLabel("`" + questionData[0] + "`" + " has Slang Word is?");
        }
    }
}
