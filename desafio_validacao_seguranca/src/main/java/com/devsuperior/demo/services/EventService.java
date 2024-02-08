package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.mapper.EventMapper;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.repositories.EventRepository;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private EventMapper eventMapper;

    @Transactional(readOnly = true)
    public Page<EventDTO> findAllPageable(Pageable pageable) {
        return eventRepository.findAll(pageable).map(event -> eventMapper.eventToEventDTO(event));
    }

    @Transactional
    public EventDTO insert(EventDTO eventDTO) {
        return eventMapper.eventToEventDTO(eventRepository.save(eventMapper.eventDTOToEvent(eventDTO)));
    }
    
    @Transactional
    public EventDTO update(Long id, EventDTO eventDTO) {
        try {
            Event event = eventRepository.getReferenceById(id);
            City city = cityRepository.getReferenceById(eventDTO.getCityId());
            event.setCity(city);
            eventMapper.copyEventDTOToEvent(eventDTO, event);
            return eventMapper.eventToEventDTO(eventRepository.save(event));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Register not found");
        }
    }
}
