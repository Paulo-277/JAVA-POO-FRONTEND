package com.br.pdvpostocombustivel.model;

import java.util.List;

// A simple generic class to capture the 'content' field from Spring's Page object
public class PageableResponse<T> {
    private List<T> content;

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
