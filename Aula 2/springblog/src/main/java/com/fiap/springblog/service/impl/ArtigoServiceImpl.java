package com.fiap.springblog.service.impl;

import com.fiap.springblog.model.Artigo;
import com.fiap.springblog.model.Autor;
import com.fiap.springblog.repository.ArtigoRepository;
import com.fiap.springblog.repository.AutorRepository;
import com.fiap.springblog.service.ArtigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArtigoServiceImpl implements ArtigoService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    private ArtigoRepository artigoRepository;

    @Autowired
    private AutorRepository autorRepository;

    public ArtigoServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Artigo> obterTodos() {
        return this.artigoRepository.findAll();
    }

    @Override
    public Artigo obterPorCodigo(String id) {
        return this.artigoRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("Artigo não existe") );
    }

    @Override
    public Artigo criar(Artigo artigo) {
        if (artigo.getAutor().getCodigo() != null) {
            Autor autor = this.autorRepository.findById(artigo.getAutor().getCodigo())
                    .orElseThrow( () -> new IllegalArgumentException("Autor não encontrado") );

            artigo.setAutor(autor);
        } else {
            artigo.setAutor(null);
        }
        return this.artigoRepository.save(artigo);
    }

    @Override
    public List<Artigo> encontrarArtigoComDataMaiorQue(LocalDateTime data) {
        Query query = new Query(Criteria.where("data").gt(data));
        return mongoTemplate.find(query, Artigo.class);
    }

    @Override
    public List<Artigo> encontrarArtigoComDataEQueTenhaAutor(LocalDateTime data, String codigoAutor) {
        Query query = new Query(Criteria.where("data").is(data).and("autor.codigo").is(codigoAutor));
        return mongoTemplate.find(query, Artigo.class);
    }

    @Override
    public void atualiza(Artigo novoArtigo) {
        Artigo artigo = this.artigoRepository.findById(novoArtigo.getCodigo())
                            .orElseThrow( () -> new IllegalArgumentException("Artigo não encontrado") );

        artigo.setAutor(novoArtigo.getAutor());
        artigo.setData(novoArtigo.getData());
        artigo.setTexto(novoArtigo.getTexto());
        artigo.setTitulo(novoArtigo.getTitulo());
        artigo.setStatus(novoArtigo.getStatus());
        artigo.setUrl(novoArtigo.getUrl());

        this.artigoRepository.save(artigo);
    }

    @Override
    public void atualizaSoUrl(String id, String novaUrl) {
        Query query = new Query(Criteria.where("codigo").is(id));
        Update update = new Update().set("url", novaUrl);
        this.mongoTemplate.updateFirst(query, update, Artigo.class);
    }

    @Override
    public void delete(String id) {
        this.artigoRepository.deleteById(id);
    }

    @Override
    public void deleteTemplate(String id) {
        Query query = new Query(Criteria.where("codigo").is(id));
        this.mongoTemplate.remove(query, Artigo.class);
    }

}
