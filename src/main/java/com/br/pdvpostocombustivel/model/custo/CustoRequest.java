package com.br.pdvpostocombustivel.model.custo;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CustoRequest(
        BigDecimal imposto,
        BigDecimal custoVariavel,
        BigDecimal custoFixo,
        BigDecimal margemLucro,
        LocalDate dataProcessamento
) {}
