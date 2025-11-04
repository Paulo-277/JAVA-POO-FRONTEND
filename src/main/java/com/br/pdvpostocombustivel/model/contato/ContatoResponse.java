package com.br.pdvpostocombustivel.model.contato;

public record ContatoResponse(
        Long id,
        String telefone,
        String email,
        String endereco
) {}
