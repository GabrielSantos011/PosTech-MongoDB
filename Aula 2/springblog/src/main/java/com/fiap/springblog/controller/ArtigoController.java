package com.fiap.springblog.controller;

import com.fiap.springblog.model.Artigo;
import com.fiap.springblog.service.ArtigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/artigos")
public class ArtigoController {

    @Autowired
    private ArtigoService artigoService;

    //erro de concorrencia no banco
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<String> handleOptimistLockingFaliureException(OptimisticLockingFailureException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro de concorrência: O Artigo já foi atualizado por outro usuário, tente novamente!");
    }

    @GetMapping(value = "/todos")
    public List<Artigo> obterTodos() {
        return this.artigoService.obterTodos();
    }

    @GetMapping(value = "/todosPaginados")
    public ResponseEntity<Page<Artigo>> listarTodosPaginado(Pageable pageable) {
        //para chamar esse endpoint é necessário passar como requestParam o
        //page (sendo 0 a primeira pagina) e o size (numero de artigos por pagina)
        Page<Artigo> page = this.artigoService.listarTodosPaginado(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping(value = "/{codigo}")
    public Artigo obterPorCodigo(@PathVariable String codigo) {
        return this.artigoService.obterPorCodigo(codigo);
    }

    @GetMapping(value = "/dataMaiorQue")
    public List<Artigo> encontrarArtigoComDataMaiorQue(@RequestParam("data")LocalDateTime data) {
        return this.artigoService.encontrarArtigoComDataMaiorQue(data);
    }

    @GetMapping(value = "/dataEAutor")
    public List<Artigo> encontrarArtigoComDataEQueTenhaAutor(@RequestParam("data")LocalDateTime data, @RequestParam("codigoAutor") String codigoAutor) {
        return this.artigoService.encontrarArtigoComDataEQueTenhaAutor(data, codigoAutor);
    }

    @GetMapping(value = "/statusEDtaMaiorQue")
    public List<Artigo> encontraPorStatusEDataMaiorQue(@RequestParam("status") Integer status, @RequestParam("data")LocalDateTime data) {
        return this.artigoService.encontraPorStatusEDataMaiorQue(status, data);
    }

    @GetMapping(value = "/porDataEHora")
    public List<Artigo> obterArtigoPorDataEHora(@RequestParam("de") LocalDateTime de, @RequestParam("ate") LocalDateTime ate) {
        return this.artigoService.obterArtigoPorDataEHora(de, ate);
    }

    @GetMapping(value = "/statusOrdenado")
    public List<Artigo> encontraPorStatusOrdenadoPorTiTuloDesc(@RequestParam("status") Integer status) {
        return this.artigoService.findByStatusOrderByTituloDesc(status);
    }

    @GetMapping(value = "/statusOrdenadoQuery")
    public List<Artigo> encontraPorStatusOrdenadoPorTiTuloDescQuery(@RequestParam("status") Integer status) {
        return this.artigoService.obterArtigoPorStatusComOrdenacaoDesc(status);
    }

    @GetMapping(value = "/todosPaginadosOrdenado")
    public ResponseEntity<Page<Artigo>> listarTodosPaginadoComOrdenacao(@PageableDefault(size = 2, page = 0, sort = "titulo", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Artigo> page = this.artigoService.listarTodosPaginadoComOrdenacao(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping(value = "/criar")
    public Artigo criar(@RequestBody Artigo artigo) {
        return this.artigoService.criar(artigo);
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
