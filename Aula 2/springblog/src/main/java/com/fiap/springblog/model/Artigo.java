package com.fiap.springblog.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data //notação do lombok que cria getters e setters para os atributos automáticamente
@Document //equivalente ao entity em um projeto sql
public class Artigo {

    @Id //faz com que o mongo gere o id automáticamente
    private String codigo;
    private String titulo;
    private LocalDateTime data;
    private String texto;
    private String url;
    private Integer status;

    @DBRef //referencia de um outro documento no mongodb
    private Autor autor;

}
