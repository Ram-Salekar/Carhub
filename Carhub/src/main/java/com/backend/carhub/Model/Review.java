package com.backend.carhub.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private  int ReviewID;

    @Column(name = "content")
    private String content;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_ref_id")
    private User user;

    @Column(name = "rating")
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private RentedCar rentedCar;
}
