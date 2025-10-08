package com.br.pdvpostocombustivel.view.pessoa;

import com.br.pdvpostocombustivel.model.Pessoa;
import com.br.pdvpostocombustivel.enums.TipoPessoa;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class PessoaFormDialog extends JDialog {

    private JTextField nomeCompletoField;
    private JTextField cpfCnpjField;
    private JTextField numeroCtpsField;
    private JTextField dataNascimentoField;
    private JComboBox<TipoPessoa> tipoPessoaComboBox;

    private Pessoa pessoa;

    public PessoaFormDialog(Frame owner, Pessoa pessoa) {
        super(owner, "Adicionar/Editar Pessoa", true);
        this.pessoa = pessoa;

        setSize(400, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // Painel do formulário
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Nome Completo:"));
        nomeCompletoField = new JTextField();
        formPanel.add(nomeCompletoField);

        formPanel.add(new JLabel("CPF/CNPJ:"));
        cpfCnpjField = new JTextField();
        formPanel.add(cpfCnpjField);

        formPanel.add(new JLabel("Número CTPS:"));
        numeroCtpsField = new JTextField();
        formPanel.add(numeroCtpsField);

        formPanel.add(new JLabel("Data de Nascimento (yyyy-mm-dd):"));
        dataNascimentoField = new JTextField();
        formPanel.add(dataNascimentoField);

        formPanel.add(new JLabel("Tipo de Pessoa:"));
        tipoPessoaComboBox = new JComboBox<>(TipoPessoa.values());
        formPanel.add(tipoPessoaComboBox);

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

        // Preenche o formulário se uma pessoa for fornecida (modo de edição)
        if (pessoa != null) {
            preencherFormulario();
        }
    }

    private void preencherFormulario() {
        nomeCompletoField.setText(pessoa.getNomeCompleto());
        cpfCnpjField.setText(pessoa.getCpfCnpj());
        if (pessoa.getNumeroCtps() != null) {
            numeroCtpsField.setText(pessoa.getNumeroCtps().toString());
        }
        dataNascimentoField.setText(pessoa.getDataNascimento().toString());
        tipoPessoaComboBox.setSelectedItem(pessoa.getTipoPessoa());
    }

    private void salvar() {
        try {
            String nomeCompleto = nomeCompletoField.getText();
            String cpfCnpj = cpfCnpjField.getText();
            String numeroCtpsStr = numeroCtpsField.getText();
            String dataNascimentoStr = dataNascimentoField.getText();
            TipoPessoa tipoPessoa = (TipoPessoa) tipoPessoaComboBox.getSelectedItem();

            if (nomeCompleto.isBlank() || cpfCnpj.isBlank() || dataNascimentoStr.isBlank()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Long numeroCtps = numeroCtpsStr.isBlank() ? null : Long.parseLong(numeroCtpsStr);
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr);

            Long id = (pessoa != null) ? pessoa.getId() : null;
            this.pessoa = new Pessoa(id, nomeCompleto, cpfCnpj, numeroCtps, dataNascimento, tipoPessoa);

            setVisible(false);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número CTPS inválido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use yyyy-mm-dd.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Optional<Pessoa> getPessoa() {
        return Optional.ofNullable(pessoa);
    }
}
