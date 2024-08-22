package com.backend.carhub.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentedCar {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RentCarId")
    private int RentCarId;


    private String modelName;

    private String brandName;

    private int capacity;


    @OneToMany(mappedBy = "rentedCar", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Review> reviews;

    @Enumerated(EnumType.STRING)
    private FuelType fuel;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private long price;

    @Lob
    @Column(name = "Photos", nullable = false ,length = 1000)
    private byte[] carPhotos;


    @OneToMany(mappedBy = "rentedCar", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Booking> bookings;
}
