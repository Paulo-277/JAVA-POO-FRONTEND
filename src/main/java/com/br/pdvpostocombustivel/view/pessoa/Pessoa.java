package com.br.pdvpostocombustivel.view.pessoa;

import com.br.pdvpostocombustivel.model.pessoa.PessoaRequest;
import com.br.pdvpostocombustivel.services.PessoaService;
import com.br.pdvpostocombustivel.enums.TipoPessoa;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
public class Pessoa {
    private JPanel pessoaPanel;
    private JTable tPessoa;
    private JButton ADICIONARButton;
    private JButton EDITARButton;
    private JButton EXCLUIRButton;
    private final PessoaService pessoaService;

    public Pessoa(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
        initComponents();

        ADICIONARButton.addActionListener(e -> {
            showPessoaDialog(null);
        });

        EDITARButton.addActionListener(e -> {
            int selectedRow = tPessoa.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(pessoaPanel, "Selecione uma pessoa para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            showPessoaDialog(selectedRow);
        });

        EXCLUIRButton.addActionListener(e -> {
            int selectedRow = tPessoa.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(pessoaPanel, "Selecione uma pessoa para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(pessoaPanel, "Tem certeza que deseja excluir a pessoa selecionada?", "Excluir Pessoa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) tPessoa.getModel();
                Long id = (Long) model.getValueAt(selectedRow, 0);
                pessoaService.deletePessoa(id);
                refreshTable();
            }
        });
    }

    private void initComponents() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nome Completo");
        model.addColumn("CPF/CNPJ");
        model.addColumn("Número CTPS");
        model.addColumn("Data Nascimento");
        model.addColumn("Tipo Pessoa");
        tPessoa.setModel(model); 
        refreshTable();
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) tPessoa.getModel();
        model.setRowCount(0); // Limpa a tabela
        pessoaService.getAllPessoas().forEach(pessoa -> {
            model.addRow(new Object[]{
                pessoa.id(), 
                pessoa.nomeCompleto(), 
                pessoa.cpfCnpj(), 
                pessoa.numeroCtps(),
                pessoa.dataNascimento(),
                pessoa.tipoPessoa()
            });
        });
    }

    private void showPessoaDialog(Integer selectedRow) {
        JTextField nomeCompletoField = new JTextField(20);
        JTextField cpfCnpjField = new JTextField(20);
        JTextField numeroCtpsField = new JTextField(20);
        JTextField dataNascimentoField = new JTextField(20);
        JComboBox<TipoPessoa> tipoPessoaComboBox = new JComboBox<>(TipoPessoa.values());

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Nome Completo:"));
        myPanel.add(nomeCompletoField);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("CPF/CNPJ:"));
        myPanel.add(cpfCnpjField);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("Número CTPS:"));
        myPanel.add(numeroCtpsField);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("Data Nascimento (YYYY-MM-DD):"));
        myPanel.add(dataNascimentoField);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("Tipo Pessoa:"));
        myPanel.add(tipoPessoaComboBox);

        DefaultTableModel model = (DefaultTableModel) tPessoa.getModel();
        String title;

        if (selectedRow != null) {
            title = "Editar Pessoa";
            nomeCompletoField.setText((String) model.getValueAt(selectedRow, 1));
            cpfCnpjField.setText((String) model.getValueAt(selectedRow, 2));
            // numeroCtps pode ser null, então precisamos verificar
            Object ctpsValue = model.getValueAt(selectedRow, 3);
            if (ctpsValue != null) {
                numeroCtpsField.setText(String.valueOf(ctpsValue));
            }
            dataNascimentoField.setText((String) model.getValueAt(selectedRow, 4));
            
            // Handle null for tipoPessoa from table model
            Object tipoPessoaValue = model.getValueAt(selectedRow, 5);
            if (tipoPessoaValue != null) {
                try {
                    tipoPessoaComboBox.setSelectedItem(TipoPessoa.valueOf((String) tipoPessoaValue));
                } catch (IllegalArgumentException ex) {
                    // Handle case where enum value might not match
                    tipoPessoaComboBox.setSelectedIndex(-1); // No selection
                }
            } else {
                tipoPessoaComboBox.setSelectedIndex(-1); // No selection if value is null
            }
        } else {
            title = "Adicionar Pessoa";
        }

        int result = JOptionPane.showConfirmDialog(null, myPanel, title, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nomeCompleto = nomeCompletoField.getText();
            String cpfCnpj = cpfCnpjField.getText();
            Long numeroCtps = null;
            if (!numeroCtpsField.getText().trim().isEmpty()) {
                try {
                    numeroCtps = Long.parseLong(numeroCtpsField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(pessoaPanel, "Número CTPS inválido. Deve ser um número.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            String dataNascimento = dataNascimentoField.getText();
            String tipoPessoa = ((TipoPessoa) tipoPessoaComboBox.getSelectedItem()).name();

            if (nomeCompleto != null && !nomeCompleto.trim().isEmpty() &&
                cpfCnpj != null && !cpfCnpj.trim().isEmpty() &&
                dataNascimento != null && !dataNascimento.trim().isEmpty() &&
                tipoPessoa != null && !tipoPessoa.trim().isEmpty()) {
                
                // Basic date format validation
                try {
                    LocalDate.parse(dataNascimento);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(pessoaPanel, "Formato de Data de Nascimento inválido. Use YYYY-MM-DD.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (selectedRow != null) {
                    Long id = (Long) model.getValueAt(selectedRow, 0);
                    pessoaService.updatePessoa(id, new PessoaRequest(nomeCompleto, cpfCnpj, numeroCtps, dataNascimento, tipoPessoa));
                } else {
                    pessoaService.createPessoa(new PessoaRequest(nomeCompleto, cpfCnpj, numeroCtps, dataNascimento, tipoPessoa));
                }
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(pessoaPanel, "Nome Completo, CPF/CNPJ, Data de Nascimento e Tipo de Pessoa são campos obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JPanel getPessoaPanel() {
        return pessoaPanel;
    }
}
