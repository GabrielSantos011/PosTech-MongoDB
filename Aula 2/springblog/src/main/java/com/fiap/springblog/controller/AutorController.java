package com.fiap.springblog.controller;

import com.fiap.springblog.model.Autor;
import com.fiap.springblog.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping(value = "/todos")
    public List<Autor> obterTodos() {
        return this.autorService.obterTodos();
    }

    @GetMapping(value = "/{codigo}")
    public Autor obterPorCodigo(@PathVariable String codigo) {
        return this.autorService.obterPorCodigo(codigo);
    }

    @PostMapping(value = "criar")
    public Autor criar(@RequestBody Autor autor) {
        return this.autorService.criar(autor);
    }

}
