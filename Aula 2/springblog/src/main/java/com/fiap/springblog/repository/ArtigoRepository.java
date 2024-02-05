package com.fiap.springblog.repository;

import com.fiap.springblog.model.Artigo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArtigoRepository extends MongoRepository<Artigo, String> {

    //Query method - JPA ou, no caso, MongoRepository fazem o método automaticamente
    //por meio do nome
    List<Artigo> findByStatusAndDataGreaterThan(Integer Status, LocalDateTime data);

    //montando por meio da anotação query, podendo assim fazer query mais complexas
    //utilizando a linguagem do mongo para fazer os filtros
    @Query( "{ $and: [ { 'data': { $gte: ?0 } }, { 'data': { $lte: ?1 } } ] }" )
    List<Artigo> obterArtigoPorDataEHora(LocalDateTime de, LocalDateTime ate);

    //ordenação
    //por query method
    List<Artigo> findByStatusOrderByTituloDesc(Integer status);

    @Query(value = "{ 'status': { $eq: ?0 } }", sort = "{ 'titulo': -1 }")
    List<Artigo> obterArtigoPorStatusComOrdenacaoDesc(Integer status);

}
