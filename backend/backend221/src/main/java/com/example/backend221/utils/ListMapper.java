package com.example.backend221.utils;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Collectors;

public class ListMapper {
    private static final ListMapper listMapper = new ListMapper();
    private ListMapper() { }
    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass, ModelMapper modelMapper) {
        return source.stream().map(entity -> modelMapper.map(entity, targetClass))
                .collect(Collectors.toList());
    }
    public static ListMapper getInstance() {
        return listMapper;
    }

}