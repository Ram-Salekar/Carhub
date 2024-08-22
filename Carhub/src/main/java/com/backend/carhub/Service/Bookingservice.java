package com.backend.carhub.Service;

import com.backend.carhub.Model.Booking;
import com.backend.carhub.Repo.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Bookingservice {


    @Autowired
    private BookingRepo bookingRepo;


    public String addBooking(Booking booking) {

        return null;
    }
}
