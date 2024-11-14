package com.montonurb.screenmatch_backend.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.montonurb.screenmatch_backend.model.DadosTraducao;

public class ConsultaMyMemory {
    public static String obterTraducao(String text) throws UnsupportedEncodingException {
        ObjectMapper mapper = new ObjectMapper();
        ConsumoAPI consumo = new ConsumoAPI();
        String texto = URLEncoder.encode(text, "UTF-8");
        String langpair = URLEncoder.encode("en|pt-br", "UTF-8");
        String url = "https://api.mymemory.translated.net/get?q=" + texto + "&langpair=" + langpair;
        String json = consumo.obterDados(url);
        DadosTraducao traducao;
        
        try {
            traducao = mapper.readValue(json, DadosTraducao.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return traducao.dadosResposta().textoTraduzido();
    }
}
