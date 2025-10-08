package com.br.pdvpostocombustivel.view.acesso;

import com.br.pdvpostocombustivel.model.Acesso;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class AcessoFormDialog extends JDialog {

    private JTextField usuarioField;
    private JPasswordField senhaField;

    private Acesso acesso;

    public AcessoFormDialog(Frame owner, Acesso acesso) {
        super(owner, "Adicionar/Editar Acesso", true);
        this.acesso = acesso;

        setSize(400, 150);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // Painel do formulário
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Usuário:"));
        usuarioField = new JTextField();
        formPanel.add(usuarioField);

        formPanel.add(new JLabel("Senha:"));
        senhaField = new JPasswordField();
        formPanel.add(senhaField);

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

        if (acesso != null) {
            preencherFormulario();
        }
    }

    private void preencherFormulario() {
        usuarioField.setText(acesso.getUsuario());
        senhaField.setText(acesso.getSenha());
    }

    private void salvar() {
        String usuario = usuarioField.getText();
        String senha = new String(senhaField.getPassword());

        if (usuario.isBlank() || senha.isBlank()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Long id = (acesso != null) ? acesso.getId() : null;
        this.acesso = new Acesso(id, usuario, senha);

        setVisible(false);
    }

    public Optional<Acesso> getAcesso() {
        return Optional.ofNullable(acesso);
    }
}
