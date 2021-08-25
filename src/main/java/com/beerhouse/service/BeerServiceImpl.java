package com.beerhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beerhouse.exception.BeerNotFoundException;
import com.beerhouse.model.Beer;
import com.beerhouse.repository.BeerRepository;

@Service
public class BeerServiceImpl implements BeerService {
	
	@Autowired
	private BeerRepository beerRepository;

	@Override
	public List<Beer> findAll() {
		return beerRepository.findAll();
	}

	@Override
	public Beer findById(Integer id) {
		return beerRepository.findById(id)
				.orElseThrow(() -> new BeerNotFoundException(id));
	}

	@Override
	public Beer save(Beer beer) {
		return beerRepository.save(beer);
	}

	@Override
	public Beer update(Integer id, Beer newBeer) {
		return beerRepository.findById(id) 
				.map(beer -> {
					beer.setName(newBeer.getName());
					beer.setIngredients(newBeer.getIngredients());
					beer.setAlcoholContent(newBeer.getAlcoholContent());
					beer.setCategory(newBeer.getCategory());
					beer.setPrice(newBeer.getPrice());
					return save(beer);
				}).orElseThrow(() -> new BeerNotFoundException(id));
	}
	
	@Override
	public Beer updatePartial(Integer id, Beer newBeer) {
		return beerRepository.findById(id) 
				.map(beer -> {
					if (newBeer.getName() != null && !newBeer.getName().isEmpty())
						beer.setName(newBeer.getName());
					if (newBeer.getIngredients() != null && !newBeer.getIngredients().isEmpty())
						beer.setIngredients(newBeer.getIngredients());
					if (newBeer.getAlcoholContent() != null && !newBeer.getAlcoholContent().isEmpty())
						beer.setAlcoholContent(newBeer.getAlcoholContent());
					if (newBeer.getCategory() != null && !newBeer.getCategory().isEmpty()) 
						beer.setCategory(newBeer.getCategory());
					if (newBeer.getPrice() != null)
						beer.setPrice(newBeer.getPrice());
					return save(beer);
				}).orElseThrow(() -> new BeerNotFoundException(id));
	}

	@Override
	public void delete(Integer id) {
		Beer beer = findById(id);
		
		beerRepository.delete(beer);
	}

}
