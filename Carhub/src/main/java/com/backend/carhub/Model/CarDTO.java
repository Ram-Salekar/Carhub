package com.backend.carhub.Model;


import lombok.Data;

@Data
public class CarDTO {
    private String modelName;
    private String brandName;
    private int capacity;
    private String dateOfPurchase;
    private FuelType fuel;
    private Category category;
    private long price;


}
