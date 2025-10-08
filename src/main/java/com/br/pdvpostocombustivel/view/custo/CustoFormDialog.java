package com.br.pdvpostocombustivel.view.custo;

import com.br.pdvpostocombustivel.model.Custo;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class CustoFormDialog extends JDialog {

    private JTextField impostoField;
    private JTextField custoVariavelField;
    private JTextField custoFixoField;
    private JTextField margemLucroField;
    private JTextField dataProcessamentoField;

    private Custo custo;

    public CustoFormDialog(Frame owner, Custo custo) {
        super(owner, "Adicionar/Editar Custo", true);
        this.custo = custo;

        setSize(400, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // Painel do formulário
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Imposto (%):"));
        impostoField = new JTextField();
        formPanel.add(impostoField);

        formPanel.add(new JLabel("Custo Variável:"));
        custoVariavelField = new JTextField();
        formPanel.add(custoVariavelField);

        formPanel.add(new JLabel("Custo Fixo:"));
        custoFixoField = new JTextField();
        formPanel.add(custoFixoField);

        formPanel.add(new JLabel("Margem de Lucro (%):"));
        margemLucroField = new JTextField();
        formPanel.add(margemLucroField);

        formPanel.add(new JLabel("Data Processamento (yyyy-mm-dd):"));
        dataProcessamentoField = new JTextField();
        formPanel.add(dataProcessamentoField);

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

        if (custo != null) {
            preencherFormulario();
        }
    }

    private void preencherFormulario() {
        impostoField.setText(custo.getImposto().toString());
        custoVariavelField.setText(custo.getCustoVariavel().toString());
        custoFixoField.setText(custo.getCustoFixo().toString());
        margemLucroField.setText(custo.getMargemLucro().toString());
        dataProcessamentoField.setText(custo.getDataProcessamento().toString());
    }

    private void salvar() {
        try {
            String impostoStr = impostoField.getText();
            String custoVariavelStr = custoVariavelField.getText();
            String custoFixoStr = custoFixoField.getText();
            String margemLucroStr = margemLucroField.getText();
            String dataProcessamentoStr = dataProcessamentoField.getText();

            if (impostoStr.isBlank() || custoVariavelStr.isBlank() || custoFixoStr.isBlank() || margemLucroStr.isBlank() || dataProcessamentoStr.isBlank()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Double imposto = Double.parseDouble(impostoStr);
            Double custoVariavel = Double.parseDouble(custoVariavelStr);
            Double custoFixo = Double.parseDouble(custoFixoStr);
            Double margemLucro = Double.parseDouble(margemLucroStr);
            LocalDate dataProcessamento = LocalDate.parse(dataProcessamentoStr);

            Long id = (custo != null) ? custo.getId() : null;
            this.custo = new Custo(id, imposto, custoVariavel, custoFixo, margemLucro, dataProcessamento);

            setVisible(false);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valores numéricos inválidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use yyyy-mm-dd.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Optional<Custo> getCusto() {
        return Optional.ofNullable(custo);
    }
}
