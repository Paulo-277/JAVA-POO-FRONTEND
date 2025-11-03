package com.br.pdvpostocombustivel.view.pessoa;

import com.br.pdvpostocombustivel.enums.TipoPessoa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;

public class Pessoa {
    private JPanel pessoaPanel;
    private JTable tPessoa;
    private JButton EDITARButton;
    private JButton EXCLUIRButton;
    private JButton ADICIONARButton;

    public Pessoa() {
        initComponents();
    }

    private void initComponents() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nome Completo");
        model.addColumn("CPF/CNPJ");
        model.addColumn("Nº CTPS");
        model.addColumn("Data de Nascimento");
        model.addColumn("Tipo de Pessoa");

        model.addRow(new Object[]{1L, "João da Silva", "111.111.111-11", 1234567L, LocalDate.of(1990, 1, 1), TipoPessoa.FISICA});
        model.addRow(new Object[]{2L, "Empresa XYZ", "11.111.111/0001-11", null, LocalDate.of(2010, 5, 10), TipoPessoa.JURIDICA});

        tPessoa.setModel(model);
    }

    public JPanel getPessoaPanel() {
        return pessoaPanel;
    }
}
