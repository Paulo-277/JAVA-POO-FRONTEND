package com.br.pdvpostocombustivel.model.produto;

public record ProdutoRequest(
        String nome,
        String referencia,
        String fornecedor,
        String categoria,
        String marca
) {}
