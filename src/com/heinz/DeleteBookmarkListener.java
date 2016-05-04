package com.heinz;

import static com.heinz.TextReaderWindow.bookmarkValue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

public class DeleteBookmarkListener implements ActionListener{

	private final JComboBox<String> combo;
	private final JMenu oldbookmarkMenu;
	private final JTextArea textReaderArea;
	public DeleteBookmarkListener(JComboBox<String> combo,JMenu oldbookmarkMenu,JTextArea textReaderArea)
	{
		this.combo=combo;
		this.oldbookmarkMenu=oldbookmarkMenu;
		this.textReaderArea=textReaderArea;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub


		String t = combo.getSelectedItem().toString();
		bookmarkValue.remove(t);
		resetBookmark();
		combo.removeAllItems();
		Set<Entry<String, Integer>> bookmarkSet = bookmarkValue.entrySet();
		for(Entry<String, Integer> bookmark:bookmarkSet)
		{
			combo.addItem(bookmark.getKey());	
		}
		

	
	}

	
	private void resetBookmark() {

		//sortByValues(bookmarkValue);
		oldbookmarkMenu.removeAll();

		Set<Entry<String, Integer>> bookmarkSet = bookmarkValue.entrySet();
		
		for(Entry<String, Integer> bookmark:bookmarkSet)
		{
			String text = bookmark.getKey();

			JMenuItem bookmarkItem = new JMenuItem();
			bookmarkItem.setText(text);

			oldbookmarkMenu.add(bookmarkItem);

			bookmarkItem.addActionListener(new ResetBookmarkMenuListener(bookmarkItem, textReaderArea));
		}
	
		}
}
