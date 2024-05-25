package com.bookStore.controller;

import com.bookStore.repository.MyBookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.bookStore.entity.Book;
import com.bookStore.entity.MyBookList;
import com.bookStore.service.BookService;
import com.bookStore.service.MyBookListService;


import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Character.getName;

@Controller
public class BookController {
	
	@Autowired
	private BookService service;

	@Autowired
	private MyBookRepository repository;
	
	@Autowired
	private MyBookListService myBookService;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/book_register")
	public String bookRegister() {
		return "bookRegister";
	}


	@GetMapping("/available_books")
	public ModelAndView getAllBook() {
		List<Book>list=service.getAllBook();
		return new ModelAndView("bookList","book",list);
	}
	@PostMapping("/save")
	public String addBook(@ModelAttribute Book b) {
		service.save(b);
		System.out.println(b);
		return "redirect:/available_books";
	}
	@GetMapping("/my_books")
	public String getMyBooks(Model model) {
		List<MyBookList> list = myBookService.getAllMyBooks();
		model.addAttribute("book", list);

		List<MyBookList> teste = repository.findAll();
		List<DetalharDados> detalhar = teste.stream().map(DetalharDados::new).toList();

		// Converta a lista 'detalhar' para JSON
		ObjectMapper objectMapper = new ObjectMapper();
		String detalharJson;
		try {
			detalharJson = objectMapper.writeValueAsString(detalhar);
		} catch (JsonProcessingException e) {
			detalharJson = "[]"; // Em caso de erro, envie uma array vazia
			throw new RuntimeException("Erro ao serializar detalhar para JSON", e);
		}

		// Adicione o JSON como um atributo do modelo
		model.addAttribute("detalharJson", detalharJson);

		System.out.println(detalhar);

		return "myBooks";
	}
	@RequestMapping("/mylist/{id}")
	public String getMyList(@PathVariable("id") int id) {
		Book b=service.getBookById(id);
		MyBookList mb=new MyBookList(b.getId(),b.getName(),b.getAuthor(),b.getPrice());
		myBookService.saveMyBooks(mb);
		return "redirect:/my_books";
	}
	@RequestMapping("/editBook/{id}")
	public String editBook(@PathVariable("id") int id,Model model) {
		Book b=service.getBookById(id);
		model.addAttribute("book",b);
		return "bookEdit";
	}
	@RequestMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id")int id) {
		service.deleteById(id);
		return "redirect:/available_books";
	}
}
