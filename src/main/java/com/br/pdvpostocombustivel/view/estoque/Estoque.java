package com.br.pdvpostocombustivel.view.estoque;

import com.br.pdvpostocombustivel.model.estoque.EstoqueRequest;
import com.br.pdvpostocombustivel.services.EstoqueService;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class Estoque {
    private JPanel panel1;
    private JButton EDITARButton;
    private JButton EXCLUIRButton;
    private JButton ADICIONARButton;
    private JTable tEstoque;
    private final EstoqueService estoqueService;

    public Estoque(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
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
                Long id = (Long) model.getValueAt(selectedRow, 0);
                estoqueService.deleteEstoque(id);
                refreshTable();
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
        tEstoque.setModel(model);
        refreshTable();
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) tEstoque.getModel();
        model.setRowCount(0); // Limpa a tabela
        estoqueService.getAllEstoques().forEach(estoque -> {
            model.addRow(new Object[]{estoque.id(), estoque.quantidade(), estoque.localTanque(), estoque.localEndereco(), estoque.loteFabricacao(), estoque.dataValidade()});
        });
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
                    Long id = (Long) model.getValueAt(selectedRow, 0);
                    estoqueService.updateEstoque(id, new EstoqueRequest(quantidade, localTanque, localEndereco, loteFabricacao, dataValidade));
                } else {
                    estoqueService.createEstoque(new EstoqueRequest(quantidade, localTanque, localEndereco, loteFabricacao, dataValidade));
                }
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel1, "Erro ao converter os valores. Verifique o formato dos dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JPanel getEstoquePanel() {
        return panel1;
    }
}
