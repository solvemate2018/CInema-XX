package com.future.cinemaxx.configuration;

import com.future.cinemaxx.dtos.converter.DTOConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    //Set matching strategy STRICT
    //otherwise it can generate the id when converting from DTO to entity.
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    public DTOConverter dtosConverter(ModelMapper modelMapper){
        DTOConverter dtOsConverter = new DTOConverter(modelMapper);
        return dtOsConverter;
    }
}