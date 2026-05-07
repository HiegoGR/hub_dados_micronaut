package com.dados.br.hub.repository;


import com.dados.br.hub.entity.JobEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface JobRepository extends CrudRepository<JobEntity, String> {

}
