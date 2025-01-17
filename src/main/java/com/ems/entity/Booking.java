package com.ems.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "booking_tbl")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Event event;

    @ManyToOne
    private User user;
    
	@CreationTimestamp
    private LocalDateTime bookingDateTime;

    private int ticketCount;

    private int totalAmount;

    @Enumerated(EnumType.STRING)
    private PaymentStatusType paymentStatus;
    
    @Column(unique=true)
	private String ticketId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getBookingDateTime() {
		return bookingDateTime;
	}

	public void setBookingDateTime(LocalDateTime bookingDateTime) {
		this.bookingDateTime = bookingDateTime;
	}

	public int getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(int numberOfTickets) {
		this.ticketCount = numberOfTickets;
	}

	public double getTotalAmount() {
		this.CalculateTotalAmount();
		return totalAmount;
	}

	public void setTotalAmount() {
		CalculateTotalAmount();
	}

	public PaymentStatusType getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatusType paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	public void setTicketId()
	{
		this.ticketId = "eventify@"+System.currentTimeMillis();
	}
	
	public String getTicketId()
	{
		return this.ticketId;
	}

	public void CalculateTotalAmount()
	{
		this.totalAmount=event.getTicketPrice()*this.ticketCount;
	}
	@Override
	public String toString() {
	    return "Booking{" +
	            "id=" + id +
	            ", event=" + (event != null ? event.getName() : "null") +
	            ", user=" + (user != null ? user.getUsername() : "null") +
	            ", bookingDateTime=" + bookingDateTime +
	            ", ticketCount=" + ticketCount +
	            ", totalAmount=" + totalAmount +
	            ", paymentStatus=" + paymentStatus +
	            ", ticketId='" + ticketId + '\'' +
	            '}';
	}

}

