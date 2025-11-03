package com.br.pdvpostocombustivel.view.acesso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Acesso {
    private JPanel acessoPanel;
    private JTable tAcesso;
    private JButton addBut;
    private JButton editBut;
    private JButton apagBut;

    public Acesso() {
        initComponents();

        addBut.addActionListener(e -> {
            showAcessoDialog(null);
        });

        editBut.addActionListener(e -> {
            int selectedRow = tAcesso.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(acessoPanel, "Selecione um acesso para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            showAcessoDialog(selectedRow);
        });

        apagBut.addActionListener(e -> {
            int selectedRow = tAcesso.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(acessoPanel, "Selecione um acesso para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(acessoPanel, "Tem certeza que deseja excluir o acesso selecionado?", "Excluir Acesso", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) tAcesso.getModel();
                model.removeRow(selectedRow);
            }
        });
    }

    private void initComponents() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Usuário");
        model.addColumn("Senha");

        model.addRow(new Object[]{1L, "admin", "admin123"});
        model.addRow(new Object[]{2L, "user", "user123"});

        tAcesso.setModel(model);
    }

    private void showAcessoDialog(Integer selectedRow) {
        JTextField usuarioField = new JTextField(20);
        JTextField senhaField = new JTextField(20);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Usuário:"));
        myPanel.add(usuarioField);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("Senha:"));
        myPanel.add(senhaField);

        DefaultTableModel model = (DefaultTableModel) tAcesso.getModel();
        String title;

        if (selectedRow != null) {
            title = "Editar Acesso";
            usuarioField.setText((String) model.getValueAt(selectedRow, 1));
            senhaField.setText((String) model.getValueAt(selectedRow, 2));
        } else {
            title = "Adicionar Acesso";
        }

        int result = JOptionPane.showConfirmDialog(null, myPanel, title, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String usuario = usuarioField.getText();
            String senha = senhaField.getText();

            if (usuario != null && !usuario.trim().isEmpty() && senha != null && !senha.trim().isEmpty()) {
                if (selectedRow != null) {
                    model.setValueAt(usuario, selectedRow, 1);
                    model.setValueAt(senha, selectedRow, 2);
                } else {
                    long newId = model.getRowCount() > 0 ? (long) model.getValueAt(model.getRowCount() - 1, 0) + 1 : 1L;
                    model.addRow(new Object[]{newId, usuario, senha});
                }
            } else {
                JOptionPane.showMessageDialog(acessoPanel, "Usuário e Senha não podem ser vazios.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JPanel getAcessoPanel() {
        return acessoPanel;
    }
}
