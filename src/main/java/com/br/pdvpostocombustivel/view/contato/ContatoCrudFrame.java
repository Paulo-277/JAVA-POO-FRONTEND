package com.br.pdvpostocombustivel.view.contato;

import com.br.pdvpostocombustivel.model.Contato;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContatoCrudFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private List<Contato> contatos = new ArrayList<>();
    private long nextId = 1;

    public ContatoCrudFrame() {
        setTitle("Gerenciamento de Contatos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tabela
        String[] columnNames = {"ID", "Telefone", "Email", "Endereço"};
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
        addButton.addActionListener(e -> adicionarContato());
        editButton.addActionListener(e -> editarContato());
        deleteButton.addActionListener(e -> excluirContato());

        // Carregar dados iniciais
        carregarDadosIniciais();
        atualizarTabela();
    }

    private void carregarDadosIniciais() {
        contatos.add(new Contato(nextId++, "(11) 99999-8888", "joao.silva@email.com", "Rua das Flores, 123"));
        contatos.add(new Contato(nextId++, "(21) 88888-7777", "maria.souza@email.com", "Avenida Principal, 456"));
        contatos.add(new Contato(nextId++, "(31) 77777-6666", "empresa.xyz@email.com", "Praça da Matriz, 789"));
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        for (Contato contato : contatos) {
            tableModel.addRow(new Object[]{
                    contato.getId(),
                    contato.getTelefone(),
                    contato.getEmail(),
                    contato.getEndereco()
            });
        }
    }

    private void adicionarContato() {
        ContatoFormDialog dialog = new ContatoFormDialog(this, null);
        dialog.setVisible(true);

        Optional<Contato> novoContatoOpt = dialog.getContato();
        if (novoContatoOpt.isPresent()) {
            Contato novoContato = novoContatoOpt.get();
            novoContato.setId(nextId++);
            contatos.add(novoContato);
            atualizarTabela();
        }
    }

    private void editarContato() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um contato para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) tableModel.getValueAt(selectedRow, 0);
        Contato contatoSelecionado = contatos.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);

        if (contatoSelecionado != null) {
            ContatoFormDialog dialog = new ContatoFormDialog(this, contatoSelecionado);
            dialog.setVisible(true);

            Optional<Contato> contatoEditadoOpt = dialog.getContato();
            if (contatoEditadoOpt.isPresent()) {
                Contato contatoEditado = contatoEditadoOpt.get();
                contatoSelecionado.setTelefone(contatoEditado.getTelefone());
                contatoSelecionado.setEmail(contatoEditado.getEmail());
                contatoSelecionado.setEndereco(contatoEditado.getEndereco());
                atualizarTabela();
            }
        }
    }

    private void excluirContato() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um contato para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o contato selecionado?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            contatos.removeIf(c -> c.getId().equals(id));
            atualizarTabela();
        }
    }
}
