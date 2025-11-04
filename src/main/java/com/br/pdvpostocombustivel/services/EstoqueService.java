package com.br.pdvpostocombustivel.services;

import com.br.pdvpostocombustivel.model.estoque.EstoqueRequest;
import com.br.pdvpostocombustivel.model.estoque.EstoqueResponse;
import com.br.pdvpostocombustivel.model.PageableResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EstoqueService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "http://localhost:8080/api/v1/estoques";

    public List<EstoqueResponse> getAllEstoques() {
        ResponseEntity<PageableResponse<EstoqueResponse>> response = restTemplate.exchange(
                API_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageableResponse<EstoqueResponse>>() {}
        );
        return response.getBody().getContent();
    }

    public EstoqueResponse createEstoque(EstoqueRequest estoqueRequest) {
        return restTemplate.postForObject(API_URL, estoqueRequest, EstoqueResponse.class);
    }

    public void updateEstoque(Long id, EstoqueRequest estoqueRequest) {
        restTemplate.put(API_URL + "/" + id, estoqueRequest);
    }

    public void deleteEstoque(Long id) {
        restTemplate.delete(API_URL + "/" + id);
    }
}
