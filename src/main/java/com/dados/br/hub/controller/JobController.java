package com.dados.br.hub.controller;

import com.dados.br.hub.dto.JobInfo;
import com.dados.br.hub.dto.JobResponse;
import com.dados.br.hub.dto.ResponseCompletoDto;
import com.dados.br.hub.service.JobService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.validation.Valid;

@Controller("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @Post("/enriquecimento")
    public HttpResponse<JobResponse> criarJob(@Body @Valid ResponseCompletoDto request) {
        JobResponse response = jobService.criarJob(request);
        return HttpResponse.accepted().body(response);
    }

    @Get("/{id}")
    public HttpResponse<JobInfo> buscarJob(@PathVariable String id) {
        JobInfo jobInfo = jobService.findById(id);
        return HttpResponse.ok(jobInfo);
    }
}
