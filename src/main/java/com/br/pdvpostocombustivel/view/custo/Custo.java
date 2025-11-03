package com.br.pdvpostocombustivel.view.custo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Custo {
    private JPanel panel1;
    private JTable tCusto;
    private JButton EDITARButton;
    private JButton EXCLUIRButton;
    private JButton ADICIONARButton;

    public Custo() {
        initComponents();
    }

    private void initComponents() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Imposto");
        model.addColumn("Custo Variável");
        model.addColumn("Custo Fixo");
        model.addColumn("Margem de Lucro");
        model.addColumn("Data de Processamento");

        model.addRow(new Object[]{1L, new BigDecimal("0.10"), new BigDecimal("1.50"), new BigDecimal("1000.00"), new BigDecimal("0.20"), LocalDate.now()});
        model.addRow(new Object[]{2L, new BigDecimal("0.12"), new BigDecimal("1.60"), new BigDecimal("1200.00"), new BigDecimal("0.22"), LocalDate.now()});

        tCusto.setModel(model);
    }

    public JPanel getCustoPanel() {
        return panel1;
    }
}
