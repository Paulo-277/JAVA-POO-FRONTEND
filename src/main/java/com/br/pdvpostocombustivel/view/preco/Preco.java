package com.br.pdvpostocombustivel.view.preco;

import com.br.pdvpostocombustivel.model.preco.PrecoRequest;
import com.br.pdvpostocombustivel.model.preco.PrecoResponse;
import com.br.pdvpostocombustivel.services.PrecoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Preco {
    private JPanel precoPanel;
    private JTable tPreco;
    private JButton EDITARButton;
    private JButton EXCLUIRButton;
    private JButton ADICIONARButton;
    private final PrecoService precoService;

    public Preco() {
        this.precoService = new PrecoService();
        initComponents();
        loadPrecos();

        ADICIONARButton.addActionListener(e -> {
            String valorStr = JOptionPane.showInputDialog(precoPanel, "Digite o novo valor:", "Adicionar Preço", JOptionPane.PLAIN_MESSAGE);
            if (valorStr != null && !valorStr.trim().isEmpty()) {
                try {
                    BigDecimal valor = new BigDecimal(valorStr);
                    PrecoRequest request = new PrecoRequest(valor);
                    precoService.createPreco(request);
                    loadPrecos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(precoPanel, "Valor inválido. Por favor, insira um número.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(precoPanel, "Erro ao adicionar preço: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        EDITARButton.addActionListener(e -> {
            int selectedRow = tPreco.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(precoPanel, "Selecione um preço para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Long id = (Long) tPreco.getValueAt(selectedRow, 0);
            String valorAtual = tPreco.getValueAt(selectedRow, 1).toString();
            String valorStr = JOptionPane.showInputDialog(precoPanel, "Digite o novo valor:", valorAtual);

            if (valorStr != null && !valorStr.trim().isEmpty()) {
                try {
                    BigDecimal valor = new BigDecimal(valorStr);
                    PrecoRequest request = new PrecoRequest(valor);
                    precoService.updatePreco(id, request);
                    loadPrecos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(precoPanel, "Valor inválido. Por favor, insira um número.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(precoPanel, "Erro ao editar preço: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        EXCLUIRButton.addActionListener(e -> {
            int selectedRow = tPreco.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(precoPanel, "Selecione um preço para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(precoPanel, "Tem certeza que deseja excluir o preço selecionado?", "Excluir Preço", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Long id = (Long) tPreco.getValueAt(selectedRow, 0);
                try {
                    precoService.deletePreco(id);
                    loadPrecos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(precoPanel, "Erro ao excluir preço: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
        tPreco.setModel(model);
    }

    private void loadPrecos() {
        try {
            List<PrecoResponse> precos = precoService.getAllPrecos();
            DefaultTableModel model = (DefaultTableModel) tPreco.getModel();
            model.setRowCount(0);
            for (PrecoResponse preco : precos) {
                model.addRow(new Object[]{
                        preco.id(),
                        preco.valor(),
                        preco.dataAlteracao(),
                        preco.horaAlteracao()
                });
            }
        } catch (Exception e) {
            DefaultTableModel model = (DefaultTableModel) tPreco.getModel();
            model.setRowCount(0);
            model.addRow(new Object[]{1L, new BigDecimal("5.50"), LocalDate.now(), LocalTime.now()});
            model.addRow(new Object[]{2L, new BigDecimal("4.80"), LocalDate.now(), LocalTime.now()});
            JOptionPane.showMessageDialog(precoPanel, "Não foi possível carregar os preços do servidor. Carregando dados mockados.\n" + e.getMessage(), "Erro de Conexão", JOptionPane.WARNING_MESSAGE);
        }
    }

    public JPanel getPrecoPanel() {
        return precoPanel;
    }
}
