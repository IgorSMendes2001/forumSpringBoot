package br.com.alura.forum.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

public class TopicoDTO {
         private Long id;
         @NotBlank @Length(min=5) 
         private String titulo;
         @NotBlank @Length(min=10)
         private String mensagem;
         private LocalDateTime dataCriacao;
         @NotBlank
         private String nomeCurso; 
         
         public Long getId() {
            return id;
        }

        public TopicoDTO(String titulo, String mensagem, String nomeCurso) {
            this.titulo = titulo;
            this.mensagem = mensagem;
            this.nomeCurso = nomeCurso;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public String getMensagem() {
            return mensagem;
        }

        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }

        public LocalDateTime getDataCriacao() {
            return dataCriacao;
        }

        public void setDataCriacao(LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
        }

        public TopicoDTO(Topico topico) { 
            this.id = topico.getId(); 
                this.titulo = topico.getTitulo();
                this.mensagem = topico.getMensagem();
                this.dataCriacao = topico.getDataCriacao();
        
                }

        public static List<TopicoDTO> converter(List<Topico> topicos) {
            return topicos.stream().map(TopicoDTO::new).collect(Collectors.toList());
        }

        public String getNomeCurso() {
            return nomeCurso;
        }

        public void setNomeCurso(String nomeCurso) {
            this.nomeCurso = nomeCurso;
        }

        public Topico converter(CursoRepository repository) {
            Curso curso =repository.findByNome(nomeCurso);
            return new Topico(titulo,mensagem,curso);
        }

        public Topico atualizar(Long id2, TopicoRepository topicoRepository) {
            Topico topico=topicoRepository.getOne(id);
            topico.setTitulo(this.titulo);
            topico.setMensagem(this.mensagem);
            return topico;
        }
        }
