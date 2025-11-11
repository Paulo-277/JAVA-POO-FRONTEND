package com.br.pdvpostocombustivel.view.gerenciadorbomba;

import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ItemEvent;
import java.util.Map;

@Component
public class GerenciadorBombas {
    private JPanel panel1;
    private JComboBox PagB2;
    private JComboBox PagB1;
    private JComboBox PagB3;
    private JComboBox<String> combB2;
    private JButton EFETUARPAGAMENTOButton;
    private JButton GERARCOMPROVANTEButton;
    private JButton INICIARATENDIMENTOButton;
    private JLabel statusB1;
    private JLabel statusB2;
    private JLabel statusB3;
    private JLabel preçoB3;
    private JLabel preçoB2;
    private JLabel preçoB1;
    private JTextField quantB1;
    private JTextField quantB2;
    private JTextField quantB3;
    private JLabel preçoTotalB1;
    private JPanel B1;
    private JPanel B3;
    private JPanel B2;
    private JLabel preçoTotalB2;
    private JLabel PreçoTotalB3;
    private JComboBox<String> combB1;
    private JComboBox<String> combB3;

    private final Map<String, Double> precosCombustiveis = Map.of(
            "GASOLINA", 5.00,
            "ETANOL", 3.00
    );

    public GerenciadorBombas() {
        preparaBomba(combB1, quantB1, preçoB1, preçoTotalB1);
        preparaBomba(combB2, quantB2, preçoB2, preçoTotalB2);
        preparaBomba(combB3, quantB3, preçoB3, PreçoTotalB3);
    }

    private void preparaBomba(JComboBox<String> tipoComb, JTextField quantidadeComb, JLabel precoComb, JLabel precoTotal) {
        // Arumar valores iniciais
        precoComb.setText(String.format("R$ %.2f", precosCombustiveis.get("GASOLINA")));
        precoTotal.setText("R$ 0.00");
        quantidadeComb.setText("0");

        // Preparando a mudança de combustível
        tipoComb.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updatePrice(tipoComb, precoComb);
                updateTotalPrice(tipoComb, quantidadeComb, precoTotal);
            }
        });

        // Preparando a mudança nos campos de texto
        quantidadeComb.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateTotalPrice(tipoComb, quantidadeComb, precoTotal);
            }
            public void removeUpdate(DocumentEvent e) {
                updateTotalPrice(tipoComb, quantidadeComb, precoTotal);
            }
            public void changedUpdate(DocumentEvent e) {
                updateTotalPrice(tipoComb, quantidadeComb, precoTotal);
            }
        });
    }

    private void updatePrice(JComboBox<String> fuelCombo, JLabel priceLabel) {
        String selectedFuel = (String) fuelCombo.getSelectedItem();
        if (selectedFuel != null) {
            priceLabel.setText(String.format("R$ %.2f", precosCombustiveis.get(selectedFuel)));
        }
    }

    private void updateTotalPrice(JComboBox<String> fuelCombo, JTextField quantityField, JLabel totalPriceLabel) {
        try {
            String selectedFuel = (String) fuelCombo.getSelectedItem();
            double pricePerLiter = precosCombustiveis.get(selectedFuel);
            double quantity = Double.parseDouble(quantityField.getText());
            double totalPrice = pricePerLiter * quantity;
            totalPriceLabel.setText(String.format("R$ %.2f", totalPrice));
        } catch (NumberFormatException | NullPointerException ex) {
            totalPriceLabel.setText("R$ 0.00");
        }
    }

    public JPanel getGerenciadorBombasPanel() {
        return panel1;
    }
}
