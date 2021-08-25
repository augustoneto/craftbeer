package com.beerhouse.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.beerhouse.model.Beer;
import com.beerhouse.service.BeerService;

@RestController
public class BeerController {

	@Autowired
	private BeerService beerService;

	@GetMapping(value = "/beers")
	public List<Beer> findAll() {
		return beerService.findAll();
	}

	@PostMapping(value = "/beers")
	public ResponseEntity<Void> save(@Valid @RequestBody Beer newBeer){
		Beer beer = beerService.save(newBeer);

		//Location
		//Get current resource url
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(beer.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@GetMapping(value = "/beers/{id}")
	public ResponseEntity<Beer> findById(@PathVariable Integer id) {
		Beer beer = beerService.findById(id);

		return new ResponseEntity<Beer>(beer, HttpStatus.OK);
	}

	@PutMapping(value = "/beers/{id}")
	public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody Beer newBeer){		
		beerService.update(id, newBeer);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PatchMapping(value = "/beers/{id}")
	public ResponseEntity<Void> updatePartial(@PathVariable Integer id, @RequestBody Beer newBeer) {
		beerService.updatePartial(id, newBeer);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/beers/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		beerService.delete(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}
