package com.uneb.spring_api.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uneb.spring_api.dto.PropostaDTO;
import com.uneb.spring_api.models.Anuncio;
import com.uneb.spring_api.models.Proposta;
import com.uneb.spring_api.models.Status;
import com.uneb.spring_api.models.User;
import com.uneb.spring_api.service.AnuncioService;
import com.uneb.spring_api.service.PropostaService;
import com.uneb.spring_api.service.StatusService;
import com.uneb.spring_api.service.UserService;

@RestController
@RequestMapping("/api/proposta")
public class PropostaController {

    @Autowired
    private PropostaService propostaService;

    @Autowired
    private UserService userService;

    @Autowired
    private AnuncioService anuncioService;

    @Autowired
    private StatusService statusService;

    @PostMapping("/criar") // rota para criar uma proposta
    public ResponseEntity<Map<String,Object>> createProposta(@RequestBody PropostaDTO propostaDTO) {
        Proposta proposta = new Proposta();
        System.out.println("recena" + propostaDTO);
        Map<String,Object> response = new HashMap<>();
        Optional<User> requisitante = userService.verUsuario(propostaDTO.idRequisitante());
        Optional<Anuncio> anuncio = anuncioService.verAnuncio(propostaDTO.idAnuncio());
        Optional<Status> status = statusService.obterStatus(1L);
        if (!requisitante.isPresent()){ // verificar se o id do requisitante existe
            response.put("message","Requisitante não existe no banco de usuários");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (!anuncio.isPresent()) { //  verifica se o id do anunncio existe
            response.put("message","O anuncio informado não foi achado.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (!status.isPresent()) { // verifica se o status existe
            response.put("message","O status infomrado não foi achado no banco de dados.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            // passando as informações do dto para o objeto Proposta final
            proposta.setRequisitante(requisitante.get());
            proposta.setAnuncio(anuncio.get());
            proposta.setStatus(status.get());
            proposta.setDataDeProposta(LocalDateTime.now());
            proposta.setMensagem(propostaDTO.mensagem());

            propostaService.criarProposta(proposta);
            response.put("proposta", proposta);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
    }

    //procura a proposta pelo criador
    @GetMapping("/lista/createdBy/{userId}")
    public List<Proposta> listarPropostaByUser(@PathVariable Long userId) {
        System.out.println("body" + userId); 
        return propostaService.listarPropostasByUserId(userId);
    }

    // Retorna a lista de proposta baseado no id do usuário quem efetuou a requisição
    @GetMapping("lista/requisitante/{userId}")
    public List<Proposta> listarPropostaByRequisitante(@PathVariable Long userId) {
        System.out.println("body" + userId);
        return propostaService.listarPropostasByRequisitante(userId);
    }
    
    // Rota para uma proposta ser aceita
    @PostMapping("/aceitar/{id}")
    public ResponseEntity<Map<String,Object>> aceitarProposta(@PathVariable Long id){

        // Alterando a proposta na tabela
        Proposta teste = new Proposta();

        Optional<Proposta> propostaAAceitar = propostaService.obterProposta(id);
        Optional<Status> statusDeAceito = statusService.obterStatus(2L);

        Optional<Anuncio> anuncioEmQuestao = anuncioService.verAnuncio(propostaAAceitar.get().getAnuncio().getId());
        propostaAAceitar.get().setStatus(statusDeAceito.get());
        propostaService.atualizarProposta(propostaAAceitar.get());


        Map<String,Object> response = new HashMap<>();
        response.put("message","Proposta aceita!");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    // Rota para uma proposta ser recusada
    @PostMapping("/recusar/{id}")
    public ResponseEntity<Map<String,Object>> recusarProposta(@PathVariable Long id){

        // Alterando a proposta na tabela
        Optional<Proposta> propostaAAceitar = propostaService.obterProposta(id);
        Optional<Status> statusDeAceito = statusService.obterStatus(3L);
        propostaAAceitar.get().setStatus(statusDeAceito.get());
        propostaService.atualizarProposta(propostaAAceitar.get());


        Map<String,Object> response = new HashMap<>();
        response.put("message","Proposta recusada!");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    // rota para confirmação do criador
    @PostMapping("/confirmarPeloCriador/{id}")
    public ResponseEntity<Map<String,Object>> confirmarPropostaPeloCriador(@PathVariable Long id) {
        Optional<Proposta> propostaOptional = propostaService.obterProposta(id);
        Map<String, Object> response = new HashMap<>();

        if (propostaOptional.isPresent()) {
            Proposta proposta = propostaOptional.get();
            proposta.setCriadorConfirmou(true);

            if (proposta.isRequisitanteConfirmou()) {
                Optional<Status> statusConfirmado = statusService.obterStatus(4L);
                if (statusConfirmado.isPresent()) {
                    proposta.setStatus(statusConfirmado.get());
                    proposta.getAnuncio().setStatus(false);
                    anuncioService.atualizarStatusAnuncio(proposta.getAnuncio());
                }
            }

            propostaService.atualizarProposta(proposta);

            response.put("message", "Proposta confirmada pelo criador.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("error", "Proposta não encontrada.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // rota de confirmação pelo requisitante
    @PostMapping("/confirmarPeloRequisitante/{id}")
    public ResponseEntity<Map<String,Object>> confirmarPropostaPeloRequisitante(@PathVariable Long id) {
        Optional<Proposta> propostaOptional = propostaService.obterProposta(id);
        Map<String, Object> response = new HashMap<>();

        if (propostaOptional.isPresent()) {
            Proposta proposta = propostaOptional.get();
            proposta.setRequisitanteConfirmou(true);

            if (proposta.isCriadorConfirmou()) {
                Optional<Status> statusConfirmado = statusService.obterStatus(4L);
                if (statusConfirmado.isPresent()) {
                    proposta.setStatus(statusConfirmado.get());
                    proposta.getAnuncio().setStatus(false);
                    anuncioService.atualizarStatusAnuncio(proposta.getAnuncio());
                }
            }

            propostaService.atualizarProposta(proposta);

            response.put("message", "Proposta confirmada pelo requisitante.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("error", "Proposta não encontrada.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



}
