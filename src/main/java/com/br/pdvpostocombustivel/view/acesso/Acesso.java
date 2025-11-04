package com.br.pdvpostocombustivel.view.acesso;

import com.br.pdvpostocombustivel.model.acesso.AcessoRequest;
import com.br.pdvpostocombustivel.services.AcessoService;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@Component
public class Acesso {
    private JPanel acessoPanel;
    private JTable tAcesso;
    private JButton addBut;
    private JButton editBut;
    private JButton apagBut;
    private final AcessoService acessoService;

    public Acesso(AcessoService acessoService) {
        this.acessoService = acessoService;
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
                Long id = (Long) model.getValueAt(selectedRow, 0);
                acessoService.deleteAcesso(id);
                refreshTable();
            }
        });
    }

    private void initComponents() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Usuário");
        model.addColumn("Senha");
        tAcesso.setModel(model);
        refreshTable();
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) tAcesso.getModel();
        model.setRowCount(0); // Limpa a tabela
        acessoService.getAllAcessos().forEach(acesso -> {
            model.addRow(new Object[]{acesso.id(), acesso.usuario(), acesso.senha()});
        });
    }

    private void showAcessoDialog(Integer selectedRow) {
        JTextField usuarioField = new JTextField(20);
        JPasswordField senhaField = new JPasswordField(20);

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
            // A senha não é preenchida por segurança
        } else {
            title = "Adicionar Acesso";
        }

        int result = JOptionPane.showConfirmDialog(null, myPanel, title, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String usuario = usuarioField.getText();
            String senha = new String(senhaField.getPassword());

            if (usuario != null && !usuario.trim().isEmpty()) {
                if (selectedRow != null) {
                    Long id = (Long) model.getValueAt(selectedRow, 0);
                    acessoService.updateAcesso(id, new AcessoRequest(usuario, senha));
                } else {
                    acessoService.createAcesso(new AcessoRequest(usuario, senha));
                }
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(acessoPanel, "O nome de usuário não pode ser vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JPanel getAcessoPanel() {
        return acessoPanel;
    }
}
