package com.xabe.spring.pulsar.producer.infrastructure.messaging.mapper;

import com.xabe.spring.pulsar.producer.domain.entity.CarDO;
import com.xabe.spring.pulsar.producer.infrastructure.messaging.dto.CarDTO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface CarDTOMapper {

  CarDTO map(CarDO carDO);
}
