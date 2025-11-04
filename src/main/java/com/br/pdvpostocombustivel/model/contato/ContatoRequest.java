package com.br.pdvpostocombustivel.model.contato;

public record ContatoRequest(
        String telefone,
        String email,
        String endereco
) {}
