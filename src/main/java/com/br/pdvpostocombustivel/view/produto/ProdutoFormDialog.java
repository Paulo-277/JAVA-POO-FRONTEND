package com.br.pdvpostocombustivel.view.produto;

import com.br.pdvpostocombustivel.model.Produto;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class ProdutoFormDialog extends JDialog {

    private JTextField nomeField;
    private JTextField referenciaField;
    private JTextField fornecedorField;
    private JTextField categoriaField;
    private JTextField marcaField;

    private Produto produto;

    public ProdutoFormDialog(Frame owner, Produto produto) {
        super(owner, "Adicionar/Editar Produto", true);
        this.produto = produto;

        setSize(400, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // Painel do formulário
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        formPanel.add(nomeField);

        formPanel.add(new JLabel("Referência:"));
        referenciaField = new JTextField();
        formPanel.add(referenciaField);

        formPanel.add(new JLabel("Fornecedor:"));
        fornecedorField = new JTextField();
        formPanel.add(fornecedorField);

        formPanel.add(new JLabel("Categoria:"));
        categoriaField = new JTextField();
        formPanel.add(categoriaField);

        formPanel.add(new JLabel("Marca:"));
        marcaField = new JTextField();
        formPanel.add(marcaField);

        add(formPanel, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Salvar");
        JButton cancelButton = new JButton("Cancelar");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Ações dos botões
        cancelButton.addActionListener(e -> setVisible(false));
        saveButton.addActionListener(e -> salvar());

        if (produto != null) {
            preencherFormulario();
        }
    }

    private void preencherFormulario() {
        nomeField.setText(produto.getNome());
        referenciaField.setText(produto.getReferencia());
        fornecedorField.setText(produto.getFornecedor());
        categoriaField.setText(produto.getCategoria());
        marcaField.setText(produto.getMarca());
    }

    private void salvar() {
        String nome = nomeField.getText();
        String referencia = referenciaField.getText();
        String fornecedor = fornecedorField.getText();
        String categoria = categoriaField.getText();
        String marca = marcaField.getText();

        if (nome.isBlank() || referencia.isBlank() || fornecedor.isBlank() || categoria.isBlank() || marca.isBlank()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Long id = (produto != null) ? produto.getId() : null;
        this.produto = new Produto(id, nome, referencia, fornecedor, categoria, marca);

        setVisible(false);
    }

    public Optional<Produto> getProduto() {
        return Optional.ofNullable(produto);
    }
}
