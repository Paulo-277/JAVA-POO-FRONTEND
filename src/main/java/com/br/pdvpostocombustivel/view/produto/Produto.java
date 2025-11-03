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

        ADICIONARButton.addActionListener(e -> {
            showProdutoDialog(null);
        });

        EDITARButton.addActionListener(e -> {
            int selectedRow = tProduto.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel1, "Selecione um produto para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            showProdutoDialog(selectedRow);
        });

        EXCLUIRButton.addActionListener(e -> {
            int selectedRow = tProduto.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel1, "Selecione um produto para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(panel1, "Tem certeza que deseja excluir o produto selecionado?", "Excluir Produto", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) tProduto.getModel();
                model.removeRow(selectedRow);
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

        model.addRow(new Object[]{1L, "Gasolina Comum", "GC001", "Petrobras", "Combustível", "Petrobras"});
        model.addRow(new Object[]{2L, "Óleo Lubrificante", "OL001", "Ipiranga", "Lubrificante", "Ipiranga"});

        tProduto.setModel(model);
    }

    private void showProdutoDialog(Integer selectedRow) {
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

        DefaultTableModel model = (DefaultTableModel) tProduto.getModel();
        String title;

        if (selectedRow != null) {
            title = "Editar Produto";
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

            if (nome != null && !nome.trim().isEmpty() && referencia != null && !referencia.trim().isEmpty() && fornecedor != null && !fornecedor.trim().isEmpty() && categoria != null && !categoria.trim().isEmpty() && marca != null && !marca.trim().isEmpty()) {
                if (selectedRow != null) {
                    model.setValueAt(nome, selectedRow, 1);
                    model.setValueAt(referencia, selectedRow, 2);
                    model.setValueAt(fornecedor, selectedRow, 3);
                    model.setValueAt(categoria, selectedRow, 4);
                    model.setValueAt(marca, selectedRow, 5);
                } else {
                    long newId = model.getRowCount() > 0 ? (long) model.getValueAt(model.getRowCount() - 1, 0) + 1 : 1L;
                    model.addRow(new Object[]{newId, nome, referencia, fornecedor, categoria, marca});
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
