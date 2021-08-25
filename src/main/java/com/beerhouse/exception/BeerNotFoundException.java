package com.beerhouse.exception;

public class BeerNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6375146385577741024L;

	public BeerNotFoundException(Integer id) {
		super("Could not find beer " + id);
	}

}
