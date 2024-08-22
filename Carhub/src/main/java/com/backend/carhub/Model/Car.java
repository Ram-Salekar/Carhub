package com.backend.carhub.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int carId;

    private String modelName;

    private String brandName;

    private int capacity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "purchase_date", nullable = false)
    private LocalDate dateOfPurchase;

    @Enumerated(EnumType.STRING)
    private FuelType fuel;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private long price;

    @Lob
    @Column(name = "Photos", nullable = false ,length = 1000)
    private byte[] carPhotos;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setCarPhoto(byte[] photo) {
        this.carPhotos = photo;
    }
}

