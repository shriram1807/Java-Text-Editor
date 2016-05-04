package com.heinz;

import static com.heinz.TextReaderWindow.*;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 * @author shriram
 *Action listener adapts -defines action on button clicks and feature selection
 *Serves as the control part of the editor in the context of MVC model 
 */
public class ActionListenerAdapter implements ActionListener {

	private static final String MESSAGE_FILE_CHOOSER = "Text Files Only";
	private static final String MESSAGE_FILE_NOT_FOUND = " is Not Found!!...";
	private static final String MESSAGE_SEARCH_GOOGLE = "Please select text to search";
	private static final String MESSAGE_TRANSLATE_GOOGLE = "Please select text to translate";
	private static final String EXTENSION_TEXT = "txt";
	private static final String EXTENSION_JAVA = "java";
	private static final String CURRENT_DIRECTORY = "user.dir";
	private static final String DELIMITER_NEW_LINE = "\n";
	private static final String DELIMITER_WHITE_SPACE = " ";
	private static final String DELIMITER_DOT = ".";
	private static final String MESSAGE_FILE_APPEND = "File: ";
	private static final String TITLE_ABOUT_FRAME = "About Text Editor";
	private static final String TRANSLATE_GOOGLE = "https://translate.google.co.in/#auto/en/";
	private static final String SEARCH_GOOGLE = "https://www.google.com/?#q=";
    private static final String PATH_RESOURCE="/Resource/readme.txt";
	private int lastAddedKeyword = 0;

	private final JFrame parent;
	private final JTextArea textReaderArea;
	private final JLabel fileNameLabel;

	private final JMenu searchMenu;
	private final JMenu oldbookmarkMenu;

	private File file = null;

	private JMenuItem keyword1;
	private JMenuItem keyword2;
	private JMenuItem keyword3;
	private JMenuItem keyword4;
	private JMenuItem keyword5;

	public ActionListenerAdapter(final JFrame parent, final JTextArea textReaderArea, JMenu searchMenu,
			JMenu oldbookmarkMenu, final JLabel fileNameLabel) {
		this.parent = parent;
		this.textReaderArea = textReaderArea;
		this.searchMenu = searchMenu;
		this.fileNameLabel = fileNameLabel;
		this.oldbookmarkMenu = oldbookmarkMenu;

	}

	/**
	 * Action Listener for all buttons Specifies functionality of each button
	 * when clicked
	 * 
	 * @return void
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		textReaderArea.setEditable(true);
		Desktop desktop = Desktop.getDesktop();

		switch (e.getActionCommand()) {

		case BUTTON_OPEN:
			file = browseFile();
			if (file != null) {
				textReaderArea.setText(readFileContents(file.getPath()));
			}
			break;

		case MENU_ITEM_EXIT:

			parent.setVisible(false);
			System.exit(0);
			break;

		case BUTTON_CLOSE:
			file = null;
			textReaderArea.setText("");
			fileNameLabel.setText("");
			break;

		case BUTTON_SAVE:
			if (file != null) {
				try (FileWriter writer = new FileWriter(file);) {

					writer.write(textReaderArea.getText());
					fileNameLabel.setText(file.getName() + "Successfully Saved");

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				saveAs();
			}
			break;
		case BUTTON_SAVE_AS:
			saveAs();
			break;

		case MENU_ITEM_ABOUT:

			JFrame aboutFrame = new JFrame(TITLE_ABOUT_FRAME);
			JPanel contentPanel = new JPanel();
			contentPanel.add(new JLabel("Text Editor by Shriram Rangarajan"));
			Dimension frameDimension = new Dimension(550, 450);
			/* setLayout(new BorderLayout()); */
			aboutFrame.setSize(frameDimension);
			aboutFrame.add(contentPanel);
			aboutFrame.setVisible(true);
			aboutFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			break;

		case MENU_BOOKMARK_NEW:

			String tagname = JOptionPane.showInputDialog("Enter tag name", "enter text here");

			if (file != null && tagname != null) {

				int tagvalue = textReaderArea.getCaretPosition();

				bookmarkValue.put(tagname, tagvalue);
				resetBookmark();

			} else {
				JOptionPane.showMessageDialog(parent, "Bookmarking needs file content..please add a file", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
			break;
		case MENU_BOOKMARK_DELETE:
			deletebookmark();
			break;

		case MENU_ITEM_SEARCH_NEW:

			String newSearchKey = JOptionPane.showInputDialog("Enter Search Keyword...");
			boolean isWordFound = searchAndHighlightWord(newSearchKey);
			if (isWordFound) {
				if (lastAddedKeyword <= 4) {
					switch (lastAddedKeyword) {
					case 0:
						if (keyword1 == null)
							addKeyWord(keyword1 = new JMenuItem(newSearchKey));
						else
							keyword1.setText(newSearchKey);

						lastAddedKeyword++;
						break;
					case 1:
						if (keyword2 == null)
							addKeyWord(keyword2 = new JMenuItem(newSearchKey));
						else
							keyword2.setText(newSearchKey);
						lastAddedKeyword++;
						break;
					case 2:
						if (keyword3 == null)
							addKeyWord(keyword3 = new JMenuItem(newSearchKey));
						else
							keyword3.setText(newSearchKey);
						lastAddedKeyword++;
						break;
					case 3:
						if (keyword4 == null)
							addKeyWord(keyword4 = new JMenuItem(newSearchKey));
						else
							keyword4.setText(newSearchKey);
						lastAddedKeyword++;
						break;
					case 4:
						if (keyword5 == null)
							addKeyWord(keyword5 = new JMenuItem(newSearchKey));
						else
							keyword5.setText(newSearchKey);
						lastAddedKeyword++;
						break;

					}
				} else {
					lastAddedKeyword = 0;
				}
			}
			break;

		case MENU_ITEM_COUNT:
			fileNameLabel.setText("Total number of words: " + getCountOfWords());
			break;

		case MENU_ITEM_UNIQUE:
			fileNameLabel.setText("Total number of unique words: " + getUniqueWordCount());
			break;

		case MENU_ITEM_PRINT:
			if (file != null) {

				if (desktop.isSupported(Action.PRINT)) {
					try {
						desktop.print(file);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			break;

		case MENU_ITEM_SEARCH:
			if (desktop.isSupported(Action.BROWSE)) {
				try {
					String selectedText = textReaderArea.getSelectedText();
					if (selectedText != null && !selectedText.isEmpty()) {
						desktop.browse(new URI(SEARCH_GOOGLE + selectedText));
					} else {
						fileNameLabel.setText(MESSAGE_SEARCH_GOOGLE);
					}
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				fileNameLabel.setText("Default browser is not found");
			}
			break;

		case MENU_ITEM_TRANSLATE:
			if (desktop.isSupported(Action.BROWSE)) {
				try {
					String selectedText = textReaderArea.getSelectedText();
					if (selectedText != null && !selectedText.isEmpty()) {
						desktop.browse(new URI(TRANSLATE_GOOGLE + selectedText));
					} else {
						fileNameLabel.setText(MESSAGE_TRANSLATE_GOOGLE);
					}
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				fileNameLabel.setText("Default browser is not found");
			}
			break;

		case MENU_ITEM_NEW_FEATURE:
			try(Scanner scanner=new Scanner(new File(System.getProperty(CURRENT_DIRECTORY)+PATH_RESOURCE)))
			{
				String text="";
				while(scanner.hasNextLine())
				{
					text+=scanner.nextLine();
				}
				textReaderArea.setText(text);
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			break;
			
		default:
			if (keyword1 != null && keyword1.getText().equals(e.getActionCommand())) {
				searchAndHighlightWord(keyword1.getText());
			} else if (keyword2 != null && keyword2.getText().equals(e.getActionCommand())) {
				searchAndHighlightWord(keyword2.getText());
			} else if (keyword3 != null && keyword3.getText().equals(e.getActionCommand())) {
				searchAndHighlightWord(keyword3.getText());
			} else if (keyword4 != null && keyword4.getText().equals(e.getActionCommand())) {
				searchAndHighlightWord(keyword5.getText());
			} else if (keyword5 != null && keyword5.getText().equals(e.getActionCommand())) {
				searchAndHighlightWord(keyword5.getText());
			}
			break;
		}

	}

	/**
	 * to delete bookmark
	 * 
	 * @return int
	 */
	private void deletebookmark() {
		// TODO Auto-generated method stub
		JFrame delete = new JFrame("Delete");
		delete.setLayout(new GridLayout(2, 2));

		JLabel deleteLabel = new JLabel("Select Bookmark for deletion");
		JComboBox<String> combo = new JComboBox<>();

		Set<Entry<String, Integer>> bookmarkSet = bookmarkValue.entrySet();
		for (Entry<String, Integer> bookmark : bookmarkSet) {
			combo.addItem(bookmark.getKey());
		}

		JButton deleteBookMark = new JButton("Delete");
		JButton cancelDeletion = new JButton("Cancel");

		delete.add(deleteLabel);
		delete.add(combo);
		delete.add(deleteBookMark);
		delete.add(cancelDeletion);
		delete.setSize(200, 100);
		delete.setVisible(true);

		cancelDeletion.addActionListener(new CancelDeletionListener(delete));

		deleteBookMark.addActionListener(new DeleteBookmarkListener(combo, oldbookmarkMenu, textReaderArea));
	}

	/**
	 * Method for SaveAs Functionality in TextEditor
	 * 
	 * @return void
	 */
	private void saveAs() {
		JFileChooser saveAsChooser = new JFileChooser(System.getProperty(CURRENT_DIRECTORY));
		saveAsChooser.showSaveDialog(parent);
		if (saveAsChooser.getSelectedFile() != null) {
			try (FileWriter writer = new FileWriter(saveAsChooser.getSelectedFile());) {

				writer.write(textReaderArea.getText());
				fileNameLabel.setText(saveAsChooser.getSelectedFile().getName() + " Successfully Saved");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Reads the content of file
	 * 
	 * @param fileName
	 * @return String
	 * 
	 */
	private String readFileContents(String fileName) {
		StringBuilder fileContents = new StringBuilder("");

		if (fileName != null) {
			if (!(fileName.endsWith(EXTENSION_TEXT) || fileName.endsWith(EXTENSION_JAVA))) {
				fileName += DELIMITER_DOT + EXTENSION_TEXT;
			}

			try (Scanner scanner = new Scanner(new File(fileName))) {
				while (scanner.hasNextLine()) {
					fileContents.append(scanner.nextLine()).append(DELIMITER_NEW_LINE);
				}
				fileNameLabel.setText(MESSAGE_FILE_APPEND + fileName);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				fileContents.append(fileName + MESSAGE_FILE_NOT_FOUND);
			}
		}

		return fileContents.toString();
	}

	/**
	 * Opens general file browse for file selection
	 * 
	 * @return File
	 */
	private File browseFile() {
		JFileChooser fileChooser = new JFileChooser(new File(System.getProperty(CURRENT_DIRECTORY)));
		fileChooser.setFileFilter(new FileNameExtensionFilter(MESSAGE_FILE_CHOOSER, EXTENSION_TEXT, EXTENSION_JAVA));
		fileChooser.showOpenDialog(parent);
		return fileChooser.getSelectedFile();
	}

	/**
	 * Search specific word and highlight
	 * 
	 * @return boolean
	 */
	private boolean searchAndHighlightWord(String word) {
		boolean isWordFound = false;
		int count = 0;
		if (word != null && !word.isEmpty()) {
			Highlighter h = textReaderArea.getHighlighter();

			h.removeAllHighlights();

			int lastIndex = 0;

			String content = textReaderArea.getText();

			while ((lastIndex = content.indexOf(word, lastIndex)) != -1) {
				try {
					h.addHighlight(content.indexOf(word, lastIndex), content.indexOf(word, lastIndex) + word.length(),
							DefaultHighlighter.DefaultPainter);
					count++;
					isWordFound = true;
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				lastIndex += word.length() - 1;
			}
		}

		fileNameLabel.setText("Occurences:" + count);
		return isWordFound;

	}

	/**
	 * Get number of words in file
	 * 
	 * @return int
	 */
	private int getCountOfWords() {
		String content = textReaderArea.getText();

		String[] words = content.replaceAll(DELIMITER_NEW_LINE, DELIMITER_WHITE_SPACE).split(DELIMITER_WHITE_SPACE);
		int count = 0;
		for (String word : words) {
			if (word != null && !word.isEmpty())
				count++;
		}
		return count;
	}

	/**
	 * Get number of unique words in file
	 * 
	 * @return int
	 */
	private int getUniqueWordCount() {
		String content = textReaderArea.getText();

		String[] words = content.replaceAll(DELIMITER_NEW_LINE, DELIMITER_WHITE_SPACE).split(DELIMITER_WHITE_SPACE);
		List<String> listOfUniqueWords = new ArrayList<String>();

		for (String word : words) {
			if (word != null && !word.isEmpty() && !listOfUniqueWords.contains(word)) {
				listOfUniqueWords.add(word);
			}
		}
		return listOfUniqueWords.size();

	}

	private void addKeyWord(JMenuItem keyword) {
		keyword.addActionListener(actionAdapter);
		searchMenu.add(keyword);
	}

	/**
	 * Add bookmarks
	 * 
	 * @return void
	 */
	private void resetBookmark() {

		oldbookmarkMenu.removeAll();

		Set<Entry<String, Integer>> bookmarkSet = bookmarkValue.entrySet();

		for (Entry<String, Integer> bookmark : bookmarkSet) {
			String text = bookmark.getKey();

			JMenuItem bookmarkItem = new JMenuItem();
			bookmarkItem.setText(text);

			oldbookmarkMenu.add(bookmarkItem);

			bookmarkItem.addActionListener(new ResetBookmarkMenuListener(bookmarkItem, textReaderArea));
		}

	}

}
