package com.xabe.spring.pulsar.consumer.infrastructure.presentation.mapper;

import com.xabe.spring.pulsar.consumer.domain.entity.CarDO;
import com.xabe.spring.pulsar.consumer.infrastructure.presentation.payload.CarPayload;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface CarDOMapper {

  CarPayload map(CarDO carDO);

}
