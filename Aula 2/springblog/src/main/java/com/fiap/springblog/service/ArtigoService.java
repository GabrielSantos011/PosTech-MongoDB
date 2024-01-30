package com.fiap.springblog.service;

import com.fiap.springblog.model.Artigo;

import java.time.LocalDateTime;
import java.util.List;

public interface ArtigoService {

    List<Artigo> obterTodos();
    Artigo obterPorCodigo(String id);
    Artigo criar(Artigo artigo);
    List<Artigo> encontrarArtigoComDataMaiorQue(LocalDateTime data);
    List<Artigo> encontrarArtigoComDataEQueTenhaAutor(LocalDateTime data, String codigoAutor);
    void atualiza(Artigo novoArtigo);
    void atualizaSoUrl(String id, String novaUrl);
    void delete(String id);
    void deleteTemplate(String id);

}
