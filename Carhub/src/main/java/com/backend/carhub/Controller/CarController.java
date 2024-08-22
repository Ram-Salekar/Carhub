package com.backend.carhub.Controller;

import com.backend.carhub.Model.Car;
import com.backend.carhub.Repo.CarRepo;
import com.backend.carhub.Service.CarService;
import com.backend.carhub.util.ImageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
@CrossOrigin("*")
public class CarController {

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private CarService carService;

    @Qualifier("objectMapper")
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/add")
    public ResponseEntity<String> createCar(
            @RequestPart("car") String carJson,
            @RequestPart(value = "carPhoto", required = false) MultipartFile carPhoto) {

        System.out.println("Received car data: " + carJson);
        System.out.println("Received photo: " + (carPhoto != null ? carPhoto.getOriginalFilename() : "No file"));

        Car car;
        try {
            car = objectMapper.readValue(carJson, Car.class);
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
            carRepo.save(car);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving car");
        }

        return ResponseEntity.ok("Car added successfully");
    }

    @GetMapping("/find")
    public Car findbyId(@RequestParam int id) {

       Car c =  carService.getCar(id);
        byte[] p = ImageUtils.decompressImage(c.getCarPhotos());
        c.setCarPhotos(p);

       return c;
    }
    @GetMapping("/getall")
    public ResponseEntity<List<Car>> getAllCars() {

        List<Car> cars = carService.getAllCars();
        for(Car c : cars) {
           byte[] p = ImageUtils.decompressImage(c.getCarPhotos());
           c.setCarPhotos(p);

        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(cars);
    }





    @PutMapping("/update")
    public Car updateCar(@RequestParam int id, @RequestBody Car car) {
        return carService.updateCar(id, car);
    }

    @DeleteMapping("/delete")
    public String deleteCar(@RequestParam int id) {
        return carService.deleteCar(id);
    }
}







