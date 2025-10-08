package com.br.pdvpostocombustivel.view.preco;

import com.br.pdvpostocombustivel.model.Preco;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrecoCrudFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private List<Preco> precos = new ArrayList<>();
    private long nextId = 1;

    public PrecoCrudFrame() {
        setTitle("Gerenciamento de Preços");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tabela
        String[] columnNames = {"ID", "Valor", "Data Alteração", "Hora Alteração"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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
        addButton.addActionListener(e -> adicionarPreco());
        editButton.addActionListener(e -> editarPreco());
        deleteButton.addActionListener(e -> excluirPreco());

        // Carregar dados iniciais
        carregarDadosIniciais();
        atualizarTabela();
    }

    private void carregarDadosIniciais() {
        precos.add(new Preco(nextId++, new BigDecimal("5.89"), LocalDate.of(2023, 10, 1), LocalDate.now()));
        precos.add(new Preco(nextId++, new BigDecimal("6.09"), LocalDate.of(2023, 10, 5), LocalDate.now()));
        precos.add(new Preco(nextId++, new BigDecimal("3.69"), LocalDate.of(2023, 10, 8), LocalDate.now()));
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        for (Preco preco : precos) {
            tableModel.addRow(new Object[]{
                    preco.getId(),
                    preco.getValor(),
                    preco.getDataAlteracao(),
                    preco.getHoraAlteracao()
            });
        }
    }

    private void adicionarPreco() {
        PrecoFormDialog dialog = new PrecoFormDialog(this, null);
        dialog.setVisible(true);

        Optional<Preco> novoPrecoOpt = dialog.getPreco();
        if (novoPrecoOpt.isPresent()) {
            Preco novoPreco = novoPrecoOpt.get();
            novoPreco.setId(nextId++);
            precos.add(novoPreco);
            atualizarTabela();
        }
    }

    private void editarPreco() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um preço para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) tableModel.getValueAt(selectedRow, 0);
        Preco precoSelecionado = precos.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);

        if (precoSelecionado != null) {
            PrecoFormDialog dialog = new PrecoFormDialog(this, precoSelecionado);
            dialog.setVisible(true);

            Optional<Preco> precoEditadoOpt = dialog.getPreco();
            if (precoEditadoOpt.isPresent()) {
                Preco precoEditado = precoEditadoOpt.get();
                precoSelecionado.setValor(precoEditado.getValor());
                precoSelecionado.setDataAlteracao(precoEditado.getDataAlteracao());
                precoSelecionado.setHoraAlteracao(precoEditado.getHoraAlteracao());
                atualizarTabela();
            }
        }
    }

    private void excluirPreco() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um preço para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o preço selecionado?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            precos.removeIf(p -> p.getId().equals(id));
            atualizarTabela();
        }
    }
}
