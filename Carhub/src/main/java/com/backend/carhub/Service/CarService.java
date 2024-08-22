package com.backend.carhub.Service;

import com.backend.carhub.CarhubApplication;
import com.backend.carhub.Model.Car;
import com.backend.carhub.Repo.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.backend.carhub.Model.Car;
import com.backend.carhub.Model.Category;
import com.backend.carhub.Model.FuelType;
import com.backend.carhub.Repo.CarRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service

public class CarService {




    @Autowired
    private CarRepo carRepository;


   public String addCar(Car car){

       carRepository.save(car);

       return  "Successfully added";
   }


   public Car getCar (int id){
       Optional<Car> car = carRepository.findById(id);
       if(car.isPresent()){
           return car.get();
       }
       return null;
   }

   public List<Car> getAllCars(){
       return carRepository.findAll();
   }

   public Car updateCar(int id,Car car){
       if (carRepository.existsById(id)) {
           Car car1 = carRepository.findById(id).get();
           car1.setCarPhotos(car.getCarPhotos());
           car1.setFuel(car.getFuel());
           car1.setCapacity(car.getCapacity());
           car1.setUser(car.getUser());
           car1.setBrandName(car.getBrandName());
           car1.setModelName(car.getModelName());
           carRepository.save(car1);

           return car1;
       }
       else {
           return null;
       }

   }
   public String  deleteCar(int id){
       if(carRepository.existsById(id)){
           carRepository.deleteById(id);
           return  "Delete Successfully";
       }
       return "Not Deleted";

   }





}

