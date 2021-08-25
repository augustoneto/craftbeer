package com.beerhouse.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "beer")
public class Beer extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4328828864602928122L;

	@Column(name = "name")
	@NotEmpty
	private String name;
	
	@Column(name = "ingredients")
	@NotEmpty
	private String ingredients;
	
	@Column(name = "alcohol_content")
	@NotEmpty
	private String alcoholContent;
	
	@Column(name = "price")
	@NotNull
	private BigDecimal price;
	
	@Column(name = "category")
	@NotEmpty
	private String category;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public String getAlcoholContent() {
		return alcoholContent;
	}

	public void setAlcoholContent(String alcoholContent) {
		this.alcoholContent = alcoholContent;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Beer [name=" + name + ", ingredients=" + ingredients + ", alcoholContent=" + alcoholContent + ", price="
				+ price + ", category=" + category + "]";
	}
	
}
