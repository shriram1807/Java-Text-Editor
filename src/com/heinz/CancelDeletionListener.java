package com.heinz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class CancelDeletionListener implements ActionListener {

	private final JFrame delete;
	public CancelDeletionListener(JFrame delete)
	{
		this.delete=delete;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		delete.setVisible(false);
	}

}
