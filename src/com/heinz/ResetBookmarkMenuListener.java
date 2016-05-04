package com.heinz;

import static com.heinz.TextReaderWindow.bookmarkValue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class ResetBookmarkMenuListener implements ActionListener {

	private final JMenuItem bookmarkItem;
	private final JTextArea  textReaderArea;
	public ResetBookmarkMenuListener(JMenuItem bookmarkItem,JTextArea  textReaderArea)
	{
		this.bookmarkItem=bookmarkItem;
		this.textReaderArea=textReaderArea;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String text = bookmarkItem.getText();
		
		if(bookmarkValue.containsKey(text))
		{
			try {
				
			int key=bookmarkValue.get(text);
			textReaderArea.setCaretPosition(key);
			
			Highlighter h = textReaderArea.getHighlighter();
			h.removeAllHighlights();
     		h.addHighlight(key-1, key + 1, DefaultHighlighter.DefaultPainter);
     		
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	
	}

}
