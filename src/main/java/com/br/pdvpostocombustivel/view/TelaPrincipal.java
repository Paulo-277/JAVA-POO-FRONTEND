package com.br.pdvpostocombustivel.view;

import com.br.pdvpostocombustivel.view.acesso.AcessoCrudFrame;
import com.br.pdvpostocombustivel.view.contato.ContatoCrudFrame;
import com.br.pdvpostocombustivel.view.custo.CustoCrudFrame;
import com.br.pdvpostocombustivel.view.estoque.EstoqueCrudFrame;
import com.br.pdvpostocombustivel.view.pessoa.PessoaCrudFrame;
import com.br.pdvpostocombustivel.view.preco.PrecoCrudFrame;
import com.br.pdvpostocombustivel.view.produto.ProdutoCrudFrame;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("PDV Posto de Combustível - Menu Principal");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal com GridLayout
        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botões para as entidades principais
        JButton pessoasButton = new JButton("Gerenciar Pessoas");
        JButton produtosButton = new JButton("Gerenciar Produtos");
        JButton estoqueButton = new JButton("Gerenciar Estoque");
        JButton custosButton = new JButton("Gerenciar Custos");
        JButton precosButton = new JButton("Gerenciar Preços");
        JButton acessosButton = new JButton("Gerenciar Acessos");
        JButton contatosButton = new JButton("Gerenciar Contatos");

        // Adicionar botões ao painel
        panel.add(pessoasButton);
        panel.add(produtosButton);
        panel.add(estoqueButton);
        panel.add(custosButton);
        panel.add(precosButton);
        panel.add(acessosButton);
        panel.add(contatosButton);

        // Adicionar painel à janela
        add(panel);

        // Ações dos botões
        pessoasButton.addActionListener(e -> new PessoaCrudFrame().setVisible(true));
        produtosButton.addActionListener(e -> new ProdutoCrudFrame().setVisible(true));
        estoqueButton.addActionListener(e -> new EstoqueCrudFrame().setVisible(true));
        custosButton.addActionListener(e -> new CustoCrudFrame().setVisible(true));
        precosButton.addActionListener(e -> new PrecoCrudFrame().setVisible(true));
        acessosButton.addActionListener(e -> new AcessoCrudFrame().setVisible(true));
        contatosButton.addActionListener(e -> new ContatoCrudFrame().setVisible(true));
    }
}
