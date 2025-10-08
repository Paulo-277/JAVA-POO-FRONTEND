package com.br.pdvpostocombustivel.view.acesso;

import com.br.pdvpostocombustivel.model.Acesso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AcessoCrudFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private List<Acesso> acessos = new ArrayList<>();
    private long nextId = 1;

    public AcessoCrudFrame() {
        setTitle("Gerenciamento de Acessos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tabela
        String[] columnNames = {"ID", "Usuário", "Senha"};
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
        addButton.addActionListener(e -> adicionarAcesso());
        editButton.addActionListener(e -> editarAcesso());
        deleteButton.addActionListener(e -> excluirAcesso());

        // Carregar dados iniciais
        carregarDadosIniciais();
        atualizarTabela();
    }

    private void carregarDadosIniciais() {
        acessos.add(new Acesso(nextId++, "admin", "admin123"));
        acessos.add(new Acesso(nextId++, "gerente", "gerente123"));
        acessos.add(new Acesso(nextId++, "caixa", "caixa123"));
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        for (Acesso acesso : acessos) {
            tableModel.addRow(new Object[]{
                    acesso.getId(),
                    acesso.getUsuario(),
                    acesso.getSenha()
            });
        }
    }

    private void adicionarAcesso() {
        AcessoFormDialog dialog = new AcessoFormDialog(this, null);
        dialog.setVisible(true);

        Optional<Acesso> novoAcessoOpt = dialog.getAcesso();
        if (novoAcessoOpt.isPresent()) {
            Acesso novoAcesso = novoAcessoOpt.get();
            novoAcesso.setId(nextId++);
            acessos.add(novoAcesso);
            atualizarTabela();
        }
    }

    private void editarAcesso() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um acesso para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) tableModel.getValueAt(selectedRow, 0);
        Acesso acessoSelecionado = acessos.stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);

        if (acessoSelecionado != null) {
            AcessoFormDialog dialog = new AcessoFormDialog(this, acessoSelecionado);
            dialog.setVisible(true);

            Optional<Acesso> acessoEditadoOpt = dialog.getAcesso();
            if (acessoEditadoOpt.isPresent()) {
                Acesso acessoEditado = acessoEditadoOpt.get();
                acessoSelecionado.setUsuario(acessoEditado.getUsuario());
                acessoSelecionado.setSenha(acessoEditado.getSenha());
                atualizarTabela();
            }
        }
    }

    private void excluirAcesso() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um acesso para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o acesso selecionado?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            acessos.removeIf(a -> a.getId().equals(id));
            atualizarTabela();
        }
    }
}
