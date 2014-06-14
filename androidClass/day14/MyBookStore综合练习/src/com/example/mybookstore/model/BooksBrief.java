package com.example.mybookstore.model;

public class BooksBrief {
	private long book_id;
	private String bookTitle;
	private String bookAuthor;
	private String bookPrice;
	private String bookArt;

	public long getBook_id() {
		return book_id;
	}

	public void setBook_id(long book) {
		this.book_id = book;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(String bookPrice) {
		this.bookPrice = bookPrice;
	}

	public String getBookArt() {
		return bookArt;
	}

	public void setBookArt(String bookArt) {
		this.bookArt = bookArt;
	}
}
