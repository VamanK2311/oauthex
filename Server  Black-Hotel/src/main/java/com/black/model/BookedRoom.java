package com.black.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.naming.Name;
import java.time.LocalDate;

@Data
@AllArgsConstructor
//@NoArgsConstructor
@Entity
public class BookedRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column(name = "check_in")
    private LocalDate checkInDate;

    @Column(name = "check_out")
    private LocalDate checkOutDate;

    @Column(name = "guest_fullname")
    private String guestFullName;

    @Column(name = "guest_email")
    private String guestEmail;

    @Column(name = "adults")
    private int NumOfAdults;

    @Column(name = "children")
    private int NumOfChidren;

    @Column(name = "total_guest")
    private int totalNumOfGuest;

    @Column(name = "conformation_code")
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public BookedRoom(){}

    public void calculateTotalNumberOfGuest(){
        this.totalNumOfGuest = this.NumOfAdults + this.NumOfChidren;

    }

    public void setNumOfAdults(int numOfAdults) {
        NumOfAdults = numOfAdults;
        calculateTotalNumberOfGuest(); // when guest adds or remove each time it gone calculate.
    }

    public void setNumOfChidren(int numOfChidren) {
        NumOfChidren = numOfChidren;
        calculateTotalNumberOfGuest(); // when guest adds or remove each time it gone calculate.
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
