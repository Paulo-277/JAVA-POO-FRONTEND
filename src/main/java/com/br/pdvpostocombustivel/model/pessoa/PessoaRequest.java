package com.br.pdvpostocombustivel.model.pessoa;

public record PessoaRequest(
        String nomeCompleto,
        String cpfCnpj,
        Long numeroCtps,
        String dataNascimento,
        String tipoPessoa
) {}
