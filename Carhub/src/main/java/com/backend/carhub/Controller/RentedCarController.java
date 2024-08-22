package com.backend.carhub.Controller;


import com.backend.carhub.Model.Car;
import com.backend.carhub.Model.RentedCar;
import com.backend.carhub.Service.RentedCarService;
import com.backend.carhub.util.ImageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/rentcar")
@CrossOrigin("*")
public class RentedCarController {

    @Autowired
    private RentedCarService rentCarService;


    @Qualifier("objectMapper")
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/get")
    public RentedCar getRentedCar(@RequestParam int id) {
     return  rentCarService.findbyId(id);
    }


    @PostMapping("/add")
    public ResponseEntity<String> createCar(
            @RequestPart("car") String carJson,
            @RequestPart(value = "carPhoto", required = false) MultipartFile carPhoto) {

        System.out.println("Received car data: " + carJson);
        System.out.println("Received photo: " + (carPhoto != null ? carPhoto.getOriginalFilename() : "No file"));

        RentedCar car;
        try {
            car = objectMapper.readValue(carJson, RentedCar.class);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Invalid car data: " + e.getMessage());
        }

        if (carPhoto != null && !carPhoto.isEmpty()) {
            try {
                car.setCarPhotos(ImageUtils.compressImage(carPhoto.getBytes()));
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing photo");
            }
        }

        try {
            rentCarService.addRentCar(car);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving car");
        }

        return ResponseEntity.ok("Car added successfully");
    }



    @GetMapping("/getall")
    public List<RentedCar> getAllRentedCar() {


      List<RentedCar> cars =  rentCarService.getAllRentedCars();

      for(RentedCar car : cars) {

          car.setCarPhotos(ImageUtils.decompressImage(car.getCarPhotos()));
      }
      return cars;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRentedCar(@RequestParam int id) {
       return  rentCarService.deleteRentedCar(id);
    }


    @PutMapping("/update")
    public ResponseEntity<String> updateRentedCar(@RequestParam int id, @RequestBody RentedCar car) {
        return rentCarService.updateRentedCar(id,car);
    }



}
