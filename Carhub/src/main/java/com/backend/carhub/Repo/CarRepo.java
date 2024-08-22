package com.backend.carhub.Repo;

import com.backend.carhub.Model.Car;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.function.Function;

public interface CarRepo extends JpaRepository<Car, Integer> {


}
