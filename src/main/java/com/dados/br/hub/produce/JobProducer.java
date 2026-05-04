package com.dados.br.hub.produce;

import com.dados.br.hub.dto.ConsultaErroEvent;
import com.dados.br.hub.dto.ConsultaProcessadaEvent;
import com.dados.br.hub.dto.ConsultaSolicitadaEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface JobProducer {

    @Topic("job.recebido")
    void enviarJobRecebido(ConsultaSolicitadaEvent event);

    @Topic("job.processado")
    void enviarJobProcessado(ConsultaProcessadaEvent event);

    @Topic("job.erro")
    void enviarJobErro(ConsultaErroEvent event);

}
