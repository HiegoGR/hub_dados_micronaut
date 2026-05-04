package com.dados.br.hub.config;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Factory
public class ExecutorConfig {
    @Singleton
    @Named("enriquecimento-executor")
    public ExecutorService enriquecimentoExecutor() {
        return Executors.newFixedThreadPool(5);
    }
}
