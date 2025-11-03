package com.br.pdvpostocombustivel.view.contato;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Contato {
    private JPanel panel1;
    private JButton ADICIONARButton;
    private JButton EDITARButton;
    private JButton EXCLUIRButton;
    private JTable tContato;

    public Contato() {
        initComponents();

        ADICIONARButton.addActionListener(e -> {
            showContatoDialog(null);
        });

        EDITARButton.addActionListener(e -> {
            int selectedRow = tContato.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel1, "Selecione um contato para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            showContatoDialog(selectedRow);
        });

        EXCLUIRButton.addActionListener(e -> {
            int selectedRow = tContato.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel1, "Selecione um contato para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(panel1, "Tem certeza que deseja excluir o contato selecionado?", "Excluir Contato", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) tContato.getModel();
                model.removeRow(selectedRow);
            }
        });
    }

    private void initComponents() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Telefone");
        model.addColumn("Email");
        model.addColumn("Endereço");

        model.addRow(new Object[]{1L, "(11) 99999-9999", "cliente1@email.com", "Rua A, 123"});
        model.addRow(new Object[]{2L, "(22) 88888-8888", "cliente2@email.com", "Rua B, 456"});

        tContato.setModel(model);
    }

    private void showContatoDialog(Integer selectedRow) {
        JTextField telefoneField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField enderecoField = new JTextField(30);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Telefone:"));
        myPanel.add(telefoneField);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("Email:"));
        myPanel.add(emailField);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("Endereço:"));
        myPanel.add(enderecoField);

        DefaultTableModel model = (DefaultTableModel) tContato.getModel();
        String title;

        if (selectedRow != null) {
            title = "Editar Contato";
            telefoneField.setText((String) model.getValueAt(selectedRow, 1));
            emailField.setText((String) model.getValueAt(selectedRow, 2));
            enderecoField.setText((String) model.getValueAt(selectedRow, 3));
        } else {
            title = "Adicionar Contato";
        }

        int result = JOptionPane.showConfirmDialog(null, myPanel, title, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String telefone = telefoneField.getText();
            String email = emailField.getText();
            String endereco = enderecoField.getText();

            if (telefone != null && !telefone.trim().isEmpty() && email != null && !email.trim().isEmpty() && endereco != null && !endereco.trim().isEmpty()) {
                if (selectedRow != null) {
                    model.setValueAt(telefone, selectedRow, 1);
                    model.setValueAt(email, selectedRow, 2);
                    model.setValueAt(endereco, selectedRow, 3);
                } else {
                    long newId = model.getRowCount() > 0 ? (long) model.getValueAt(model.getRowCount() - 1, 0) + 1 : 1L;
                    model.addRow(new Object[]{newId, telefone, email, endereco});
                }
            } else {
                JOptionPane.showMessageDialog(panel1, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JPanel getContatoPanel() {
        return panel1;
    }
}
