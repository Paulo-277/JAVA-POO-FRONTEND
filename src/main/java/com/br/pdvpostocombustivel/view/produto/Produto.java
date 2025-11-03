package com.br.pdvpostocombustivel.view.produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Produto {
    private JPanel panel1;
    private JButton EDITARButton;
    private JButton EXCLUIRButton;
    private JButton ADICIONARButton;
    private JTable tProduto;

    public Produto() {
        initComponents();
    }

    private void initComponents() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("Referência");
        model.addColumn("Fornecedor");
        model.addColumn("Categoria");
        model.addColumn("Marca");

        model.addRow(new Object[]{1L, "Gasolina Comum", "GC001", "Petrobras", "Combustível", "Petrobras"});
        model.addRow(new Object[]{2L, "Óleo Lubrificante", "OL001", "Ipiranga", "Lubrificante", "Ipiranga"});

        tProduto.setModel(model);
    }

    public JPanel getProdutoPanel() {
        return panel1;
    }
}
