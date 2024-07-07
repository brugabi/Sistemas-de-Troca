package com.uneb.spring_api.controller;

import com.uneb.spring_api.dto.DepartamentoDTO;
import com.uneb.spring_api.models.Departamento;
import com.uneb.spring_api.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @PostMapping("/criar")
    public ResponseEntity<Map<String, Object>> criarDepartamento(@RequestBody DepartamentoDTO departamentoDTO) {
        Departamento departamento = new Departamento();
        Map<String, Object> response = new HashMap<>();
        departamento.setNome(departamentoDTO.nome());
        Departamento departamentoCriado = departamentoService.criarDepartamento(departamento);
        response.put("timestamp", LocalDateTime.now());
        response.put("message","Departamento criado com sucesso!!");
        response.put("data",departamentoCriado);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/listar")
    public List<Map<String,Object>> listarDepartamentos() {
        return departamentoService.listarDepartamentos();
    }
}
