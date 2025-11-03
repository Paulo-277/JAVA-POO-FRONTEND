package com.br.pdvpostocombustivel.view.preco;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Preco {
    private JPanel precoPanel;
    private JTable tPreco;
    private JButton EDITARButton;
    private JButton EXCLUIRButton;
    private JButton ADICIONARButton;

    public Preco() {
        initComponents();

        ADICIONARButton.addActionListener(e -> {
            showPrecoDialog(null);
        });

        EDITARButton.addActionListener(e -> {
            int selectedRow = tPreco.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(precoPanel, "Selecione um preço para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            showPrecoDialog(selectedRow);
        });

        EXCLUIRButton.addActionListener(e -> {
            int selectedRow = tPreco.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(precoPanel, "Selecione um preço para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(precoPanel, "Tem certeza que deseja excluir o preço selecionado?", "Excluir Preço", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) tPreco.getModel();
                model.removeRow(selectedRow);
            }
        });
    }

    private void initComponents() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Valor");
        model.addColumn("Data de Alteração");
        model.addColumn("Hora de Alteração");

        model.addRow(new Object[]{1L, new BigDecimal("5.50"), LocalDate.now(), LocalTime.now()});
        model.addRow(new Object[]{2L, new BigDecimal("4.80"), LocalDate.now(), LocalTime.now()});

        tPreco.setModel(model);
    }

    private void showPrecoDialog(Integer selectedRow) {
        JTextField valorField = new JTextField(10);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Valor:"));
        myPanel.add(valorField);

        DefaultTableModel model = (DefaultTableModel) tPreco.getModel();
        String title;

        if (selectedRow != null) {
            title = "Editar Preço";
            valorField.setText(model.getValueAt(selectedRow, 1).toString());
        } else {
            title = "Adicionar Preço";
        }

        int result = JOptionPane.showConfirmDialog(null, myPanel, title, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                BigDecimal valor = new BigDecimal(valorField.getText());

                if (selectedRow != null) {
                    model.setValueAt(valor, selectedRow, 1);
                    model.setValueAt(LocalDate.now(), selectedRow, 2);
                    model.setValueAt(LocalTime.now(), selectedRow, 3);
                } else {
                    long newId = model.getRowCount() > 0 ? (long) model.getValueAt(model.getRowCount() - 1, 0) + 1 : 1L;
                    model.addRow(new Object[]{newId, valor, LocalDate.now(), LocalTime.now()});
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(precoPanel, "Erro ao converter o valor. Verifique o formato dos dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JPanel getPrecoPanel() {
        return precoPanel;
    }
}
