package com.br.pdvpostocombustivel.view;

import com.br.pdvpostocombustivel.view.pessoa.PessoaCrudFrame; // Importação corrigida

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("PDV Posto de Combustível - Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal com GridLayout
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botões para as entidades principais
        JButton pessoasButton = new JButton("Gerenciar Pessoas");
        JButton produtosButton = new JButton("Gerenciar Produtos");
        JButton estoqueButton = new JButton("Gerenciar Estoque");
        JButton custosButton = new JButton("Gerenciar Custos");

        // Adicionar botões ao painel
        panel.add(pessoasButton);
        panel.add(produtosButton);
        panel.add(estoqueButton);
        panel.add(custosButton);

        // Adicionar painel à janela
        add(panel);

        // Ações dos botões
        pessoasButton.addActionListener(e -> {
            PessoaCrudFrame pessoaCrudFrame = new PessoaCrudFrame();
            pessoaCrudFrame.setVisible(true);
        });

        // Ações para os outros botões (ainda não implementado)
        produtosButton.addActionListener(e -> showNotImplementedMessage());
        estoqueButton.addActionListener(e -> showNotImplementedMessage());
        custosButton.addActionListener(e -> showNotImplementedMessage());
    }

    private void showNotImplementedMessage() {
        JOptionPane.showMessageDialog(this, "Funcionalidade ainda não implementada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }
}
