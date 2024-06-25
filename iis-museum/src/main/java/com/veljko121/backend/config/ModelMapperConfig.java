package com.veljko121.backend.config;

import java.util.ArrayList;
import java.util.Collection;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.veljko121.backend.dto.events.EventPictureResponseDTO;
import com.veljko121.backend.dto.events.EventRequestDTO;
import com.veljko121.backend.dto.events.EventUpdateRequestDTO;
import com.veljko121.backend.model.events.Event;
import com.veljko121.backend.model.events.EventPicture;

@Configuration
public class ModelMapperConfig {
    
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        // events
        Converter<Collection<String>, Collection<EventPicture>> picturePathStringsToEventPicture = new Converter<Collection<String>, Collection<EventPicture>>() {
            public Collection<EventPicture> convert(MappingContext<Collection<String>, Collection<EventPicture>> context) {
                Collection<String> picturePaths = context.getSource();
                Collection<EventPicture> eventPictures = new ArrayList<>();
                for (var picturePath : picturePaths) {
                    var eventPicture = new EventPicture();
                    eventPicture.setPath(picturePath);
                    eventPictures.add(eventPicture);
                }
                return eventPictures;
            }
        };
        Converter<Collection<EventPicture>, Collection<EventPictureResponseDTO>> eventPictureToPicturePathString = new Converter<Collection<EventPicture>, Collection<EventPictureResponseDTO>>() {
            public Collection<EventPictureResponseDTO> convert(MappingContext<Collection<EventPicture>, Collection<EventPictureResponseDTO>> context) {
                Collection<EventPicture> eventPictures = context.getSource();
                Collection<EventPictureResponseDTO> responseDTOs = new ArrayList<>();
                for (var eventPicture : eventPictures) {
                    var responseDTO = new EventPictureResponseDTO();
                    responseDTO.setId(eventPicture.getId());
                    responseDTO.setPath(eventPicture.getPath());
                    responseDTOs.add(responseDTO);
                }
                return responseDTOs;
            }
        };
        modelMapper.addConverter(picturePathStringsToEventPicture);
        modelMapper.addConverter(eventPictureToPicturePathString);
        modelMapper.typeMap(EventRequestDTO.class, Event.class).addMapping(src -> src.getRoomId(), (dest, value) -> dest.getRoomReservation().getRoom().setId((Integer)value));
        modelMapper.typeMap(EventRequestDTO.class, Event.class).addMappings(new PropertyMap<EventRequestDTO, Event>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
        modelMapper.typeMap(EventUpdateRequestDTO.class, Event.class).addMapping(src -> src.getRoomId(), (dest, value) -> dest.getRoomReservation().getRoom().setId((Integer)value));

        return modelMapper;
    }

}
