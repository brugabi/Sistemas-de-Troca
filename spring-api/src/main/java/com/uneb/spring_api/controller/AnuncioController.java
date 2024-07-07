package com.uneb.spring_api.controller;

import com.uneb.spring_api.dto.AnuncioDTO;
import com.uneb.spring_api.models.Anuncio;
import com.uneb.spring_api.models.Departamento;
import com.uneb.spring_api.models.User;
import com.uneb.spring_api.service.AnuncioService;
import com.uneb.spring_api.service.DepartamentoService;
import com.uneb.spring_api.service.FotoService;
import com.uneb.spring_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/anuncios")
public class AnuncioController {

    @Autowired
    private AnuncioService anuncioService;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartamentoService departamentoService;
    
    @Autowired
    private FotoService fotoService;

    @PostMapping("/criarAnuncio")
    public ResponseEntity<Map<String, Object>> createAnuncio(@Valid @RequestBody AnuncioDTO anuncioDTO) {
        Anuncio anuncio = new Anuncio();
        Map<String,Object> response = new HashMap<>();
        response.put("datetime",LocalDateTime.now());
        Optional<User> criador = userService.verUsuario(anuncioDTO.idCriador());
        Optional<Departamento> departamento = departamentoService.verDepartamento(anuncioDTO.idDepartamento());
        if (criador.isPresent() && departamento.isPresent()) {
            anuncio.setTitulo(anuncioDTO.titulo());
            anuncio.setDescricao(anuncioDTO.descricao());
            anuncio.setCriador(criador.get());
            anuncio.setDepartamento(departamento.get());

            // Salvar a foto
            String fotoUrl = fotoService.saveFile(anuncioDTO.foto());
            anuncio.setFotoUrl(fotoUrl);

            anuncioService.criarAnuncio(anuncio);
            response.put("anuncio",anuncio);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } else {
            response.put("error","Ou o departamento ou o criador nao existe.");
            return new ResponseEntity<>(response,HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> verAnuncio(@PathVariable Long id) {
        Optional<Anuncio> anuncio = anuncioService.verAnuncio(id);
        Map<String,Object> response = new HashMap<>();
        response.put("datetime",LocalDateTime.now());
        if (anuncio.isPresent()) {
            response.put("anuncio",anuncio.get());
            return new ResponseEntity<>(response,HttpStatus.OK);
        }else{
            response.put("Error","Anuncio nao encontrado.");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/listarAnuncios")
    public ResponseEntity<Map<String, Object>> listarAnuncio() {
        Map<String,Object> response = new HashMap<>();
        response.put("Datetime", LocalDateTime.now());
        response.put("Lista",anuncioService.listarAnuncios());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/listar/categoria/{idCategoria}")
    public List<Anuncio> getMethodName(@RequestParam Long idCategoria) {
        return anuncioService.listarAnuncioByCategoryId(idCategoria);
    }
    

    @GetMapping("/listarAnuncios/{userId}")
    public ResponseEntity<Map<String,Object>> getAnunciosByUserId(@PathVariable Long userId) {
        Optional<User> criador = userService.verUsuario(userId);
        Map<String,Object> response = new HashMap<>();
        response.put("timestamp",LocalDateTime.now());
        if (criador.isPresent()) {
            response.put("anuncios",anuncioService.getAnunciosByUser(criador.get()));
            return new ResponseEntity<>(response,HttpStatus.OK);
        } else {
            response.put("error","O id informado nao esta associado a nenhum usuario.");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Map<String, Object>> deletarAnuncio(@PathVariable Long id) {
        Optional<Anuncio> anuncio = anuncioService.verAnuncio(id);
        Map<String,Object> response = new HashMap<>();
        response.put("timestamp",LocalDateTime.now());
        if (anuncio.isPresent()) {
            anuncioService.deletarAnuncio(id);
            response.put("message","anuncio deletado com sucesso.");
            return new ResponseEntity<>(response,HttpStatus.OK);
        } else {
            response.put("error","Anuncio nao encontrado. Nao foi efetuado o delete.");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }
}
