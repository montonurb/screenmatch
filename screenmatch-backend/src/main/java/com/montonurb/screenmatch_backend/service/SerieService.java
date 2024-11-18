package com.montonurb.screenmatch_backend.service;

import com.montonurb.screenmatch_backend.dto.SerieDTO;
import com.montonurb.screenmatch_backend.model.Serie;
import com.montonurb.screenmatch_backend.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    SerieRepository serieRepository;

    public List<SerieDTO> buscarTodasAsSeries() {
        return converteDados(serieRepository.findAll());
    }

    public List<SerieDTO> buscar5MelhoresSeries() {
        return converteDados(serieRepository.findTop5ByOrderByAvaliacaoDesc());
    }

    public List<SerieDTO> buscarUltimosLancamentos() {
        return converteDados(serieRepository.buscarTop5EpisodiosPorDataLancamento());
    }

    private List<SerieDTO> converteDados(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPosters(), s.getSinopse()))
                .collect(Collectors.toList());
    }

    private SerieDTO converte(Serie serie) {
        return new SerieDTO(serie.getId(), serie.getTitulo(), serie.getTotalTemporadas(), serie.getAvaliacao(), serie.getGenero(), serie.getAtores(), serie.getPosters(), serie.getSinopse());
    }

    public SerieDTO buscarSeriePorId(Long id) {
        Optional<Serie> s = serieRepository.findById(id);
        return s.map(this::converte).orElse(null);
    }
}
