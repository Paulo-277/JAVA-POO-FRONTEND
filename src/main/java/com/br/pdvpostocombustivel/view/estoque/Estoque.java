package com.br.pdvpostocombustivel.view.estoque;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Estoque {
    private JPanel panel1;
    private JButton EDITARButton;
    private JButton EXCLUIRButton;
    private JButton ADICIONARButton;
    private JTable tEstoque;

    public Estoque() {
        initComponents();
    }

    private void initComponents() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Quantidade");
        model.addColumn("Local do Tanque");
        model.addColumn("Endereço do Local");
        model.addColumn("Lote de Fabricação");
        model.addColumn("Data de Validade");

        model.addRow(new Object[]{1L, new BigDecimal("10000.00"), "Tanque 01", "Posto A", "LOTE001", LocalDate.now().plusMonths(6)});
        model.addRow(new Object[]{2L, new BigDecimal("15000.00"), "Tanque 02", "Posto A", "LOTE002", LocalDate.now().plusMonths(6)});

        tEstoque.setModel(model);
    }

    public JPanel getEstoquePanel() {
        return panel1;
    }
}
