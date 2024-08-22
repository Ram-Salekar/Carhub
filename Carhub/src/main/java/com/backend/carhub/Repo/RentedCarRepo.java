package com.backend.carhub.Repo;

import com.backend.carhub.Model.RentedCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  RentedCarRepo extends JpaRepository<RentedCar, Integer> {
}
