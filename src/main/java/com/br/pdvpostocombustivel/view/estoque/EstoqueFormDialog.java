package com.br.pdvpostocombustivel.view.estoque;

import com.br.pdvpostocombustivel.model.Estoque;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class EstoqueFormDialog extends JDialog {

    private JTextField quantidadeField;
    private JTextField localTanqueField;
    private JTextField localEnderecoField;
    private JTextField loteFabricacaoField;
    private JTextField dataValidadeField;

    private Estoque estoque;

    public EstoqueFormDialog(Frame owner, Estoque estoque) {
        super(owner, "Adicionar/Editar Estoque", true);
        this.estoque = estoque;

        setSize(400, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // Painel do formulário
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Quantidade:"));
        quantidadeField = new JTextField();
        formPanel.add(quantidadeField);

        formPanel.add(new JLabel("Local (Tanque):"));
        localTanqueField = new JTextField();
        formPanel.add(localTanqueField);

        formPanel.add(new JLabel("Endereço:"));
        localEnderecoField = new JTextField();
        formPanel.add(localEnderecoField);

        formPanel.add(new JLabel("Lote de Fabricação:"));
        loteFabricacaoField = new JTextField();
        formPanel.add(loteFabricacaoField);

        formPanel.add(new JLabel("Data de Validade (yyyy-mm-dd):"));
        dataValidadeField = new JTextField();
        formPanel.add(dataValidadeField);

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

        if (estoque != null) {
            preencherFormulario();
        }
    }

    private void preencherFormulario() {
        quantidadeField.setText(estoque.getQuantidade().toPlainString());
        localTanqueField.setText(estoque.getLocalTanque());
        localEnderecoField.setText(estoque.getLocalEndereco());
        loteFabricacaoField.setText(estoque.getLoteFabricacao());
        dataValidadeField.setText(estoque.getDataValidade().toString());
    }

    private void salvar() {
        try {
            String quantidadeStr = quantidadeField.getText();
            String localTanque = localTanqueField.getText();
            String localEndereco = localEnderecoField.getText();
            String loteFabricacao = loteFabricacaoField.getText();
            String dataValidadeStr = dataValidadeField.getText();

            if (quantidadeStr.isBlank() || localTanque.isBlank() || localEndereco.isBlank() || loteFabricacao.isBlank() || dataValidadeStr.isBlank()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BigDecimal quantidade = new BigDecimal(quantidadeStr);
            LocalDate dataValidade = LocalDate.parse(dataValidadeStr);

            Long id = (estoque != null) ? estoque.getId() : null;
            this.estoque = new Estoque(id, quantidade, localTanque, localEndereco, loteFabricacao, dataValidade);

            setVisible(false);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida. Use um número.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use yyyy-mm-dd.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Optional<Estoque> getEstoque() {
        return Optional.ofNullable(estoque);
    }
}
