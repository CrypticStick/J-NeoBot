package com.Neobots2903.Discord.NeoBot.objects;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;

public class TextAreaConsole
extends OutputStream {
    private JTextArea textArea;

    public TextAreaConsole(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) throws IOException {
        this.textArea.setText(String.valueOf(this.textArea.getText()) + String.valueOf((char)b));
        this.textArea.setCaretPosition(this.textArea.getDocument().getLength());
        this.textArea.update(this.textArea.getGraphics());
    }
}

