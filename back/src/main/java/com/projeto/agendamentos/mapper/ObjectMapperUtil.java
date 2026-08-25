package com.projeto.agendamentos.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ObjectMapperUtil {

    private static ModelMapper modelMapper;

    public ObjectMapperUtil() {
        ObjectMapperUtil.modelMapper = new ModelMapper();
        // Configuração opcional para ignorar valores nulos ou ajustar correspondência rígida
        ObjectMapperUtil.modelMapper.getConfiguration().setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);
    }

    // Mapeia Entidade -> Response OU Request -> Entidade
    public static <S, T> T mapTo(S source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        return modelMapper.map(source, targetClass);
    }

    // Mapeia listas genéricas para Lista de Response ou Lista de Entidade
    public static <S, T> List<T> mapListTo(List<S> sourceList, Class<T> targetClass) {
        return sourceList.stream()
                .map(element -> mapTo(element, targetClass))
                .collect(Collectors.toList());
    }
}