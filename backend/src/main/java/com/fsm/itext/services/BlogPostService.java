package com.fsm.itext.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsm.itext.DTO.BlogPostDTO;
import com.fsm.itext.DTO.BuscarPostTextoBlogPostDTO;
import com.fsm.itext.entities.BlogPost;
import com.fsm.itext.repositories.BlogPostRepository;

@Service
public class BlogPostService {
	
	@Autowired
	private BlogPostRepository repository;
	
	@Transactional(readOnly = true)
	public BuscarPostTextoBlogPostDTO  clique(Long id) {
		Optional<BlogPost> blogPost = repository.findById(id);
		BlogPost entity = blogPost.get();
		entity.setCliques(entity.getCliques()+1);
		//repository.save(entity)
		return new BuscarPostTextoBlogPostDTO( entity, entity.getUrl());
	}
	@Transactional(readOnly = true)
	public List<BuscarPostTextoBlogPostDTO> buscarPostTexto(String seach) {
		List<BlogPost> posts = repository.findByResumoOrTituloContaining(seach,Sort.by(Sort.Direction.DESC,"dataPublicacao"));

		return posts.stream().map( x -> new BuscarPostTextoBlogPostDTO(x)).collect(Collectors.toList());
	}	

}
