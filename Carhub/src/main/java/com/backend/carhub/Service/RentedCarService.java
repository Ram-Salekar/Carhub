package com.backend.carhub.Service;

import com.backend.carhub.Model.RentedCar;
import com.backend.carhub.Repo.RentedCarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentedCarService {
    @Autowired

    private RentedCarRepo repo;

    public String addRentCar (RentedCar car) {
        repo.save(car);

        return "Successfull";

    }
    public RentedCar findbyId (int id) {
        if(repo.existsById(id)){
            return repo.findById(id).get();
        }
        else{
            return null;
        }
    }
    public List<RentedCar> getAllRentedCars() {
        return repo.findAll();
    }


    public ResponseEntity<String> updateRentedCar(int id, RentedCar car) {
        if (repo.existsById(id)) {
            car.setRentCarId(id);
            repo.save(car);
            return ResponseEntity.ok("Successfull");
        } else {
            return ResponseEntity.ok("Not found");
        }
    }


    public ResponseEntity<String> deleteRentedCar(int id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.ok("Successfully deleted");
        } else {
            return ResponseEntity.ok("Car not found");
        }
    }
}
