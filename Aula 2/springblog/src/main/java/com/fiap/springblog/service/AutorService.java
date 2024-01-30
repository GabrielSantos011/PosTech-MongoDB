package com.fiap.springblog.service;

import com.fiap.springblog.model.Autor;

import java.util.List;

public interface AutorService {

    List<Autor> obterTodos();
    Autor obterPorCodigo(String id);
    Autor criar(Autor autor);

}
