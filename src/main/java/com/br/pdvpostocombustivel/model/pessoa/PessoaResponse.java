package com.br.pdvpostocombustivel.model.pessoa;

import com.br.pdvpostocombustivel.enums.TipoPessoa;

import java.time.LocalDate;

public record PessoaResponse(
        Long id,
        String nomeCompleto,
        String cpfCnpj,
        Long numeroCtps,
        LocalDate dataNascimento,
        TipoPessoa tipoPessoa
) {}
