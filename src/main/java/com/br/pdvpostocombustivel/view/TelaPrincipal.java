package com.br.pdvpostocombustivel.view;

import com.br.pdvpostocombustivel.model.Pessoa;
import com.br.pdvpostocombustivel.enums.TipoPessoa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TelaPrincipal extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private List<Pessoa> pessoas = new ArrayList<>();
    private long nextId = 1;

    public TelaPrincipal() {
        setTitle("Cadastro de Pessoas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tabela
        String[] columnNames = {"ID", "Nome Completo", "CPF/CNPJ", "Data de Nascimento", "Tipo"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna a tabela não editável diretamente
            }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Adicionar");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Excluir");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Ações dos botões
        addButton.addActionListener(e -> adicionarPessoa());
        editButton.addActionListener(e -> editarPessoa());
        deleteButton.addActionListener(e -> excluirPessoa());

        // Carregar dados iniciais
        carregarDadosIniciais();
        atualizarTabela();
    }

    private void carregarDadosIniciais() {
        pessoas.add(new Pessoa(nextId++, "João da Silva", "111.222.333-44", 12345L, LocalDate.of(1990, 1, 15), TipoPessoa.FISICA));
        pessoas.add(new Pessoa(nextId++, "Maria Souza", "555.666.777-88", 67890L, LocalDate.of(1985, 5, 20), TipoPessoa.FISICA));
        pessoas.add(new Pessoa(nextId++, "Empresa XYZ Ltda", "12.345.678/0001-99", null, LocalDate.of(2010, 10, 1), TipoPessoa.JURIDICA));
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa a tabela
        for (Pessoa pessoa : pessoas) {
            tableModel.addRow(new Object[]{
                    pessoa.getId(),
                    pessoa.getNomeCompleto(),
                    pessoa.getCpfCnpj(),
                    pessoa.getDataNascimento(),
                    pessoa.getTipoPessoa()
            });
        }
    }

    private void adicionarPessoa() {
        PessoaFormDialog dialog = new PessoaFormDialog(this, null);
        dialog.setVisible(true);

        Optional<Pessoa> novaPessoaOpt = dialog.getPessoa();
        if (novaPessoaOpt.isPresent()) {
            Pessoa novaPessoa = novaPessoaOpt.get();
            novaPessoa.setId(nextId++);
            pessoas.add(novaPessoa);
            atualizarTabela();
        }
    }

    private void editarPessoa() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma pessoa para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) tableModel.getValueAt(selectedRow, 0);
        Pessoa pessoaSelecionada = pessoas.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);

        if (pessoaSelecionada != null) {
            PessoaFormDialog dialog = new PessoaFormDialog(this, pessoaSelecionada);
            dialog.setVisible(true);

            Optional<Pessoa> pessoaEditadaOpt = dialog.getPessoa();
            if (pessoaEditadaOpt.isPresent()) {
                Pessoa pessoaEditada = pessoaEditadaOpt.get();
                pessoaSelecionada.setNomeCompleto(pessoaEditada.getNomeCompleto());
                pessoaSelecionada.setCpfCnpj(pessoaEditada.getCpfCnpj());
                pessoaSelecionada.setNumeroCtps(pessoaEditada.getNumeroCtps());
                pessoaSelecionada.setDataNascimento(pessoaEditada.getDataNascimento());
                pessoaSelecionada.setTipoPessoa(pessoaEditada.getTipoPessoa());
                atualizarTabela();
            }
        }
    }

    private void excluirPessoa() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma pessoa para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir a pessoa selecionada?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            pessoas.removeIf(p -> p.getId().equals(id));
            atualizarTabela();
        }
    }
}
