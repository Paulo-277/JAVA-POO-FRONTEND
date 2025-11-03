package com.br.pdvpostocombustivel.view.acesso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Acesso {
    private JPanel acessoPanel;
    private JTable tAcesso;
    private JButton addBut;
    private JButton editBut;
    private JButton apagBut;

    public Acesso() {
        initComponents();
    }

    private void initComponents() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Usuário");
        model.addColumn("Senha");

        model.addRow(new Object[]{1L, "admin", "admin123"});
        model.addRow(new Object[]{2L, "user", "user123"});

        tAcesso.setModel(model);
    }

    public JPanel getAcessoPanel() {
        return acessoPanel;
    }
}
