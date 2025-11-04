package com.br.pdvpostocombustivel.model.custo;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CustoResponse(
        Long id,
        BigDecimal imposto,
        BigDecimal custoVariavel,
        BigDecimal custoFixo,
        BigDecimal margemLucro,
        LocalDate dataProcessamento
) {}
