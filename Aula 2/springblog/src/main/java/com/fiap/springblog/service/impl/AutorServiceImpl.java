package com.fiap.springblog.service.impl;

import com.fiap.springblog.model.Autor;
import com.fiap.springblog.repository.AutorRepository;
import com.fiap.springblog.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorServiceImpl implements AutorService {

    @Autowired
    private AutorRepository autorRepository;
    @Override
    public List<Autor> obterTodos() {
        return this.autorRepository.findAll();
    }

    @Override
    public Autor obterPorCodigo(String id) {
        return this.autorRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("Autor não existe") );
    }

    @Override
    public Autor criar(Autor autor) {
        return this.autorRepository.save(autor);
    }

}
