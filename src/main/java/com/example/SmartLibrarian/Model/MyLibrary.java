package com.example.SmartLibrarian.Model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

@Entity
public class MyLibrary {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinTable( name = "user_id", joinColumns = @JoinColumn (name = "id"))
	private UserMaster userId;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinTable( name = "book_id", joinColumns = @JoinColumn (name = "id"))
	private Books bookId;
	
	@Column(name = "purchase_datetime")
	private LocalDateTime purchaseDateTime;
	
	@Column(name = "renewal_datetime")
	private LocalDateTime renewalDateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserMaster getUserId() {
		return userId;
	}

	public void setUserId(UserMaster userId) {
		this.userId = userId;
	}

	public Books getBookId() {
		return bookId;
	}

	public void setBookId(Books bookId) {
		this.bookId = bookId;
	}

	public LocalDateTime getPurchaseDateTime() {
		return purchaseDateTime;
	}

	public void setPurchaseDateTime(LocalDateTime purchaseDateTime) {
		this.purchaseDateTime = purchaseDateTime;
	}

	public LocalDateTime getRenewalDateTime() {
		return renewalDateTime;
	}

	public void setRenewalDateTime(LocalDateTime renewalDateTime) {
		this.renewalDateTime = renewalDateTime;
	}

}
