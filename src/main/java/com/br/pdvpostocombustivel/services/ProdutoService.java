package com.br.pdvpostocombustivel.services;

import com.br.pdvpostocombustivel.model.PageableResponse;
import com.br.pdvpostocombustivel.model.produto.ProdutoRequest;
import com.br.pdvpostocombustivel.model.produto.ProdutoResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProdutoService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "http://localhost:8080/api/v1/produtos";

    public List<ProdutoResponse> getAllProdutos() {
        ResponseEntity<PageableResponse<ProdutoResponse>> response = restTemplate.exchange(
                API_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageableResponse<ProdutoResponse>>() {}
        );
        return response.getBody().getContent();
    }

    public ProdutoResponse createProduto(ProdutoRequest produtoRequest) {
        return restTemplate.postForObject(API_URL, produtoRequest, ProdutoResponse.class);
    }

    public void updateProduto(Long id, ProdutoRequest produtoRequest) {
        restTemplate.put(API_URL + "/" + id, produtoRequest);
    }

    public void deleteProduto(Long id) {
        restTemplate.delete(API_URL + "/" + id);
    }
}
