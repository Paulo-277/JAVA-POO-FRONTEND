package com.br.pdvpostocombustivel.view.produto;

import com.br.pdvpostocombustivel.model.produto.ProdutoRequest;
import com.br.pdvpostocombustivel.model.produto.ProdutoResponse;
import com.br.pdvpostocombustivel.services.ProdutoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class Produto {
    private JPanel panel1;
    private JButton EDITARButton;
    private JButton EXCLUIRButton;
    private JButton ADICIONARButton;
    private JTable tProduto;
    private final ProdutoService produtoService;

    public Produto() {
        this.produtoService = new ProdutoService();
        initComponents();
        loadProdutos();

        ADICIONARButton.addActionListener(e -> showProdutoDialog(null));

        EDITARButton.addActionListener(e -> {
            int selectedRow = tProduto.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel1, "Selecione um produto para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Long id = (Long) tProduto.getValueAt(selectedRow, 0);
            showProdutoDialog(id);
        });

        EXCLUIRButton.addActionListener(e -> {
            int selectedRow = tProduto.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel1, "Selecione um produto para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(panel1, "Tem certeza que deseja excluir o produto selecionado?", "Excluir Produto", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Long id = (Long) tProduto.getValueAt(selectedRow, 0);
                try {
                    produtoService.deleteProduto(id);
                    loadProdutos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel1, "Erro ao excluir produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void initComponents() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("Referência");
        model.addColumn("Fornecedor");
        model.addColumn("Categoria");
        model.addColumn("Marca");
        tProduto.setModel(model);
    }

    private void loadProdutos() {
        try {
            List<ProdutoResponse> produtos = produtoService.getAllProdutos();
            DefaultTableModel model = (DefaultTableModel) tProduto.getModel();
            model.setRowCount(0); // Clear existing data
            for (ProdutoResponse produto : produtos) {
                model.addRow(new Object[]{
                        produto.id(),
                        produto.nome(),
                        produto.referencia(),
                        produto.fornecedor(),
                        produto.categoria(),
                        produto.marca()
                });
            }
        } catch (Exception e) {
            DefaultTableModel model = (DefaultTableModel) tProduto.getModel();
            model.setRowCount(0);
            model.addRow(new Object[]{1L, "Gasolina Comum (mock)", "GC001", "Petrobras", "Combustível", "Petrobras"});
            model.addRow(new Object[]{2L, "Óleo Lubrificante (mock)", "OL001", "Ipiranga", "Lubrificante", "Ipiranga"});
            JOptionPane.showMessageDialog(panel1, "Não foi possível carregar os produtos do servidor. Carregando dados mockados.\n" + e.getMessage(), "Erro de Conexão", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showProdutoDialog(Long produtoId) {
        JTextField nomeField = new JTextField(20);
        JTextField referenciaField = new JTextField(20);
        JTextField fornecedorField = new JTextField(20);
        JTextField categoriaField = new JTextField(20);
        JTextField marcaField = new JTextField(20);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Nome:"));
        myPanel.add(nomeField);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("Referência:"));
        myPanel.add(referenciaField);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("Fornecedor:"));
        myPanel.add(fornecedorField);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("Categoria:"));
        myPanel.add(categoriaField);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("Marca:"));
        myPanel.add(marcaField);

        String title;

        if (produtoId != null) {
            title = "Editar Produto";
            int selectedRow = tProduto.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel) tProduto.getModel();
            nomeField.setText((String) model.getValueAt(selectedRow, 1));
            referenciaField.setText((String) model.getValueAt(selectedRow, 2));
            fornecedorField.setText((String) model.getValueAt(selectedRow, 3));
            categoriaField.setText((String) model.getValueAt(selectedRow, 4));
            marcaField.setText((String) model.getValueAt(selectedRow, 5));
        } else {
            title = "Adicionar Produto";
        }

        int result = JOptionPane.showConfirmDialog(null, myPanel, title, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            String referencia = referenciaField.getText();
            String fornecedor = fornecedorField.getText();
            String categoria = categoriaField.getText();
            String marca = marcaField.getText();

            if (!nome.trim().isEmpty() && !referencia.trim().isEmpty() && !fornecedor.trim().isEmpty() && !categoria.trim().isEmpty() && !marca.trim().isEmpty()) {
                ProdutoRequest request = new ProdutoRequest(nome, referencia, fornecedor, categoria, marca);
                try {
                    if (produtoId != null) {
                        produtoService.updateProduto(produtoId, request);
                    } else {
                        produtoService.createProduto(request);
                    }
                    loadProdutos(); // Refresh table
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(panel1, "Erro ao salvar produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panel1, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JPanel getProdutoPanel() {
        return panel1;
    }
}
