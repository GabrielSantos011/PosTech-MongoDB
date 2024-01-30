package com.fiap.springblog.controller;

import com.fiap.springblog.model.Artigo;
import com.fiap.springblog.service.ArtigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/artigos")
public class ArtigoController {

    @Autowired
    private ArtigoService artigoService;

    @GetMapping(value = "/todos")
    public List<Artigo> obterTodos() {
        return this.artigoService.obterTodos();
    }

    @GetMapping(value = "/{codigo}")
    public Artigo obterPorCodigo(@PathVariable String codigo) {
        return this.artigoService.obterPorCodigo(codigo);
    }

    @PostMapping(value = "/criar")
    public Artigo criar(@RequestBody Artigo artigo) {
        return this.artigoService.criar(artigo);
    }

    @GetMapping(value = "/dataMaiorQue")
    public List<Artigo> encontrarArtigoComDataMaiorQue(@RequestParam("data")LocalDateTime data) {
        return this.artigoService.encontrarArtigoComDataMaiorQue(data);
    }

    @GetMapping(value = "/dataEAutor")
    public List<Artigo> encontrarArtigoComDataEQueTenhaAutor(@RequestParam("data")LocalDateTime data, @RequestParam("codigoAutor") String codigoAutor) {
        return this.artigoService.encontrarArtigoComDataEQueTenhaAutor(data, codigoAutor);
    }

    @PutMapping(value = "/atualiza")
    public void atualiza(@RequestBody Artigo artigo) {
        this.artigoService.atualiza(artigo);
    }

    @PutMapping(value = "/atualizaUrl")
    public void atualiza(@RequestParam("codigo") String codigo, @RequestParam("url") String url) {
        this.artigoService.atualizaSoUrl(codigo, url);
    }

    @DeleteMapping(value = "delete/{id}")
    public void delete(@PathVariable String id) {
        this.artigoService.delete(id);
    }

    @DeleteMapping(value = "deleteViaMongoTemplate/{id}")
    public void deleteTemplate(@PathVariable String id) {
        this.artigoService.deleteTemplate(id);
    }

}
