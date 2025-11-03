package com.br.pdvpostocombustivel.view.contato;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Contato {
    private JPanel panel1;
    private JButton ADICIONARButton;
    private JButton EDITARButton;
    private JButton EXCLUIRButton;
    private JTable tContato;

    public Contato() {
        initComponents();
    }

    private void initComponents() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Telefone");
        model.addColumn("Email");
        model.addColumn("Endereço");

        model.addRow(new Object[]{1L, "(11) 99999-9999", "cliente1@email.com", "Rua A, 123"});
        model.addRow(new Object[]{2L, "(22) 88888-8888", "cliente2@email.com", "Rua B, 456"});

        tContato.setModel(model);
    }

    public JPanel getContatoPanel() {
        return panel1;
    }
}
