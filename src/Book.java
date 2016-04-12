import java.util.Comparator;

/**
 * Book class. Assignment 2, COIT23001.
 * 2 comparators: BOOK_TITLE_COMPARATOR - compares two books by title;
 *                BOOK_PRICE_COMPARATOR - compares two books by price (for searching).
 * Set and Get methods.
 * Created by Andrew U. (SID: s0253796) on 19/08/2014.
 */
public class Book {

	public static BookTittleComparator BOOK_TITLE_COMPARATOR = new BookTittleComparator();
	public static BookPriceComparator BOOK_PRICE_COMPARATOR = new BookPriceComparator();

	private String bookTitle;
	private String isbnNumber;
	private String authorName;
	private Double bookPrice;

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public void setIsbnNumber(String isbnNumber) {
		this.isbnNumber = isbnNumber;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public void setBookPrice(Double bookPrice) {
		this.bookPrice = bookPrice;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public String getIsbnNumber() {
		return isbnNumber;
	}

	public String getAuthorName() {
		return authorName;
	}

	public Double getBookPrice() {
		return bookPrice;
	}

	private static class BookTittleComparator implements Comparator<Book> {
		@Override
		public int compare(Book firstBook, Book secondBook) {
			return firstBook.bookTitle.compareTo(secondBook.bookTitle);
		}
	}

	private static class BookPriceComparator implements Comparator<Book> {
		@Override
		public int compare(Book firstBook, Book secondBook) {
			return Double.compare(firstBook.bookPrice, secondBook.bookPrice);
		}
	}
}
