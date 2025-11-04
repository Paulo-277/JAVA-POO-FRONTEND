package com.br.pdvpostocombustivel.services;

import com.br.pdvpostocombustivel.model.contato.ContatoRequest;
import com.br.pdvpostocombustivel.model.contato.ContatoResponse;
import com.br.pdvpostocombustivel.model.PageableResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ContatoService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "http://localhost:8080/api/v1/contatos";

    public List<ContatoResponse> getAllContatos() {
        ResponseEntity<PageableResponse<ContatoResponse>> response = restTemplate.exchange(
                API_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageableResponse<ContatoResponse>>() {}
        );
        return response.getBody().getContent();
    }

    public ContatoResponse createContato(ContatoRequest contatoRequest) {
        return restTemplate.postForObject(API_URL, contatoRequest, ContatoResponse.class);
    }

    public void updateContato(Long id, ContatoRequest contatoRequest) {
        restTemplate.put(API_URL + "/" + id, contatoRequest);
    }

    public void deleteContato(Long id) {
        restTemplate.delete(API_URL + "/" + id);
    }
}
