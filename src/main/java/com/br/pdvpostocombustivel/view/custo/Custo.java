package com.br.pdvpostocombustivel.view.custo;

import com.br.pdvpostocombustivel.model.custo.CustoRequest;
import com.br.pdvpostocombustivel.services.CustoService;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class Custo {
    private JPanel panel1;
    private JTable tCusto;
    private JButton EDITARButton;
    private JButton EXCLUIRButton;
    private JButton ADICIONARButton;
    private final CustoService custoService;

    public Custo(CustoService custoService) {
        this.custoService = custoService;
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
                Long id = (Long) model.getValueAt(selectedRow, 0);
                custoService.deleteCusto(id);
                refreshTable();
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
        tCusto.setModel(model);
        refreshTable();
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) tCusto.getModel();
        model.setRowCount(0); // Limpa a tabela
        custoService.getAllCustos().forEach(custo -> {
            model.addRow(new Object[]{custo.id(), custo.imposto(), custo.custoVariavel(), custo.custoFixo(), custo.margemLucro(), custo.dataProcessamento()});
        });
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
                    Long id = (Long) model.getValueAt(selectedRow, 0);
                    custoService.updateCusto(id, new CustoRequest(imposto, custoVariavel, custoFixo, margemLucro, dataProcessamento));
                } else {
                    custoService.createCusto(new CustoRequest(imposto, custoVariavel, custoFixo, margemLucro, dataProcessamento));
                }
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel1, "Erro ao converter os valores. Verifique o formato dos dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JPanel getCustoPanel() {
        return panel1;
    }
}
