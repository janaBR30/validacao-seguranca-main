package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    @Autowired
    EventRepository repository;

    public Page<EventDTO> findAllPaged(Pageable pageable){
        Page<Event> eventos = repository.findAll(pageable);
        return eventos.map(x -> new EventDTO(x));
    }

    @Transactional
    public EventDTO insert(EventDTO dto){
        Event event = new Event();
        event.setName(dto.getName());
        event.setDate(dto.getDate());
        event.setUrl(dto.getUrl());
        event.setCity(new City(dto.getCityId(), null));

        event = repository.save(event);
        return new EventDTO(event);
    }
}
