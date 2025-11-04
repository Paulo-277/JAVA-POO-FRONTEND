package com.br.pdvpostocombustivel;

import com.br.pdvpostocombustivel.view.TelaPrincipal;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PdvpostocombustivelApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(PdvpostocombustivelApplication.class)
                .headless(false)
                .web(WebApplicationType.NONE)
                .run(args);

        SwingUtilities.invokeLater(() -> {
            TelaPrincipal frame = context.getBean(TelaPrincipal.class);
            frame.setVisible(true);
        });
    }
}
