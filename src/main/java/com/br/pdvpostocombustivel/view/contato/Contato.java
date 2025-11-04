package com.br.pdvpostocombustivel.view.contato;

import com.br.pdvpostocombustivel.model.contato.ContatoRequest;
import com.br.pdvpostocombustivel.services.ContatoService;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@Component
public class Contato {
    private JPanel panel1;
    private JButton ADICIONARButton;
    private JButton EDITARButton;
    private JButton EXCLUIRButton;
    private JTable tContato;
    private final ContatoService contatoService;

    public Contato(ContatoService contatoService) {
        this.contatoService = contatoService;
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
                Long id = (Long) model.getValueAt(selectedRow, 0);
                contatoService.deleteContato(id);
                refreshTable();
            }
        });
    }

    private void initComponents() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Telefone");
        model.addColumn("Email");
        model.addColumn("Endereço");
        tContato.setModel(model);
        refreshTable();
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) tContato.getModel();
        model.setRowCount(0); // Limpa a tabela
        contatoService.getAllContatos().forEach(contato -> {
            model.addRow(new Object[]{contato.id(), contato.telefone(), contato.email(), contato.endereco()});
        });
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
                    Long id = (Long) model.getValueAt(selectedRow, 0);
                    contatoService.updateContato(id, new ContatoRequest(telefone, email, endereco));
                } else {
                    contatoService.createContato(new ContatoRequest(telefone, email, endereco));
                }
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(panel1, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JPanel getContatoPanel() {
        return panel1;
    }
}
