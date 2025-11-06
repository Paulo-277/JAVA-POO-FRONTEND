package com.br.pdvpostocombustivel.view.preco;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Preco {
    private JPanel precoPanel;
    private JTable tPreco;
    private JButton EDITARButton;
    private JButton EXCLUIRButton;
    private JButton ADICIONARButton;

    public Preco() {
        initComponents();
        ADICIONARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) tPreco.getModel();
                String valor = JOptionPane.showInputDialog("Digite o novo valor:");
                if (valor != null && !valor.isEmpty()) {
                    model.addRow(new Object[]{model.getRowCount() + 1L, new BigDecimal(valor), LocalDate.now(), LocalTime.now()});
                }
            }
        });
        EDITARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tPreco.getSelectedRow();
                if (selectedRow != -1) {
                    DefaultTableModel model = (DefaultTableModel) tPreco.getModel();
                    String valor = JOptionPane.showInputDialog("Digite o novo valor:", model.getValueAt(selectedRow, 1));
                    if (valor != null && !valor.isEmpty()) {
                        model.setValueAt(new BigDecimal(valor), selectedRow, 1);
                        model.setValueAt(LocalDate.now(), selectedRow, 2);
                        model.setValueAt(LocalTime.now(), selectedRow, 3);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione uma linha para editar.");
                }
            }
        });
        EXCLUIRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tPreco.getSelectedRow();
                if (selectedRow != -1) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o preço selecionado?", "Excluir Preço", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        DefaultTableModel model = (DefaultTableModel) tPreco.getModel();
                        model.removeRow(selectedRow);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione uma linha para excluir.");
                }
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

    public JPanel getPrecoPanel() {
        return precoPanel;
    }
}
