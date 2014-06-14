package com.example.mybookstore.model;


public class Books {
	private long book_id;
	private String bookTitle;
	private String bookAuthor;
	private long book_CatagoryId;
	private String bookDate;
	private String bookPrice;
	private long bookPages;
	private String bookArt;
	private String bookDescription;
	
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
	public long getBook_CatagoryId() {
		return book_CatagoryId;
	}
	public void setBook_CatagoryId(long bookCatagoryId) {
		this.book_CatagoryId = bookCatagoryId;
	}
	public String getBookDate() {
		return bookDate;
	}
	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}
	public String getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(String bookPrice) {
		this.bookPrice = bookPrice;
	}
	public long getBookPages() {
		return bookPages;
	}
	public void setBookPages(long bookPages) {
		this.bookPages = bookPages;
	}
	public String getBookArt() {
		return bookArt;
	}
	public void setBookArt(String bookArt) {
		this.bookArt = bookArt;
	}
	public String getBookDescription() {
		return bookDescription;
	}
	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}
}
