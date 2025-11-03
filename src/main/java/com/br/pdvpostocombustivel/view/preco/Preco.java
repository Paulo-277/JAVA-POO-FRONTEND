package com.br.pdvpostocombustivel.view.preco;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Preco {
    private JPanel precoPanel;
    private JTable tPreco;
    private JButton EDITARButton;
    private JButton EXCLUIRButton;
    private JButton ACESSO;

    public Preco() {
        initComponents();
    }

    private void initComponents() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Valor");
        model.addColumn("Data de Alteração");
        model.addColumn("Hora de Alteração");

        model.addRow(new Object[]{1L, new BigDecimal("5.50"), LocalDate.now(), LocalTime.now()});
        model.addRow(new Object[]{2L, new BigDecimal("4.80"), LocalDate.now(), LocalTime.now()});

        tPreco.setModel(model);
    }

    public JPanel getPrecoPanel() {
        return precoPanel;
    }
}
