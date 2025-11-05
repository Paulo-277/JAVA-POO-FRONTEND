package com.br.pdvpostocombustivel.view.pessoa;

import com.br.pdvpostocombustivel.enums.TipoPessoa;
import com.br.pdvpostocombustivel.model.pessoa.PessoaRequest;
import com.br.pdvpostocombustivel.services.PessoaService;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class Pessoa {
    private JPanel pessoaPanel;
    private JTable tPessoa;
    private JButton EDITARButton;
    private JButton EXCLUIRButton;
    private JButton ADICIONARButton;
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
            DefaultTableModel model = (DefaultTableModel) tPessoa.getModel();
            Long id = (Long) model.getValueAt(selectedRow, 0);
            if (id == null) {
                JOptionPane.showMessageDialog(pessoaPanel, "O ID da pessoa selecionada é inválido. Verifique se o backend foi reiniciado.", "Erro de Dados", JOptionPane.ERROR_MESSAGE);
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

            DefaultTableModel model = (DefaultTableModel) tPessoa.getModel();
            Long id = (Long) model.getValueAt(selectedRow, 0);
            if (id == null) {
                JOptionPane.showMessageDialog(pessoaPanel, "O ID da pessoa selecionada é inválido. Não é possível excluir.", "Erro de Dados", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(pessoaPanel, "Tem certeza que deseja excluir a pessoa selecionada?", "Excluir Pessoa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
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
        model.addColumn("Nº CTPS");
        model.addColumn("Data de Nascimento");
        model.addColumn("Tipo de Pessoa");
        tPessoa.setModel(model);
        refreshTable();
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) tPessoa.getModel();
        model.setRowCount(0); // Limpa a tabela
        pessoaService.getAllPessoas().forEach(pessoa -> {
            model.addRow(new Object[]{pessoa.id(), pessoa.nomeCompleto(), pessoa.cpfCnpj(), pessoa.numeroCtps(), pessoa.dataNascimento(), pessoa.tipoPessoa()});
        });
    }

    private void showPessoaDialog(Integer selectedRow) {
        JTextField nomeCompletoField = new JTextField(30);
        JTextField cpfCnpjField = new JTextField(20);
        JTextField numeroCtpsField = new JTextField(10);
        JTextField dataNascimentoField = new JTextField(10);
        JComboBox<TipoPessoa> tipoPessoaComboBox = new JComboBox<>(TipoPessoa.values());

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Nome Completo:"));
        myPanel.add(nomeCompletoField);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("CPF/CNPJ:"));
        myPanel.add(cpfCnpjField);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("Nº CTPS (opcional):"));
        myPanel.add(numeroCtpsField);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("Data de Nascimento (yyyy-MM-dd):"));
        myPanel.add(dataNascimentoField);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("Tipo de Pessoa:"));
        myPanel.add(tipoPessoaComboBox);

        DefaultTableModel model = (DefaultTableModel) tPessoa.getModel();
        String title;

        if (selectedRow != null) {
            title = "Editar Pessoa";
            nomeCompletoField.setText((String) model.getValueAt(selectedRow, 1));
            cpfCnpjField.setText((String) model.getValueAt(selectedRow, 2));
            numeroCtpsField.setText(model.getValueAt(selectedRow, 3) != null ? model.getValueAt(selectedRow, 3).toString() : "");
            dataNascimentoField.setText(model.getValueAt(selectedRow, 4) != null ? model.getValueAt(selectedRow, 4).toString() : "");
            tipoPessoaComboBox.setSelectedItem(model.getValueAt(selectedRow, 5));
        } else {
            title = "Adicionar Pessoa";
        }

        int result = JOptionPane.showConfirmDialog(null, myPanel, title, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nomeCompleto = nomeCompletoField.getText();
                String cpfCnpj = cpfCnpjField.getText();
                Long numeroCtps = numeroCtpsField.getText().isEmpty() ? null : Long.parseLong(numeroCtpsField.getText());
                LocalDate dataNascimento = LocalDate.parse(dataNascimentoField.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
                TipoPessoa tipoPessoa = (TipoPessoa) tipoPessoaComboBox.getSelectedItem();

                if (selectedRow != null) {
                    Long id = (Long) model.getValueAt(selectedRow, 0);
                    pessoaService.updatePessoa(id, new PessoaRequest(nomeCompleto, cpfCnpj, numeroCtps, dataNascimento, tipoPessoa));
                } else {
                    pessoaService.createPessoa(new PessoaRequest(nomeCompleto, cpfCnpj, numeroCtps, dataNascimento, tipoPessoa));
                }
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(pessoaPanel, "Erro ao converter os valores. Verifique o formato dos dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JPanel getPessoaPanel() {
        return pessoaPanel;
    }
}
