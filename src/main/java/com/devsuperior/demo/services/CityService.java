package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {

    @Autowired
    CityRepository repository;

    @Transactional(readOnly = true)
    public List<CityDTO> findAll(){
        List<City> cities = repository.findAll(Sort.by("name"));
        return cities.stream().map(x -> new CityDTO(x)).toList();
    }

    @Transactional
    public CityDTO insert(CityDTO dto){
        City city = new City();
        city.setName(dto.getName());
        city = repository.save(city);
        return new CityDTO(city);
    }

}
