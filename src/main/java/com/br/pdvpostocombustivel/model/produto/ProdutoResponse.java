package com.br.pdvpostocombustivel.model.produto;

public record ProdutoResponse(
        Long id,
        String nome,
        String referencia,
        String fornecedor,
        String categoria,
        String marca
) {}
