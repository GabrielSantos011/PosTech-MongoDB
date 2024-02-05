package com.fiap.springblog.service.impl;

import com.fiap.springblog.model.Artigo;
import com.fiap.springblog.model.Autor;
import com.fiap.springblog.repository.ArtigoRepository;
import com.fiap.springblog.repository.AutorRepository;
import com.fiap.springblog.service.ArtigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    @Override
    public Artigo obterPorCodigo(String id) {
        return this.artigoRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("Artigo não existe") );
    }

    @Transactional
    @Override
    public Artigo criar(Artigo artigo) {
        if (artigo.getAutor().getCodigo() != null) {
            Autor autor = this.autorRepository.findById(artigo.getAutor().getCodigo())
                    .orElseThrow( () -> new IllegalArgumentException("Autor não encontrado") );

            artigo.setAutor(autor);
        } else {
            artigo.setAutor(null);
        }

        try {
            return this.artigoRepository.save(artigo);
        } catch (OptimisticLockingFailureException ex) {
            //1. recuperar o documento mais recente no banco
            Artigo atualizado = this.artigoRepository.findById(artigo.getCodigo()).orElse(null);

            if (atualizado != null) {
                //2. atualizar os campos desejados
                atualizado.setTitulo(artigo.getTitulo());
                atualizado.setTexto(artigo.getTexto());
                atualizado.setAutor(artigo.getAutor());
                atualizado.setData(artigo.getData());
                atualizado.setUrl(artigo.getUrl());
                atualizado.setStatus(artigo.getStatus());

                //3. incrementar a versão
                atualizado.setVersion(atualizado.getVersion() + 1);

                //4. tentar novamente
                return this.artigoRepository.save(artigo);
            } else {
                throw new RuntimeException("Artigo não encontrado: " + artigo.getCodigo());
            }
        }
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

    @Transactional
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

    @Transactional
    @Override
    public void atualizaSoUrl(String id, String novaUrl) {
        Query query = new Query(Criteria.where("codigo").is(id));
        Update update = new Update().set("url", novaUrl);
        this.mongoTemplate.updateFirst(query, update, Artigo.class);
    }

    @Transactional
    @Override
    public void delete(String id) {
        this.artigoRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteTemplate(String id) {
        Query query = new Query(Criteria.where("codigo").is(id));
        this.mongoTemplate.remove(query, Artigo.class);
    }

    @Override
    public List<Artigo> encontraPorStatusEDataMaiorQue(Integer status, LocalDateTime data) {
        return this.artigoRepository.findByStatusAndDataGreaterThan(status, data);
    }

    @Override
    public List<Artigo> obterArtigoPorDataEHora(LocalDateTime de, LocalDateTime ate) {
        return this.artigoRepository.obterArtigoPorDataEHora(de, ate);
    }

    @Override
    public Page<Artigo> listarTodosPaginado(Pageable pageable) {
        //paginação nativa do repository
        return this.artigoRepository.findAll(pageable);
    }

    @Override
    public List<Artigo> findByStatusOrderByTituloDesc(Integer status) {
        return this.artigoRepository.findByStatusOrderByTituloDesc(status);
    }

    @Override
    public List<Artigo> obterArtigoPorStatusComOrdenacaoDesc(Integer status) {
        return this.artigoRepository.obterArtigoPorStatusComOrdenacaoDesc(status);
    }

    @Override
    public Page<Artigo> listarTodosPaginadoComOrdenacao(Pageable pageable) {
        //caso a controller n utilizasse o @pageabledefault poderiamos implementar na mão aqui
        //Sort sort = Sort.by("titulo").descending();
        //Pageable paginacao = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        //return this.artigoRepository.findAll(paginacao);
        return this.artigoRepository.findAll(pageable);
    }

}
