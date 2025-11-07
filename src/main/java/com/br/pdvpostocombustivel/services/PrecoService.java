package com.br.pdvpostocombustivel.services;

import com.br.pdvpostocombustivel.model.PageableResponse;
import com.br.pdvpostocombustivel.model.preco.PrecoRequest;
import com.br.pdvpostocombustivel.model.preco.PrecoResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PrecoService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "http://localhost:8080/api/v1/precos";

    public List<PrecoResponse> getAllPrecos() {
        ResponseEntity<PageableResponse<PrecoResponse>> response = restTemplate.exchange(
                API_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageableResponse<PrecoResponse>>() {}
        );
        return response.getBody().getContent();
    }

    public PrecoResponse createPreco(PrecoRequest precoRequest) {
        return restTemplate.postForObject(API_URL, precoRequest, PrecoResponse.class);
    }

    public void updatePreco(Long id, PrecoRequest precoRequest) {
        restTemplate.put(API_URL + "/" + id, precoRequest);
    }

    public void deletePreco(Long id) {
        restTemplate.delete(API_URL + "/" + id);
    }
}
