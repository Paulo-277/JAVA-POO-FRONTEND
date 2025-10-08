package com.br.pdvpostocombustivel.view.estoque;

import com.br.pdvpostocombustivel.model.Estoque;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EstoqueCrudFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private List<Estoque> estoques = new ArrayList<>();
    private long nextId = 1;

    public EstoqueCrudFrame() {
        setTitle("Gerenciamento de Estoque");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tabela
        String[] columnNames = {"ID", "Quantidade", "Local (Tanque)", "Endereço", "Lote", "Validade"};
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
        addButton.addActionListener(e -> adicionarEstoque());
        editButton.addActionListener(e -> editarEstoque());
        deleteButton.addActionListener(e -> excluirEstoque());

        // Carregar dados iniciais
        carregarDadosIniciais();
        atualizarTabela();
    }

    private void carregarDadosIniciais() {
        estoques.add(new Estoque(nextId++, new BigDecimal("10000.50"), "T-01", "Pátio A", "LT-GC-001", LocalDate.of(2025, 12, 31)));
        estoques.add(new Estoque(nextId++, new BigDecimal("8500.00"), "T-02", "Pátio A", "LT-GA-002", LocalDate.of(2025, 11, 30)));
        estoques.add(new Estoque(nextId++, new BigDecimal("12000.75"), "T-03", "Pátio B", "LT-ET-003", LocalDate.of(2026, 1, 15)));
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        for (Estoque estoque : estoques) {
            tableModel.addRow(new Object[]{
                    estoque.getId(),
                    estoque.getQuantidade(),
                    estoque.getLocalTanque(),
                    estoque.getLocalEndereco(),
                    estoque.getLoteFabricacao(),
                    estoque.getDataValidade()
            });
        }
    }

    private void adicionarEstoque() {
        EstoqueFormDialog dialog = new EstoqueFormDialog(this, null);
        dialog.setVisible(true);

        Optional<Estoque> novoEstoqueOpt = dialog.getEstoque();
        if (novoEstoqueOpt.isPresent()) {
            Estoque novoEstoque = novoEstoqueOpt.get();
            novoEstoque.setId(nextId++);
            estoques.add(novoEstoque);
            atualizarTabela();
        }
    }

    private void editarEstoque() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item do estoque para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) tableModel.getValueAt(selectedRow, 0);
        Estoque estoqueSelecionado = estoques.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);

        if (estoqueSelecionado != null) {
            EstoqueFormDialog dialog = new EstoqueFormDialog(this, estoqueSelecionado);
            dialog.setVisible(true);

            Optional<Estoque> estoqueEditadoOpt = dialog.getEstoque();
            if (estoqueEditadoOpt.isPresent()) {
                Estoque estoqueEditado = estoqueEditadoOpt.get();
                estoqueSelecionado.setQuantidade(estoqueEditado.getQuantidade());
                estoqueSelecionado.setLocalTanque(estoqueEditado.getLocalTanque());
                estoqueSelecionado.setLocalEndereco(estoqueEditado.getLocalEndereco());
                estoqueSelecionado.setLoteFabricacao(estoqueEditado.getLoteFabricacao());
                estoqueSelecionado.setDataValidade(estoqueEditado.getDataValidade());
                atualizarTabela();
            }
        }
    }

    private void excluirEstoque() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item do estoque para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o item selecionado?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            estoques.removeIf(e -> e.getId().equals(id));
            atualizarTabela();
        }
    }
}
