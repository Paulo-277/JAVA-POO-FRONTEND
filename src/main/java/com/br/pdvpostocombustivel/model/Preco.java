package com.br.pdvpostocombustivel.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Preco {

    private Long id;
    private BigDecimal valor;
    private LocalDate dataAlteracao;
    private LocalDate horaAlteracao; // Mantido como LocalDate para consistência com a entidade

    public Preco(Long id, BigDecimal valor, LocalDate dataAlteracao, LocalDate horaAlteracao) {
        this.id = id;
        this.valor = valor;
        this.dataAlteracao = dataAlteracao;
        this.horaAlteracao = horaAlteracao;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDate dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public LocalDate getHoraAlteracao() {
        return horaAlteracao;
    }

    public void setHoraAlteracao(LocalDate horaAlteracao) {
        this.horaAlteracao = horaAlteracao;
    }
}
