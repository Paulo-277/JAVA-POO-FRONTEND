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
        // Initialize B2 components
        preçoB2.setText(String.format("R$ %.2f", precosCombustiveis.get("GASOLINA")));
        preçoTotalB2.setText("R$ 0.00");
        quantB2.setText("0");

        combB2.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updatePriceB2();
                updateTotalPriceB2();
            }
        });

        quantB2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTotalPriceB2();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTotalPriceB2();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTotalPriceB2();
            }
        });
    }

    private void updatePriceB2() {
        String selectedFuel = (String) combB2.getSelectedItem();
        if (selectedFuel != null) {
            preçoB2.setText(String.format("R$ %.2f", precosCombustiveis.get(selectedFuel)));
        }
    }

    private void updateTotalPriceB2() {
        try {
            String selectedFuel = (String) combB2.getSelectedItem();
            double pricePerLiter = precosCombustiveis.get(selectedFuel);
            double quantity = Double.parseDouble(quantB2.getText());
            double totalPrice = pricePerLiter * quantity;
            preçoTotalB2.setText(String.format("R$ %.2f", totalPrice));
        } catch (NumberFormatException ex) {
            preçoTotalB2.setText("R$ 0.00");
        }
    }

    public JPanel getGerenciadorBombasPanel() {
        return panel1;
    }
}
