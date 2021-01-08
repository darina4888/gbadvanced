package ru.lesson4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Main {
    public static void main(String[] args) {
       Chat chat = new Chat();
       chat.showChat();
    }
}

class Chat extends JFrame {

    public void showChat() {
        JFrame frame = new JFrame("Chat");
        JPanel panel = new JPanel();
        JPanel panel_ = new JPanel();
        JPanel panel__ = new JPanel();

        panel.setLayout(new GridBagLayout());

        final JTextArea msgs = new JTextArea(7, 40);
        msgs.setEditable(false);
        final JTextField text = new JTextField();
        text.setPreferredSize( new Dimension( 420, 24 ) );

        //add message with Enter
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMessage(msgs,text);
            }
        };
        text.addActionListener( action );

        JButton button = new JButton("Add comment");
        //add message with button
        button.addActionListener(e -> this.addMessage(msgs,text));

        panel.add(msgs);
        panel__.add(text);
        panel_.add(button);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel,BorderLayout.NORTH);
        frame.add(panel__,BorderLayout.CENTER);
        frame.add(panel_,BorderLayout.SOUTH);
        frame.setSize(500, 300);
        frame.setVisible(true);
    }

    /**
     * Add message to chat
     * @param chat textarea with messages
     * @param text input for new message
     */
    private void addMessage(JTextArea chat,JTextField text) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        if(!text.getText().equals("")) {
            chat.append(dtf.format(now) + " : " + text.getText());
            chat.append("\n");
            text.setText("");
        }
    }
}
