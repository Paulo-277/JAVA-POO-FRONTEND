package com.br.pdvpostocombustivel.view.gerenciadorbomba;

import org.springframework.stereotype.Component;
import javax.swing.*;

@Component
public class Comprovante {
    private JPanel comprovantePanel;
    private JLabel produtoComprado;
    private JLabel quantidadeComprada;
    private JLabel precoCompra;
    private JLabel formaPagamento;

    public Comprovante() {
        // Default constructor
    }

    public void setDados(String produto, String quantidade, String preco, String pagamento) {
        produtoComprado.setText(produto);
        quantidadeComprada.setText(quantidade);
        precoCompra.setText(preco);
        formaPagamento.setText(pagamento);
    }

    public JPanel getComprovantePanel() {
        return comprovantePanel;
    }
}
