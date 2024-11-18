package com.montonurb.screenmatch_backend.controller;

import com.montonurb.screenmatch_backend.dto.SerieDTO;
import com.montonurb.screenmatch_backend.model.Serie;
import com.montonurb.screenmatch_backend.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    SerieService serieService;

    @GetMapping
    public List<SerieDTO> obterSeries() {
        return serieService.buscarTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Series() {
        return serieService.buscar5MelhoresSeries();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos() {
        return serieService.buscarUltimosLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obterDadosSeriePorId( @PathVariable Long id) {
        return serieService.buscarSeriePorId(id);
    }
}
