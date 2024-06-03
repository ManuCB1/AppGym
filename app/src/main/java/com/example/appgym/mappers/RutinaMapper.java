package com.example.appgym.mappers;

import com.example.appgym.model.Rutina;
import com.example.appgym.model.RutinaDTO;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RutinaMapper {
    RutinaMapper INSTANCE = Mappers.getMapper(RutinaMapper.class);

    RutinaDTO toDTO(Rutina rutina);
}
