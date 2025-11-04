package com.br.pdvpostocombustivel.view;

import com.br.pdvpostocombustivel.view.acesso.Acesso;
import com.br.pdvpostocombustivel.view.contato.Contato;
import com.br.pdvpostocombustivel.view.custo.Custo;
import com.br.pdvpostocombustivel.view.estoque.Estoque;
import com.br.pdvpostocombustivel.view.pessoa.Pessoa;
import com.br.pdvpostocombustivel.view.preco.Preco;
import com.br.pdvpostocombustivel.view.produto.Produto;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
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

    private final ApplicationContext context;

    public TelaPrincipal(ApplicationContext context) {
        this.context = context;
        setTitle("Menu Principal");
        setContentPane(TelaPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        seleAcesso.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Acesso", true);
            Acesso acessoPanel = context.getBean(Acesso.class);
            dialog.setContentPane(acessoPanel.getAcessoPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        seleContato.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Contato", true);
            Contato contatoPanel = context.getBean(Contato.class);
            dialog.setContentPane(contatoPanel.getContatoPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        seleEstoque.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Estoque", true);
            Estoque estoquePanel = new Estoque(); // Still direct instantiation
            dialog.setContentPane(estoquePanel.getEstoquePanel());
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        seleCusto.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Custo", true);
            Custo custoPanel = new Custo(); // Still direct instantiation
            dialog.setContentPane(custoPanel.getCustoPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        selePessoa.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Pessoa", true);
            Pessoa pessoaPanel = new Pessoa(); // Still direct instantiation
            dialog.setContentPane(pessoaPanel.getPessoaPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        selePreco.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Preco", true);
            Preco precoPanel = new Preco(); // Still direct instantiation
            dialog.setContentPane(precoPanel.getPrecoPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        seleProduto.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Produto", true);
            Produto produtoPanel = new Produto(); // Still direct instantiation
            dialog.setContentPane(produtoPanel.getProdutoPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });
    }
}
