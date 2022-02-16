package com.katjes.city;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class Wait extends JFrame {
	JProgressBar ProgressBar = new JProgressBar();
	JLabel laufzeitLabel = new JLabel();

	Wait() {
		getContentPane().setLayout(null);
		setTitle("Bitte warten...");
		setSize(320, 110);
		setResizable(false);

		ProgressBar.setMaximum(100);
		ProgressBar.setForeground(new Color(0, 0, 113));
		ProgressBar.setBounds(20, 40, 274, 20);
		ProgressBar.setStringPainted(true);

		laufzeitLabel.setText("Fortschritt in %");
		laufzeitLabel.setBounds(22, 20, 123, 15);

		getContentPane().add(ProgressBar);
		getContentPane().add(laufzeitLabel);

		setVisible(true);
	}

	public void progress(final int i) {
		ProgressBar.setValue(i);

		if (i == 100) {
			this.dispose();
		}
	}
}