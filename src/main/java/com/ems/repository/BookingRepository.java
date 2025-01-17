package com.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ems.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking,Integer>{

}
