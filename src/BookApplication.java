import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

/**
 * BookApplication class. Assignment 2, COIT23001.
 * Created by Andrew U. (SID: s0253796) on 19/08/2014.
 * String constants filePathRead and filePathSave are need to be changed for Windows.
 */
public class BookApplication extends JFrame {
	//declaring constants for window height and width
	private final int WINDOW_HEIGHT=600;
	private final int WINDOW_WIDTH=950;

	//path to files. read data from, and save data to
	//need to be changed for Windows
	private final String filePathRead = "/Users/snegpx/IdeaProjects/BookData.txt";
	private final String filePathSave = "/Users/snegpx/IdeaProjects/NewBookData.txt";

	//array of chars for shortcuts; is not necessary
	private char[] shortcuts= {'R','S','D','E','W','F'};

	//declaring variables and menu items
	private List<Book> bookData;
	private File file;
	private Book book;
	//private JFileChooser fileChooser;

	private JMenuBar menuBar;
	private JMenu readFileMenu;
	private JMenu saveFileMenu;
	private JMenu displayDataMenu;
	private JMenu sortDataMenu;
	private JMenu searchDataMenu;
	private JMenu helpMenu;

	private JMenuItem readFromFile;
	private JMenuItem saveFile;
	private JMenuItem displayData;
	private JMenuItem sortData;
	private JMenuItem searchData;
	private JMenuItem help;
	private JMenuItem exit;

	private JTextArea displayArea;
	private JScrollPane scroller;

	public BookApplication(){
		initUI();
	}

	/**
	 * Method to initialise GUI
	 */
	private void initUI(){
		//window properties
		setTitle("My Online Book Store");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
		setLayout(new BorderLayout());

		menuBar = new JMenuBar();

		//display area properties
		displayArea = new JTextArea();
		displayArea.setLineWrap(true);
		displayArea.setWrapStyleWord(true);

		//scroller
		scroller = new JScrollPane(displayArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		getContentPane().add(scroller, BorderLayout.CENTER);
		displayArea.setBorder(BorderFactory.createTitledBorder("Display Area for Books"));


		//create menu
		readFileMenu = new JMenu("Read File");
		saveFileMenu = new JMenu("Save File");
		displayDataMenu = new JMenu("Display Data");
		sortDataMenu = new JMenu("Sort Data");
		searchDataMenu = new JMenu("Search Data");
		helpMenu = new JMenu("Help");

		//create menu items
		readFromFile = new JMenuItem("Read");
		//creating shortcut for menu item "Read"
		readFromFile.setAccelerator(KeyStroke.getKeyStroke(shortcuts[0],
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),false));

		saveFile = new JMenuItem("Save");
		//shortcut for "Save"
		saveFile.setAccelerator(KeyStroke.getKeyStroke(shortcuts[1],
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));

		displayData = new JMenuItem("Display");
		//shortcut for "Display"
		displayData.setAccelerator(KeyStroke.getKeyStroke(shortcuts[2],
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));

		sortData = new JMenuItem("Sort");
		//shortcut for "Sort"
		sortData.setAccelerator(KeyStroke.getKeyStroke(shortcuts[4],
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));

		searchData = new JMenuItem("Search");
		//shortcut for "Search"
		searchData.setAccelerator(KeyStroke.getKeyStroke(shortcuts[5],
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));

		help = new JMenuItem("Help");

		exit = new JMenuItem("Exit");
		//shortcut for "Exit" menu item
		exit.setAccelerator(KeyStroke.getKeyStroke(shortcuts[3],
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),false));

		//add menu items to menu
		saveFileMenu.add(saveFile);
		readFileMenu.add(readFromFile);
		displayDataMenu.add(displayData);
		sortDataMenu.add(sortData);
		searchDataMenu.add(searchData);
		helpMenu.add(help);
		helpMenu.insertSeparator(1);
		helpMenu.add(exit);

		//add to components to menu bar
		menuBar.add(readFileMenu);
		menuBar.add(saveFileMenu);
		menuBar.add(displayDataMenu);
		menuBar.add(sortDataMenu);
		menuBar.add(searchDataMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);

		//assigning action listeners to menu items
		readFromFile.addActionListener(new ReadFileListener());
		saveFile.addActionListener(new SaveFileListener());
		displayData.addActionListener(new DisplayDataListener());
		sortData.addActionListener(new SortDataListener());
		searchData.addActionListener(new SearchDataListener());
		help.addActionListener(new HelpListener());
		exit.addActionListener(new ExitListener());


	}

	/**
	 * Allow user to choose file (assume that this file named BookData.txt and formatted in a proper way)
	 * and stores the information in an ArrayList <bookData>
	 *
	 * @throws IOException; checking for fileNotFoundException is made in actionListiner. However,
	 *         we just got the file from fileChooser, so it's hard to believe there is a problem, but...
	 */
	private void readFromFile() throws IOException {
		/*fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(BookApplication.this);
		file = fileChooser.getSelectedFile();*/
		file = new File(filePathRead);
		//Creating Scanner instance to read File in Java
		Scanner scanner = new Scanner(file);
		bookData = new ArrayList<>();
		//reading from file loop
		while (scanner.hasNextLine()) {
			String[] s = scanner.nextLine().split(","); //delimiter
			book = new Book();
			//adding title
			book.setBookTitle(String.valueOf(s[0]).trim());
			//isbnNumber
			book.setIsbnNumber(String.valueOf(s[1]).trim());
			//authorName
			book.setAuthorName(String.valueOf(s[2]).trim());
			//bookPrice
			book.setBookPrice(Double.valueOf(s[3]));
			bookData.add(book);
		}
	}

	/**
	 * Allows user to choose place where to save file (the name of file can be anything
	 * but we assume that it should be named as NewBookData.txt) in a certain pattern
	 * and displays total number of books at the end of file.
	 *
	 * @param list list from which the information retrieved
	 *             FileNotFoundException we just got the file from fileChooser,
	 *             so it's hard to believe there is a problem, but...
	 */
	private void saveToFile(List<Book> list) {
		try {
			/*fileChooser = new JFileChooser();
			fileChooser.showSaveDialog(BookApplication.this);
			file = fileChooser.getSelectedFile();
			//checks for file existence and display warning message
			if(file.exists()){
				Object[] options = {"Yes","No","Cancel"};
				int n = JOptionPane.showOptionDialog(null,"File already exists.\n"
						+"Overwrite existing file?", "Warning",JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE,null,options,options[0]);
				if(n == 0){
					try{
						file.delete();
					}
					catch(Exception e){
						JOptionPane.showMessageDialog(null, "Error deleting file.\nClose the file if it is open.");
						return;
					}
				}
				else{
					return;
				}
			}*/
			PrintWriter writer = new PrintWriter(filePathSave);
			//creating writer and file path where our info should be saved
			for (Book book : list){
				//formatting the output
				writer.print(String.format("%1$-30s",book.getAuthorName()));
				writer.print(String.format("%1$-44s",book.getBookTitle()));
				writer.print(String.format("%1$-6s",book.getBookPrice()));
				writer.println();
			}
			writer.println("Total Books: "+bookData.size());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	private void displayDataToTextArea (List<Book> list){
		displayArea.setFont(new Font("Courier New", Font.PLAIN, 12));
		displayArea.append(String.format("%1$-50s", "Book Title"));
		displayArea.append(String.format("%1$-30s", "Book ISBN"));
		displayArea.append(String.format("%1$-30s", "Book Author"));
		displayArea.append(String.valueOf("Book Price"));
		displayArea.append("\r\n");
		displayArea.append(String.format("%1$130s", "=").replace(' ', '='));
		displayArea.append("\r\n");
		for (Book book : list) {
			displayArea.append(String.format("%1$-50s", book.getBookTitle()));
			displayArea.append(String.format("%1$-30s", book.getIsbnNumber()));
			displayArea.append(String.format("%1$-30s", book.getAuthorName()));
			displayArea.append(String.valueOf(book.getBookPrice()));
			displayArea.append("\r\n");
		}
		displayArea.append(String.format("%1$130s", "=").replace(' ', '='));
		displayArea.append("\r\n");
	}

	/**
	 * Method sorts books in ascending order
	 * In fact it is a merge sort, as well as it is the best algorithm covered in course COIT23001.
	 * There is no optimisation. However I use recursion.
	 *
	 * @param list list that should be sorted.
	 * @param low the first index of that list.
	 * @param high the last index of that list.
	 * @param comparator comparator whether BOOK_TITLE_COMPARATOR or BOOK_PRICE_COMPARATOR.
	 */
	private void mergeSort(List<Book> list, int low, int high, Comparator<Book> comparator)
	{
		if(low < high)
		{
			//find the middle of the list
			int middle = low + (high - low) / 2;
			//recursive call
			mergeSort(list, low, middle, comparator);
			mergeSort(list, middle+1, high, comparator);
			merge(list, low, middle, high, comparator);
		}
	}

	/**
	 * Method merges two lists together in ascending order.
	 *
	 * @param list list that should be sorted.
	 * @param low the first index of that list.
	 * @param middle the middle index of the list.
	 * @param high the last index of that list.
	 * @param comparator comparator whether BOOK_TITLE_COMPARATOR or BOOK_PRICE_COMPARATOR.
	 */
	private void merge(List<Book> list, int low, int middle, int high, Comparator<Book> comparator)
	{
		List<Book> left = new ArrayList<>();
		List<Book> right = new ArrayList<>();

		for (int i = low; i <= middle; i++) {
			left.add(list.get(i));
		}

		for (int j = middle + 1; j <= high; j++) {
			right.add(list.get(j));
		}

		int leftIndex = 0;
		int rightIndex = 0;

		for (int k = low; k <= high; k++) {
			if (leftIndex < left.size() && rightIndex < right.size()) {
				if (comparator.compare(left.get(leftIndex), right.get(rightIndex)) > 0) {
					list.set(k, right.get(rightIndex));
					rightIndex++;
				} else {
					list.set(k, left.get(leftIndex));
					leftIndex++;
				}
			} else if (rightIndex < right.size()) {
				list.set(k, right.get(rightIndex));
				rightIndex++;
			} else {
				list.set(k, left.get(leftIndex));
				leftIndex++;
			}
		}
	}

	/**
	 * Search books by price using binary search algorithm,
	 * as it is the best algorithm covered in COIT2300 course.
	 *
	 * @param list list where to search.
	 * @param key key that should be found / not found.
	 * @param comparator comparator; BOOK_PRICE_COMPARATOR, since we need to find  price of book.
	 * @return index if key was found; and -1 if not found.
	 */
    private  int binarySearchMethod(List<Book> list, Book key, Comparator<Book> comparator) {
	   int low = 0;
	   int high = list.size() - 1;
	   while (low <= high) {
			int mid = (low + high) / 2;
		    Book midValue = list.get(mid);
		    int cmpValue = comparator.compare(midValue, key);
		    if (cmpValue < 0)
				low = mid + 1;
		        else if (cmpValue > 0)
			        high = mid - 1;
		        else
			        return mid; //key found
	        }
	        return -1;  //key not found
    }

	/**
	 * Search for book price. Creates a dialog where user should enter the price, that he wants to look for.
	 * After list is sorted by price using BOOK_PRICE_COMPARATOR.
	 * Next, search method applied to that list and "entered number by a user" as a key.
	 * Displays message whether the entered number found or not.
	 */
	private void searchBookPrice () {
		String inputPrice = JOptionPane.showInputDialog(null, "Enter Book Price for searching:");
		try {
			Book book = new Book();
			book.setBookPrice(Double.parseDouble(inputPrice));
			List<Book> sortedDataByPrice = new ArrayList<>(bookData);
			mergeSort(sortedDataByPrice, 0, sortedDataByPrice.size() - 1, Book.BOOK_PRICE_COMPARATOR);
			long startTime = System.nanoTime(); //timing
			int index = binarySearchMethod(sortedDataByPrice, book, Book.BOOK_PRICE_COMPARATOR);
			long endTime = System.nanoTime(); //timing ends
			long duration = (endTime - startTime); //duration
			System.out.printf("Searching time for " + bookData.size() + " lines = " + duration + " nanoseconds\n");
			if (index >= 0) {
				displayArea.setText(null);
				displayArea.setFont(new Font("Courier New", Font.PLAIN, 12));
				displayArea.append(String.format("%1$-50s", "Book Title"));
				displayArea.append(String.format("%1$-30s", "Book ISBN"));
				displayArea.append(String.format("%1$-30s", "Book Author"));
				displayArea.append(String.valueOf("Book Price"));
				displayArea.append("\r\n");
				displayArea.append(String.format("%1$130s", "=").replace(' ', '='));
				displayArea.append("\r\n");
				displayArea.append(String.format("%1$-50s", sortedDataByPrice.get(index).getBookTitle()));
				displayArea.append(String.format("%1$-30s", sortedDataByPrice.get(index).getIsbnNumber()));
				displayArea.append(String.format("%1$-30s", sortedDataByPrice.get(index).getAuthorName()));
				displayArea.append(String.valueOf(sortedDataByPrice.get(index).getBookPrice()));
				displayArea.append("\r\n");
				displayArea.append(String.format("%1$130s", "=").replace(' ', '='));
				displayArea.append("\r\n");
				JOptionPane.showMessageDialog(null, "Found.");
			}
			if (index < 0) {
				if (Double.parseDouble(inputPrice)<1)
					JOptionPane.showMessageDialog(null, "Please enter the price >= 1$"
							,"Error!", JOptionPane.ERROR_MESSAGE);
				else JOptionPane.showMessageDialog(null, "Not found.");
			}
		}
		catch  (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Chars are not allowed. " +
					"\nPlease enter the number.","Error!", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Read from file action listener
	 * Checks for a file not found exception. If so displays appropriate message to user.
	 */
	private class ReadFileListener implements ActionListener{
		@Override //Annotation checks that the method is an override.
		public void actionPerformed(ActionEvent e) {
			displayArea.setText(null);
			try {
				readFromFile();
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "File not found!", "Warning!",JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * Save to file action listener.
	 */
	private class SaveFileListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			saveToFile(bookData);
		}
	}

	/**
	 * Display data from file to text area listener.
	 */
	private class DisplayDataListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			displayArea.setText(null);
			displayDataToTextArea(bookData);
		}
	}

	/**
	 * Sorting data listener.
	 * Creates a duplicate list of bookData to avoid the situation when user wants to
	 * display data from file again after sorting. Then uses mergeSort algorithm with BOOK_TITLE_COMPARATOR
	 * comparator to sort data in ascending order.
	 */
	private class SortDataListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			displayArea.setText(null);
			List<Book> sortedDataByTitle = new ArrayList<>(bookData);
			long startTime = System.nanoTime();
			mergeSort(sortedDataByTitle,0,sortedDataByTitle.size()-1, Book.BOOK_TITLE_COMPARATOR);
			long endTime = System.nanoTime();
			long duration = (endTime - startTime);
			System.out.printf("Sorting time for "+bookData.size() + " Books = "+duration+" nanoseconds\n");
			displayDataToTextArea(sortedDataByTitle);
		}
	}

	/**
	 * Listener that invokes method for searching an entered price.
	 */
	private class SearchDataListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			searchBookPrice();

		}
	}

	/**
	 * Help listener. Displays help info as an unordered list
	 * about program and contact details of creator.
	 */
	private class HelpListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "<html>How to use this program?" +
					"<ul><li><b>Read</b> - reads the data from file.</li>" +
					"<li><b>Save</b> - saves data to file.</li>" +
					"<li><b>Display</b> - displays the data in the display area.</li>" +
					"<li><b>Sort</b> - sorts data byt the book title in ascending order.</li>" +
					"<li><b>Search</b> - ask user to enter a book price and searches for give book price.</li>" +
					"<li><b>Help</b> - provides this dialog.</li>" +
					"<li><b>Exit</b> - exits the application.</li></ul>" +
					"<p><strong>For more info, pls contact:</strong></p>" +
					"<p>andrey.udodov@cqumail.com</p></html>",
					"About this program",JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Exit listener. Exits the application.
	 */
	private class ExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	/**
	 * Main method to invoke the application.
	 */
	public static void main(String...args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				BookApplication initGUI = new BookApplication();
				initGUI.setVisible(true);
			}
		});
	}
}