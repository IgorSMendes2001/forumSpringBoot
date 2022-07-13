package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.dto.DetalhesTopicoDTO;
import br.com.alura.forum.dto.TopicoDTO;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
@RequestMapping(value = "/topicos")
@RestController
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;

	@Autowired
	private CursoRepository cursoRepository;
	@GetMapping
	public List<TopicoDTO> lista(String nomeCurso) {
		if (nomeCurso == null) {
			List<Topico> topicos = topicoRepository.findAll();
			return TopicoDTO.converter(topicos);
		} else {
			List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
			return TopicoDTO.converter(topicos);
		}
	}
	@PostMapping
	public ResponseEntity<TopicoDTO> salvar(@RequestBody @Valid TopicoDTO topicoDto,UriComponentsBuilder uriBuilder){
		Topico topico=topicoDto.converter(cursoRepository);
		topicoRepository.save(topico);
		URI uri= uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri(); 
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}
	@GetMapping("/{id}")
	public DetalhesTopicoDTO detalhar(@PathVariable Long id){
		Topico topico=topicoRepository.getOne(id);
		return new DetalhesTopicoDTO(topico);
	}
	@PutMapping("/{id}")
	public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id,@RequestBody @Valid TopicoDTO topicoDTO ){
		Topico topico=topicoDTO.atualizar(id, topicoRepository);
	}

}
