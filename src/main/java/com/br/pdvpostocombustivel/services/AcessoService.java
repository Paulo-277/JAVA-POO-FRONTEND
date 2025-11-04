package com.br.pdvpostocombustivel.services;

import com.br.pdvpostocombustivel.model.acesso.AcessoRequest;
import com.br.pdvpostocombustivel.model.acesso.AcessoResponse;
import com.br.pdvpostocombustivel.model.PageableResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AcessoService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "http://localhost:8080/api/v1/acessos";

    public List<AcessoResponse> getAllAcessos() {
        ResponseEntity<PageableResponse<AcessoResponse>> response = restTemplate.exchange(
                API_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageableResponse<AcessoResponse>>() {}
        );
        return response.getBody().getContent();
    }

    public AcessoResponse createAcesso(AcessoRequest acessoRequest) {
        return restTemplate.postForObject(API_URL, acessoRequest, AcessoResponse.class);
    }

    public void updateAcesso(Long id, AcessoRequest acessoRequest) {
        restTemplate.put(API_URL + "/" + id, acessoRequest);
    }

    public void deleteAcesso(Long id) {
        restTemplate.delete(API_URL + "/" + id);
    }
}
