package com.dados.br.hub.consumer;

import com.dados.br.hub.dto.ConsultaSolicitadaEvent;
import com.dados.br.hub.service.ProcessamentoDadosApisExternas;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaListener(groupId = "hub-enriquecimento-group")
public class JobConsumer {

    private final ProcessamentoDadosApisExternas processamentoDadosApisExternas;

    public JobConsumer(ProcessamentoDadosApisExternas processamentoDadosApisExternas) {
        this.processamentoDadosApisExternas = processamentoDadosApisExternas;
    }

    @Topic("job.recebido")
    public void receber(ConsultaSolicitadaEvent event) {
        processamentoDadosApisExternas.processarDados(event);
    }

}
