package com.heinz;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Text Reader window that sets up the UI elements .
 * Constitutes View part of the editor
 * words,searches words
 * 
 * @author shriram
 * 
 */
public class TextReaderWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String MENU_FILE = "File";
	private static final String MENU_TOOLS = "Tools";
	private static final String MENU_HELP = "Help";
	private static final String MENU_WORD = "Word";
	private static final String MENU_SEARCH = "Search";
	private static final String MENU_BOOKMARK = "Bookmark";
	
	public static final String BUTTON_OPEN = "Open";
	public static final String BUTTON_SAVE = "Save";
	public static final String BUTTON_SAVE_AS = "Save As";
	public static final String BUTTON_CLOSE = "Close";
	public static final String MENU_ITEM_EXIT = "Exit";
	public static final String MENU_ITEM_EDIT = "Edit";
	public static final String MENU_ITEM_ABOUT = "About";
	public static final String MENU_ITEM_COUNT = "Count";
	public static final String MENU_ITEM_UNIQUE = "Unique";
	public static final String MENU_ITEM_SEARCH_NEW = "Search New..";
	public static final String MENU_BOOKMARK_NEW = " New Bookmark";
	public static final String MENU_BOOKMARK_OLD = " old Bookmark";
	public static final String MENU_BOOKMARK_DELETE = " delete Bookmark";
	public static final String MENU_ITEM_PRINT = "Print";
	public static final String MENU_ITEM_SEARCH = "Search";
	public static final String MENU_ITEM_TRANSLATE = "Translate";
	public static final String MENU_ITEM_NEW_FEATURE = "New Feature";
	
	public static final Map<String,Integer> bookmarkValue = new TreeMap<String,Integer>();
	
	public static ActionListenerAdapter actionAdapter;
	

	/**
	 * @param title
	 *            Constructor of TextReaderWindow Class
	 */
	public TextReaderWindow(String title) {
		
		super(title);
		setVisible(false);
		setStyle();
		
		JPanel textPanel = new JPanel();
		
		JMenu searchMenu = new JMenu(MENU_SEARCH);
		JMenu bookmarkMenu = new JMenu(MENU_BOOKMARK);
		JMenu oldbookmarkMenu = new JMenu(MENU_BOOKMARK_OLD);
		JMenu fileMenu = new JMenu(MENU_FILE);
		JMenu toolsMenu = new JMenu(MENU_TOOLS);
		JMenu helpMenu = new JMenu(MENU_HELP);
		JMenu wordMenu = new JMenu(MENU_WORD);
		
		addComponents(textPanel, searchMenu, oldbookmarkMenu);
		add(textPanel);

		JMenuItem openItem = new JMenuItem(BUTTON_OPEN);
		JMenuItem saveItem = new JMenuItem(BUTTON_SAVE);
		JMenuItem saveAsItem = new JMenuItem(BUTTON_SAVE_AS);
		JMenuItem exitItem = new JMenuItem(MENU_ITEM_EXIT);
		JMenuItem editItem = new JMenuItem(MENU_ITEM_EDIT);
		JMenuItem aboutItem = new JMenuItem(MENU_ITEM_ABOUT);
		JMenuItem countItem = new JMenuItem(MENU_ITEM_COUNT);
		JMenuItem uniqueItem = new JMenuItem(MENU_ITEM_UNIQUE);
		JMenuItem searchNewItem = new JMenuItem(MENU_ITEM_SEARCH_NEW);
		JMenuItem closeItem = new JMenuItem(BUTTON_CLOSE);
		JMenuItem newbookItem = new JMenuItem(MENU_BOOKMARK_NEW);
		JMenuItem deletebookItem = new JMenuItem(MENU_BOOKMARK_DELETE);
		JMenuItem printItem = new JMenuItem(MENU_ITEM_PRINT);
		JMenuItem mailItem = new JMenuItem(MENU_ITEM_SEARCH);
		JMenuItem translateItem = new JMenuItem(MENU_ITEM_TRANSLATE);
		JMenuItem newFeatureItem = new JMenuItem(MENU_ITEM_NEW_FEATURE);
		

		wordMenu.add(countItem);
		wordMenu.add(uniqueItem);
		wordMenu.add(searchMenu);
		
		searchMenu.add(searchNewItem);
		
		bookmarkMenu.add(newbookItem);
		bookmarkMenu.add(oldbookmarkMenu);
		bookmarkMenu.add(deletebookItem);
		
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		fileMenu.add(printItem);
		fileMenu.add(closeItem);
		fileMenu.add(exitItem);
		
		helpMenu.add(aboutItem);
		helpMenu.add(newFeatureItem);
		
		toolsMenu.add(wordMenu);
		toolsMenu.add(bookmarkMenu);
		toolsMenu.add(mailItem);
		toolsMenu.add(translateItem);
		
		closeItem.addActionListener(actionAdapter);
		exitItem.addActionListener(actionAdapter);
		editItem.addActionListener(actionAdapter);
		openItem.addActionListener(actionAdapter);
		saveItem.addActionListener(actionAdapter);
		saveAsItem.addActionListener(actionAdapter);
		aboutItem.addActionListener(actionAdapter);
		countItem.addActionListener(actionAdapter);
		uniqueItem.addActionListener(actionAdapter);
		printItem.addActionListener(actionAdapter);
		searchNewItem.addActionListener(actionAdapter);
		mailItem.addActionListener(actionAdapter);
		newbookItem.addActionListener(actionAdapter);
		deletebookItem.addActionListener(actionAdapter);
		translateItem.addActionListener(actionAdapter);
		newFeatureItem.addActionListener(actionAdapter);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(toolsMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);
		
		setVisible(true);
	}

	/**
	 * Sets style for Text Editor (JFrame)
	 * 
	 * @param
	 * @return void
	 */
	private void setStyle() {
		Dimension frameDimension = new Dimension(550, 450);
		setLayout(new BorderLayout());
		setSize(frameDimension);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Add components-TextArea,Scroll
	 * 
	 * @param bookmarkMenu
	 * @param JPanel
	 * @return void
	 */
	private void addComponents(JPanel textPanel, JMenu searchMenu, JMenu bookmarkMenu) {

		JLabel fileNameLabel = new JLabel("");

		JTextArea textReaderArea = new JTextArea(23, 48);
		
		JScrollPane scrollPane = new JScrollPane(textReaderArea);
		scrollPane.setWheelScrollingEnabled(true);
		scrollPane.setAutoscrolls(true);
		
		textReaderArea.setFont(new Font("Arial", Font.ITALIC, 13));
		textReaderArea.setForeground(Color.blue);
		textReaderArea.setSelectedTextColor(Color.DARK_GRAY);
		textReaderArea.setSize(new Dimension(10, 200));
		textReaderArea.setLineWrap(true);
		textReaderArea.setCaretPosition(0);
		textReaderArea.setVisible(true);

		textPanel.add(scrollPane, 0);
		textPanel.add(fileNameLabel);

		fileNameLabel.setVisible(true);
		actionAdapter = new ActionListenerAdapter(this, textReaderArea, searchMenu, bookmarkMenu, fileNameLabel);
	}
}

	


