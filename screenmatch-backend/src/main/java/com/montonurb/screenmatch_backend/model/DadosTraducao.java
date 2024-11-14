package com.montonurb.screenmatch_backend.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public record DadosTraducao(@JsonAlias(value="responseData") DadosResposta dadosResposta) {

}
