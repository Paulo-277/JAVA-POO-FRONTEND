package com.br.pdvpostocombustivel.model.pessoa;

public record PessoaResponse(
        Long id,
        String nomeCompleto,
        String cpfCnpj,
        Long numeroCtps,
        String dataNascimento,
        String tipoPessoa
) {}
