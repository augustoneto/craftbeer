package com.beerhouse.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.beerhouse.model.Beer;
import com.beerhouse.service.BeerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BeerController.class)
public class BeerControllerTest {
	
	private static final Integer BEER_ID = 1;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BeerService beerService;
	
	private Beer beer;
	
	@BeforeEach
	void setUp() {
		beer = new Beer();
		beer.setId(BEER_ID);
		beer.setName("Beer Test");
		beer.setIngredients("Ingredients Beer Test");
		beer.setAlcoholContent("10 %");
		beer.setPrice(new BigDecimal("12.2"));
		beer.setCategory("Category Beer Test");
		
		given(beerService.findById(BEER_ID)).willReturn(beer);
		given(beerService.findAll()).willReturn(Lists.newArrayList(new Beer(), new Beer()));
		given(beerService.save(beer)).willReturn(beer);
	}
	
	@Test
	void testFindAll() throws Exception {
		mockMvc.perform(get("/beers")).andExpect(status().isOk());
	}
	
	@Test
	void testFindById() throws Exception {
		mockMvc.perform(get("/beers"+"/"+BEER_ID)).andExpect(status().isOk());
	}
	
	@Test
	void testSave() throws Exception {
		mockMvc.perform(post("/beers").content(new ObjectMapper().writeValueAsString(beer))
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON))
		           	.andExpect(status().isCreated())
		           	.andExpect(header().string("Location", "http://localhost/beers/1"));
	}
	
	@Test
	void testUpdate() throws Exception {
		mockMvc.perform(put("/beers"+"/"+BEER_ID).content(new ObjectMapper().writeValueAsString(beer))
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON))
		           	.andExpect(status().isOk());
	}
	
	@Test
	void testUpdatePartial() throws Exception {
		mockMvc.perform(patch("/beers"+"/"+BEER_ID).content(new ObjectMapper().writeValueAsString(beer))
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON))
		           	.andExpect(status().isOk());
	}
	
	@Test
	void testDelete() throws Exception {
		mockMvc.perform(delete("/beers"+"/"+BEER_ID))
		           	.andExpect(status().isNoContent());
	}

}
