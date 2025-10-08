package com.br.pdvpostocombustivel.view.custo;

import com.br.pdvpostocombustivel.model.Custo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustoCrudFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private List<Custo> custos = new ArrayList<>();
    private long nextId = 1;

    public CustoCrudFrame() {
        setTitle("Gerenciamento de Custos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tabela
        String[] columnNames = {"ID", "Imposto (%)", "Custo Variável", "Custo Fixo", "Margem Lucro (%)", "Data"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Adicionar");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Excluir");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Ações dos botões
        addButton.addActionListener(e -> adicionarCusto());
        editButton.addActionListener(e -> editarCusto());
        deleteButton.addActionListener(e -> excluirCusto());

        // Carregar dados iniciais
        carregarDadosIniciais();
        atualizarTabela();
    }

    private void carregarDadosIniciais() {
        custos.add(new Custo(nextId++, 17.5, 2.50, 15000.0, 15.0, LocalDate.of(2023, 1, 1)));
        custos.add(new Custo(nextId++, 18.0, 2.60, 15500.0, 15.5, LocalDate.of(2023, 2, 1)));
        custos.add(new Custo(nextId++, 18.5, 2.55, 16000.0, 16.0, LocalDate.of(2023, 3, 1)));
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        for (Custo custo : custos) {
            tableModel.addRow(new Object[]{
                    custo.getId(),
                    custo.getImposto(),
                    custo.getCustoVariavel(),
                    custo.getCustoFixo(),
                    custo.getMargemLucro(),
                    custo.getDataProcessamento()
            });
        }
    }

    private void adicionarCusto() {
        CustoFormDialog dialog = new CustoFormDialog(this, null);
        dialog.setVisible(true);

        Optional<Custo> novoCustoOpt = dialog.getCusto();
        if (novoCustoOpt.isPresent()) {
            Custo novoCusto = novoCustoOpt.get();
            novoCusto.setId(nextId++);
            custos.add(novoCusto);
            atualizarTabela();
        }
    }

    private void editarCusto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um custo para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) tableModel.getValueAt(selectedRow, 0);
        Custo custoSelecionado = custos.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);

        if (custoSelecionado != null) {
            CustoFormDialog dialog = new CustoFormDialog(this, custoSelecionado);
            dialog.setVisible(true);

            Optional<Custo> custoEditadoOpt = dialog.getCusto();
            if (custoEditadoOpt.isPresent()) {
                Custo custoEditado = custoEditadoOpt.get();
                custoSelecionado.setImposto(custoEditado.getImposto());
                custoSelecionado.setCustoVariavel(custoEditado.getCustoVariavel());
                custoSelecionado.setCustoFixo(custoEditado.getCustoFixo());
                custoSelecionado.setMargemLucro(custoEditado.getMargemLucro());
                custoSelecionado.setDataProcessamento(custoEditado.getDataProcessamento());
                atualizarTabela();
            }
        }
    }

    private void excluirCusto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um custo para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o custo selecionado?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            custos.removeIf(c -> c.getId().equals(id));
            atualizarTabela();
        }
    }
}
