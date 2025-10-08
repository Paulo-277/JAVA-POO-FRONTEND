package com.br.pdvpostocombustivel.view.preco;

import com.br.pdvpostocombustivel.model.Preco;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class PrecoFormDialog extends JDialog {

    private JTextField valorField;
    private JTextField dataAlteracaoField;
    private JTextField horaAlteracaoField;

    private Preco preco;

    public PrecoFormDialog(Frame owner, Preco preco) {
        super(owner, "Adicionar/Editar Preço", true);
        this.preco = preco;

        setSize(400, 200);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // Painel do formulário
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Valor:"));
        valorField = new JTextField();
        formPanel.add(valorField);

        formPanel.add(new JLabel("Data Alteração (yyyy-mm-dd):"));
        dataAlteracaoField = new JTextField();
        formPanel.add(dataAlteracaoField);

        formPanel.add(new JLabel("Hora Alteração (yyyy-mm-dd):"));
        horaAlteracaoField = new JTextField();
        formPanel.add(horaAlteracaoField);

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

        if (preco != null) {
            preencherFormulario();
        }
    }

    private void preencherFormulario() {
        valorField.setText(preco.getValor().toPlainString());
        dataAlteracaoField.setText(preco.getDataAlteracao().toString());
        horaAlteracaoField.setText(preco.getHoraAlteracao().toString());
    }

    private void salvar() {
        try {
            String valorStr = valorField.getText();
            String dataAlteracaoStr = dataAlteracaoField.getText();
            String horaAlteracaoStr = horaAlteracaoField.getText();

            if (valorStr.isBlank() || dataAlteracaoStr.isBlank() || horaAlteracaoStr.isBlank()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BigDecimal valor = new BigDecimal(valorStr);
            LocalDate dataAlteracao = LocalDate.parse(dataAlteracaoStr);
            LocalDate horaAlteracao = LocalDate.parse(horaAlteracaoStr);

            Long id = (preco != null) ? preco.getId() : null;
            this.preco = new Preco(id, valor, dataAlteracao, horaAlteracao);

            setVisible(false);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido. Use um número.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use yyyy-mm-dd.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Optional<Preco> getPreco() {
        return Optional.ofNullable(preco);
    }
}
