package com.br.pdvpostocombustivel.view.estoque;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Estoque {
    private JPanel panel1;
    private JButton EDITARButton;
    private JButton EXCLUIRButton;
    private JButton ADICIONARButton;
    private JTable tEstoque;

    public Estoque() {
        initComponents();

        ADICIONARButton.addActionListener(e -> {
            showEstoqueDialog(null);
        });

        EDITARButton.addActionListener(e -> {
            int selectedRow = tEstoque.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel1, "Selecione um item do estoque para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            showEstoqueDialog(selectedRow);
        });

        EXCLUIRButton.addActionListener(e -> {
            int selectedRow = tEstoque.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel1, "Selecione um item do estoque para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(panel1, "Tem certeza que deseja excluir o item selecionado?", "Excluir Item do Estoque", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) tEstoque.getModel();
                model.removeRow(selectedRow);
            }
        });
    }

    private void initComponents() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Quantidade");
        model.addColumn("Local do Tanque");
        model.addColumn("Endereço do Local");
        model.addColumn("Lote de Fabricação");
        model.addColumn("Data de Validade");

        model.addRow(new Object[]{1L, new BigDecimal("10000.00"), "Tanque 01", "Posto A", "LOTE001", LocalDate.now().plusMonths(6)});
        model.addRow(new Object[]{2L, new BigDecimal("15000.00"), "Tanque 02", "Posto A", "LOTE002", LocalDate.now().plusMonths(6)});

        tEstoque.setModel(model);
    }

    private void showEstoqueDialog(Integer selectedRow) {
        JTextField quantidadeField = new JTextField(10);
        JTextField localTanqueField = new JTextField(20);
        JTextField localEnderecoField = new JTextField(20);
        JTextField loteFabricacaoField = new JTextField(10);
        JTextField dataValidadeField = new JTextField(10);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Quantidade:"));
        myPanel.add(quantidadeField);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("Local do Tanque:"));
        myPanel.add(localTanqueField);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("Endereço do Local:"));
        myPanel.add(localEnderecoField);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("Lote de Fabricação:"));
        myPanel.add(loteFabricacaoField);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("Data de Validade (yyyy-MM-dd):"));
        myPanel.add(dataValidadeField);

        DefaultTableModel model = (DefaultTableModel) tEstoque.getModel();
        String title;

        if (selectedRow != null) {
            title = "Editar Item do Estoque";
            quantidadeField.setText(model.getValueAt(selectedRow, 1).toString());
            localTanqueField.setText((String) model.getValueAt(selectedRow, 2));
            localEnderecoField.setText((String) model.getValueAt(selectedRow, 3));
            loteFabricacaoField.setText((String) model.getValueAt(selectedRow, 4));
            dataValidadeField.setText(model.getValueAt(selectedRow, 5).toString());
        } else {
            title = "Adicionar Item ao Estoque";
        }

        int result = JOptionPane.showConfirmDialog(null, myPanel, title, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                BigDecimal quantidade = new BigDecimal(quantidadeField.getText());
                String localTanque = localTanqueField.getText();
                String localEndereco = localEnderecoField.getText();
                String loteFabricacao = loteFabricacaoField.getText();
                LocalDate dataValidade = LocalDate.parse(dataValidadeField.getText(), DateTimeFormatter.ISO_LOCAL_DATE);

                if (selectedRow != null) {
                    model.setValueAt(quantidade, selectedRow, 1);
                    model.setValueAt(localTanque, selectedRow, 2);
                    model.setValueAt(localEndereco, selectedRow, 3);
                    model.setValueAt(loteFabricacao, selectedRow, 4);
                    model.setValueAt(dataValidade, selectedRow, 5);
                } else {
                    long newId = model.getRowCount() > 0 ? (long) model.getValueAt(model.getRowCount() - 1, 0) + 1 : 1L;
                    model.addRow(new Object[]{newId, quantidade, localTanque, localEndereco, loteFabricacao, dataValidade});
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel1, "Erro ao converter os valores. Verifique o formato dos dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JPanel getEstoquePanel() {
        return panel1;
    }
}
