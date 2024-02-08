package com.devsuperior.demo.mapper;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.Event;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class EventMapper {
    @Autowired
    private ModelMapper modelMapper;

    public EventDTO eventToEventDTO(Event event) {
        return modelMapper.map(event, EventDTO.class);
    }

    public Event eventDTOToEvent(EventDTO eventDTO) {
        return modelMapper.map(eventDTO, Event.class);
    }


    public void copyEventDTOToEvent(EventDTO eventDTO, Event event) {
        event.setId(eventDTO.getId());
        event.setName(eventDTO.getName());
        event.setUrl(eventDTO.getUrl());
        event.setDate(eventDTO.getDate());
    }
}
