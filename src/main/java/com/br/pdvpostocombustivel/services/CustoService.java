package com.br.pdvpostocombustivel.services;

import com.br.pdvpostocombustivel.model.custo.CustoRequest;
import com.br.pdvpostocombustivel.model.custo.CustoResponse;
import com.br.pdvpostocombustivel.model.PageableResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CustoService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "http://localhost:8080/api/v1/custos";

    public List<CustoResponse> getAllCustos() {
        ResponseEntity<PageableResponse<CustoResponse>> response = restTemplate.exchange(
                API_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageableResponse<CustoResponse>>() {}
        );
        return response.getBody().getContent();
    }

    public CustoResponse createCusto(CustoRequest custoRequest) {
        return restTemplate.postForObject(API_URL, custoRequest, CustoResponse.class);
    }

    public void updateCusto(Long id, CustoRequest custoRequest) {
        restTemplate.put(API_URL + "/" + id, custoRequest);
    }

    public void deleteCusto(Long id) {
        restTemplate.delete(API_URL + "/" + id);
    }
}
