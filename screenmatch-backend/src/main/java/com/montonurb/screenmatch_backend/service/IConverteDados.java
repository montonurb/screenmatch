package com.montonurb.screenmatch_backend.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
