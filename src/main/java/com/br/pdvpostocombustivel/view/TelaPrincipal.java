package com.br.pdvpostocombustivel.view;

import com.br.pdvpostocombustivel.view.acesso.Acesso;
import com.br.pdvpostocombustivel.view.contato.Contato;
import com.br.pdvpostocombustivel.view.custo.Custo;
import com.br.pdvpostocombustivel.view.estoque.Estoque;
import com.br.pdvpostocombustivel.view.pessoa.Pessoa;
import com.br.pdvpostocombustivel.view.preco.Preco;
import com.br.pdvpostocombustivel.view.produto.Produto;

import javax.swing.*;

public class TelaPrincipal extends JFrame {
    private JPanel TelaPrincipal;
    private JLabel selecioneAClasseLabel;
    private JButton seleAcesso;
    private JButton seleContato;
    private JButton seleEstoque;
    private JButton seleCusto;
    private JButton selePessoa;
    private JButton selePreco;
    private JButton seleProduto;
    private JButton SIMULADORDEBOMBASButton;

    public TelaPrincipal() {
        setTitle("Menu Principal");
        setContentPane(TelaPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        seleAcesso.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Acesso", true);
            Acesso acessoPanel = new Acesso();
            dialog.setContentPane(acessoPanel.getAcessoPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        seleContato.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Contato", true);
            Contato contatoPanel = new Contato();
            dialog.setContentPane(contatoPanel.getContatoPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        seleEstoque.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Estoque", true);
            Estoque estoquePanel = new Estoque();
            dialog.setContentPane(estoquePanel.getEstoquePanel());
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        seleCusto.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Custo", true);
            Custo custoPanel = new Custo();
            dialog.setContentPane(custoPanel.getCustoPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        selePessoa.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Pessoa", true);
            Pessoa pessoaPanel = new Pessoa();
            dialog.setContentPane(pessoaPanel.getPessoaPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        selePreco.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Preco", true);
            Preco precoPanel = new Preco();
            dialog.setContentPane(precoPanel.getPrecoPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        seleProduto.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Produto", true);
            Produto produtoPanel = new Produto();
            dialog.setContentPane(produtoPanel.getProdutoPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });
    }
}
