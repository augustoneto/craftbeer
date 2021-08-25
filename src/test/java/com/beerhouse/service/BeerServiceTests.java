package com.beerhouse.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.beerhouse.exception.BeerNotFoundException;
import com.beerhouse.model.Beer;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BeerServiceTests {
	
	private static final Integer BEER_ID = 1;
	
	@Autowired
	private BeerService beerService;
	
	@Test
	void shouldFindAllBeer() {
		List<Beer> beerList = beerService.findAll();
		
		assertFalse(beerList.isEmpty());
	}
	
	@Test
	void shouldFindBeerById() {
		Beer beer = beerService.findById(BEER_ID);
		
		assertThat(beer).isNotNull();
		assertThat(beer.getId()).isEqualTo(BEER_ID);
	}
	
	@Test
	void shouldSaveBeer() {
		List<Beer> beerList = beerService.findAll();
		int beersCount = beerList.size();
		
		Beer beer = createBeer();
		
		beerService.save(beer);
		
		assertThat(beer.getId().intValue()).isNotEqualTo(0);
		
		beerList = beerService.findAll();
		
		assertThat(beerList.size()).isEqualTo(beersCount + 1);
	}
	
	@Test
	void shouldUpdateBeer() {
		Beer beer = createBeer();

		beerService.update(BEER_ID, beer);

		Beer beerUpdate = beerService.findById(BEER_ID);
		
		assertThat(beerUpdate.getName()).isEqualTo(beer.getName());
		assertThat(beerUpdate.getIngredients()).isEqualTo(beer.getIngredients());
		assertThat(beerUpdate.getAlcoholContent()).isEqualTo(beer.getAlcoholContent());
		assertThat(beerUpdate.getPrice()).isEqualTo(beer.getPrice());
		assertThat(beerUpdate.getCategory()).isEqualTo(beer.getCategory());
	}
	
	@Test
	void shouldUpdatePartialBeer() {
		Beer beer = new Beer();
		beer.setName("Beer Test");

		beerService.updatePartial(BEER_ID, beer);

		Beer beerUpdate = beerService.findById(BEER_ID);
		
		assertThat(beerUpdate.getName()).isEqualTo(beer.getName());
	}
	
	@Test
	void shouldDeleteBeer() {
		beerService.delete(BEER_ID);
		
		assertThrows(BeerNotFoundException.class, () -> {
			beerService.findById(BEER_ID);
		});
	}
	
	private Beer createBeer() {
		Beer beer = new Beer();
		beer.setName("Beer Test");
		beer.setIngredients("Ingredients Beer Test");
		beer.setAlcoholContent("10 %");
		beer.setPrice(new BigDecimal("12.2"));
		beer.setCategory("Category Beer Test");
		
		return beer;
	}

}
