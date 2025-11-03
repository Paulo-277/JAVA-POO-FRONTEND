package com.br.pdvpostocombustivel.view.custo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Custo {
    private JPanel panel1;
    private JTable tCusto;
    private JButton EDITARButton;
    private JButton EXCLUIRButton;
    private JButton ADICIONARButton;

    public Custo() {
        initComponents();

        ADICIONARButton.addActionListener(e -> {
            showCustoDialog(null);
        });

        EDITARButton.addActionListener(e -> {
            int selectedRow = tCusto.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel1, "Selecione um custo para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            showCustoDialog(selectedRow);
        });

        EXCLUIRButton.addActionListener(e -> {
            int selectedRow = tCusto.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel1, "Selecione um custo para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(panel1, "Tem certeza que deseja excluir o custo selecionado?", "Excluir Custo", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) tCusto.getModel();
                model.removeRow(selectedRow);
            }
        });
    }

    private void initComponents() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Imposto");
        model.addColumn("Custo Variável");
        model.addColumn("Custo Fixo");
        model.addColumn("Margem de Lucro");
        model.addColumn("Data de Processamento");

        model.addRow(new Object[]{1L, new BigDecimal("0.10"), new BigDecimal("1.50"), new BigDecimal("1000.00"), new BigDecimal("0.20"), LocalDate.now()});
        model.addRow(new Object[]{2L, new BigDecimal("0.12"), new BigDecimal("1.60"), new BigDecimal("1200.00"), new BigDecimal("0.22"), LocalDate.now()});

        tCusto.setModel(model);
    }

    private void showCustoDialog(Integer selectedRow) {
        JTextField impostoField = new JTextField(10);
        JTextField custoVariavelField = new JTextField(10);
        JTextField custoFixoField = new JTextField(10);
        JTextField margemLucroField = new JTextField(10);
        JTextField dataProcessamentoField = new JTextField(10);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Imposto:"));
        myPanel.add(impostoField);
        myPanel.add(Box.createVerticalStrut(15)); // a spacer
        myPanel.add(new JLabel("Custo Variável:"));
        myPanel.add(custoVariavelField);
        myPanel.add(Box.createVerticalStrut(15)); // a spacer
        myPanel.add(new JLabel("Custo Fixo:"));
        myPanel.add(custoFixoField);
        myPanel.add(Box.createVerticalStrut(15)); // a spacer
        myPanel.add(new JLabel("Margem de Lucro:"));
        myPanel.add(margemLucroField);
        myPanel.add(Box.createVerticalStrut(15)); // a spacer
        myPanel.add(new JLabel("Data de Processamento (yyyy-MM-dd):"));
        myPanel.add(dataProcessamentoField);

        DefaultTableModel model = (DefaultTableModel) tCusto.getModel();
        String title;

        if (selectedRow != null) {
            title = "Editar Custo";
            impostoField.setText(model.getValueAt(selectedRow, 1).toString());
            custoVariavelField.setText(model.getValueAt(selectedRow, 2).toString());
            custoFixoField.setText(model.getValueAt(selectedRow, 3).toString());
            margemLucroField.setText(model.getValueAt(selectedRow, 4).toString());
            dataProcessamentoField.setText(model.getValueAt(selectedRow, 5).toString());
        } else {
            title = "Adicionar Custo";
        }

        int result = JOptionPane.showConfirmDialog(null, myPanel, title, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                BigDecimal imposto = new BigDecimal(impostoField.getText());
                BigDecimal custoVariavel = new BigDecimal(custoVariavelField.getText());
                BigDecimal custoFixo = new BigDecimal(custoFixoField.getText());
                BigDecimal margemLucro = new BigDecimal(margemLucroField.getText());
                LocalDate dataProcessamento = LocalDate.parse(dataProcessamentoField.getText(), DateTimeFormatter.ISO_LOCAL_DATE);

                if (selectedRow != null) {
                    model.setValueAt(imposto, selectedRow, 1);
                    model.setValueAt(custoVariavel, selectedRow, 2);
                    model.setValueAt(custoFixo, selectedRow, 3);
                    model.setValueAt(margemLucro, selectedRow, 4);
                    model.setValueAt(dataProcessamento, selectedRow, 5);
                } else {
                    long newId = model.getRowCount() > 0 ? (long) model.getValueAt(model.getRowCount() - 1, 0) + 1 : 1L;
                    model.addRow(new Object[]{newId, imposto, custoVariavel, custoFixo, margemLucro, dataProcessamento});
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel1, "Erro ao converter os valores. Verifique o formato dos dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JPanel getCustoPanel() {
        return panel1;
    }
}
