package com.br.pdvpostocombustivel.view.gerenciadorbomba;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
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

    private final ApplicationContext context;

    public GerenciadorBombas(ApplicationContext context) {
        this.context = context;
        // Setup for each pump
        setupPump(combB1, quantB1, preçoB1, preçoTotalB1);
        setupPump(combB2, quantB2, preçoB2, preçoTotalB2);
        setupPump(combB3, quantB3, preçoB3, PreçoTotalB3);

        // Set initial component states
        updateComponentsState(statusB1, combB1, quantB1, PagB1);
        updateComponentsState(statusB2, combB2, quantB2, PagB2);
        updateComponentsState(statusB3, combB3, quantB3, PagB3);

        // Setup button listeners
        setupButtonListeners(GERARCOMPROVANTEButton, statusB1, combB1, quantB1, PagB1, preçoTotalB1);
        setupButtonListeners(EFETUARPAGAMENTOButton, statusB2, combB2, quantB2, PagB2, preçoTotalB2);
        setupButtonListeners(INICIARATENDIMENTOButton, statusB3, combB3, quantB3, PagB3, PreçoTotalB3);
    }

    private void setupPump(JComboBox<String> fuelCombo, JTextField quantityField, JLabel priceLabel, JLabel totalPriceLabel) {
        priceLabel.setText(String.format("R$ %.2f", precosCombustiveis.get("GASOLINA")));
        totalPriceLabel.setText("R$ 0.00");
        quantityField.setText("0");

        fuelCombo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updatePrice(fuelCombo, priceLabel);
                updateTotalPrice(fuelCombo, quantityField, totalPriceLabel);
            }
        });

        quantityField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateTotalPrice(fuelCombo, quantityField, totalPriceLabel); }
            public void removeUpdate(DocumentEvent e) { updateTotalPrice(fuelCombo, quantityField, totalPriceLabel); }
            public void changedUpdate(DocumentEvent e) { updateTotalPrice(fuelCombo, quantityField, totalPriceLabel); }
        });
    }

    private void setupButtonListeners(JButton button, JLabel statusLabel, JComboBox<String> fuelCombo, JTextField quantityField, JComboBox<String> paymentCombo, JLabel totalPriceLabel) {
        button.addActionListener(e -> {
            String currentStatus = statusLabel.getText();
            String nextStatus = "";
            String nextButtonText = "";

            if ("INATIVA".equals(currentStatus)) {
                nextStatus = "ATIVA";
                nextButtonText = "EFETUAR PAGAMENTO";
            } else if ("ATIVA".equals(currentStatus)) {
                nextStatus = "CONCLUÍDA";
                nextButtonText = "GERAR COMPROVANTE";
            } else if ("CONCLUÍDA".equals(currentStatus)) {
                Comprovante comprovante = context.getBean(Comprovante.class);
                comprovante.setDados(
                        (String) fuelCombo.getSelectedItem(),
                        quantityField.getText(),
                        totalPriceLabel.getText(),
                        (String) paymentCombo.getSelectedItem()
                );

                Window window = SwingUtilities.getWindowAncestor(panel1);
                JDialog dialog;
                if (window instanceof Frame) {
                    dialog = new JDialog((Frame) window, "Comprovante", true);
                } else {
                    dialog = new JDialog((Dialog) window, "Comprovante", true);
                }
                
                dialog.setContentPane(comprovante.getComprovantePanel());
                dialog.pack();
                dialog.setLocationRelativeTo(panel1);
                dialog.setVisible(true);

                nextStatus = "INATIVA";
                nextButtonText = "INICIAR ATENDIMENTO";
            }

            statusLabel.setText(nextStatus);
            button.setText(nextButtonText);
            updateComponentsState(statusLabel, fuelCombo, quantityField, paymentCombo);
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

    private void updateComponentsState(JLabel statusLabel, JComboBox<String> fuelCombo, JTextField quantityField, JComboBox<String> paymentCombo) {
        String status = statusLabel.getText();
        boolean isFuelEditable = "ATIVA".equals(status);
        boolean isPaymentEditable = "CONCLUÍDA".equals(status);

        fuelCombo.setEnabled(isFuelEditable);
        quantityField.setEditable(isFuelEditable);
        paymentCombo.setEnabled(isPaymentEditable);

        switch (status) {
            case "ATIVA":
                statusLabel.setForeground(new Color(240, 248, 20)); // Yellow
                break;
            case "CONCLUÍDA":
                statusLabel.setForeground(new Color(29, 186, 26)); // Green
                break;
            case "INATIVA":
                statusLabel.setForeground(new Color(240, 13, 28));
                break;
        }
    }

    public JPanel getGerenciadorBombasPanel() {
        return panel1;
    }
}
