package com.br.pdvpostocombustivel.view.produto;

import com.br.pdvpostocombustivel.model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoCrudFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private List<Produto> produtos = new ArrayList<>();
    private long nextId = 1;

    public ProdutoCrudFrame() {
        setTitle("Cadastro de Produtos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tabela
        String[] columnNames = {"ID", "Nome", "Referência", "Fornecedor", "Categoria", "Marca"};
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
        addButton.addActionListener(e -> adicionarProduto());
        editButton.addActionListener(e -> editarProduto());
        deleteButton.addActionListener(e -> excluirProduto());

        // Carregar dados iniciais
        carregarDadosIniciais();
        atualizarTabela();
    }

    private void carregarDadosIniciais() {
        produtos.add(new Produto(nextId++, "Gasolina Comum", "GC001", "Petrobras", "Combustível", "BR"));
        produtos.add(new Produto(nextId++, "Óleo Motor 5W30", "OM5W30", "Shell", "Lubrificante", "Helix"));
        produtos.add(new Produto(nextId++, "Pneu Aro 15", "PNR15", "Pirelli", "Pneu", "Cinturato P1"));
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        for (Produto produto : produtos) {
            tableModel.addRow(new Object[]{
                    produto.getId(),
                    produto.getNome(),
                    produto.getReferencia(),
                    produto.getFornecedor(),
                    produto.getCategoria(),
                    produto.getMarca()
            });
        }
    }

    private void adicionarProduto() {
        ProdutoFormDialog dialog = new ProdutoFormDialog(this, null);
        dialog.setVisible(true);

        Optional<Produto> novoProdutoOpt = dialog.getProduto();
        if (novoProdutoOpt.isPresent()) {
            Produto novoProduto = novoProdutoOpt.get();
            novoProduto.setId(nextId++);
            produtos.add(novoProduto);
            atualizarTabela();
        }
    }

    private void editarProduto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) tableModel.getValueAt(selectedRow, 0);
        Produto produtoSelecionado = produtos.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);

        if (produtoSelecionado != null) {
            ProdutoFormDialog dialog = new ProdutoFormDialog(this, produtoSelecionado);
            dialog.setVisible(true);

            Optional<Produto> produtoEditadoOpt = dialog.getProduto();
            if (produtoEditadoOpt.isPresent()) {
                Produto produtoEditado = produtoEditadoOpt.get();
                produtoSelecionado.setNome(produtoEditado.getNome());
                produtoSelecionado.setReferencia(produtoEditado.getReferencia());
                produtoSelecionado.setFornecedor(produtoEditado.getFornecedor());
                produtoSelecionado.setCategoria(produtoEditado.getCategoria());
                produtoSelecionado.setMarca(produtoEditado.getMarca());
                atualizarTabela();
            }
        }
    }

    private void excluirProduto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o produto selecionado?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            produtos.removeIf(p -> p.getId().equals(id));
            atualizarTabela();
        }
    }
}
