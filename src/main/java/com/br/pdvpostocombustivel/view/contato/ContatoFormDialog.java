package com.br.pdvpostocombustivel.view.contato;

import com.br.pdvpostocombustivel.model.Contato;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class ContatoFormDialog extends JDialog {

    private JTextField telefoneField;
    private JTextField emailField;
    private JTextField enderecoField;

    private Contato contato;

    public ContatoFormDialog(Frame owner, Contato contato) {
        super(owner, "Adicionar/Editar Contato", true);
        this.contato = contato;

        setSize(400, 200);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // Painel do formulário
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Telefone:"));
        telefoneField = new JTextField();
        formPanel.add(telefoneField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Endereço:"));
        enderecoField = new JTextField();
        formPanel.add(enderecoField);

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

        if (contato != null) {
            preencherFormulario();
        }
    }

    private void preencherFormulario() {
        telefoneField.setText(contato.getTelefone());
        emailField.setText(contato.getEmail());
        enderecoField.setText(contato.getEndereco());
    }

    private void salvar() {
        String telefone = telefoneField.getText();
        String email = emailField.getText();
        String endereco = enderecoField.getText();

        if (telefone.isBlank() || email.isBlank() || endereco.isBlank()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Long id = (contato != null) ? contato.getId() : null;
        this.contato = new Contato(id, telefone, email, endereco);

        setVisible(false);
    }

    public Optional<Contato> getContato() {
        return Optional.ofNullable(contato);
    }
}
