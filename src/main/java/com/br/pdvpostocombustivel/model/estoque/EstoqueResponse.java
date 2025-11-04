package com.br.pdvpostocombustivel.model.estoque;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EstoqueResponse(
        Long id,
        BigDecimal quantidade,
        String localTanque,
        String localEndereco,
        String loteFabricacao,
        LocalDate dataValidade
) {}
