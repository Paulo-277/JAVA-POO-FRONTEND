package com.br.pdvpostocombustivel.services;

import com.br.pdvpostocombustivel.model.pessoa.PessoaRequest;
import com.br.pdvpostocombustivel.model.pessoa.PessoaResponse;
import com.br.pdvpostocombustivel.model.PageableResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PessoaService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "http://localhost:8080/api/v1/pessoas";

    public List<PessoaResponse> getAllPessoas() {
        ResponseEntity<PageableResponse<PessoaResponse>> response = restTemplate.exchange(
                API_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageableResponse<PessoaResponse>>() {}
        );
        return response.getBody().getContent();
    }

    public PessoaResponse createPessoa(PessoaRequest pessoaRequest) {
        return restTemplate.postForObject(API_URL, pessoaRequest, PessoaResponse.class);
    }

    public void updatePessoa(Long id, PessoaRequest pessoaRequest) {
        restTemplate.put(API_URL + "/" + id, pessoaRequest);
    }

    public void deletePessoa(Long id) {
        restTemplate.delete(API_URL + "/" + id);
    }
}
