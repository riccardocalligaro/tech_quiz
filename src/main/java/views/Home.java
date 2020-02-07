package views;

import data.QuestionsRepositoryImpl;
import domain.QuestionsRepository;
import entities.Question;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Home extends JPanel {

    int position = 0;
    int correctAnswers = 0;
    int wrongAnswers = 0;
    int percent = 0;
    List<Question> questions;

    public Home() throws IOException {
        QuestionsRepository questionsRepository = new QuestionsRepositoryImpl();
        questions = questionsRepository.getQuestions();
        paint();
    }

    void showFinalScore() throws IOException {

        JLabel overallLabel = new JLabel();

        if (correctAnswers > wrongAnswers) {
            overallLabel.setText("Bene!");
        } else {
            overallLabel.setText("Non molto bene!");
        }
        JLabel label = new JLabel("Domande corrette: " + correctAnswers);
        JLabel labelWrong = new JLabel("Domande sbgaliate: " + wrongAnswers);

        JButton restartButton = new JButton("Restart");

        restartButton.addActionListener(e -> {
            position = 0;
            correctAnswers = 0;
            wrongAnswers = 0;

            removeAll();
            revalidate();
            repaint();
            try {
                paint();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });

        add(label);
        add(labelWrong);
        add(restartButton);
        add(overallLabel);


    }

    void paint() throws IOException {

        setBorder(new EmptyBorder(16, 16, 16, 16));

        setLayout(new GridLayout(4, 1));

        JLabel label = new JLabel(questions.get(position).getTitle());
        add(label);

        JPanel buttonsPanel = new JPanel();
        //buttonsPanel.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final List<String> answers = questions.get(position).getPossibleAnswers();

        final ButtonGroup buttons = new ButtonGroup();

        Collections.shuffle(answers);

        answers.forEach((answer) -> {
            JRadioButton btn = new JRadioButton(answer);
            btn.setMnemonic(KeyEvent.VK_C);
            btn.setActionCommand(answer);
            buttons.add(btn);
            buttonsPanel.add(btn);
        });

        add(buttonsPanel);

        JProgressBar jProgressBar = new JProgressBar();
        jProgressBar.setMinimum(0);
        jProgressBar.setMaximum(100);
        jProgressBar.setValue(0);


        add(jProgressBar);

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {


            @Override
            protected Void doInBackground() throws Exception {
                for (long i = 1; i <= 100; i++) {
                    Thread.sleep(50);
                    jProgressBar.setValue(percent++);
                    if (percent >= 101) {
                        break;
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                if (!isCancelled()) {
                    JOptionPane.showMessageDialog(getRootPane(), "Tempo scaduto!");
                    percent = 0;
                    wrongAnswers++;

                    if (position < questions.size() - 1) {
                        nextAnswer();
                    } else {
                        removeAll();
                        revalidate();
                        repaint();
                        try {
                            showFinalScore();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }


        };

        worker.execute();


        JButton sendButton = new JButton("Invia");

        final String answer = questions.get(position).getCorrectAnswer();

        // TODO: fix null pointer when no answer
        sendButton.addActionListener(e -> {
            worker.cancel(true);

            this.percent = 0;

            jProgressBar.setValue(0);

            if (position < questions.size() - 1) {

                if (buttons.getSelection().getActionCommand().equals(answer)
                ) {
                    correctAnswers++;
                    JOptionPane.showMessageDialog(getRootPane(), "Giusto!");
                } else {
                    JOptionPane.showMessageDialog(getRootPane(), "Risposta corretta: " + answer);
                    wrongAnswers++;
                }

                nextAnswer();


            } else {
                if (buttons.getSelection().getActionCommand().equals(answer)
                ) {
                    correctAnswers++;
                    JOptionPane.showMessageDialog(getRootPane(), "Giusto!");
                } else {
                    JOptionPane.showMessageDialog(getRootPane(), "Risposta corretta: " + answer);
                    wrongAnswers++;
                }
                removeAll();
                revalidate();
                repaint();
                try {
                    showFinalScore();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }


        });
        add(sendButton);
    }

    public static void fill(JProgressBar b) {
        int i = 0;
        try {
            while (i <= 100) {
                // fill the menu bar
                b.setValue(i + 10);

                // delay the thread
                Thread.sleep(1000);
                i += 20;
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    void nextAnswer() {
        position++;
        removeAll();
        revalidate();
        repaint();
        try {
            paint();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
