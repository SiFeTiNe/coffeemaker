/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 * 
 * Permission has been explicitly granted to the University of Minnesota 
 * Software Engineering Center to use and distribute this source for 
 * educational purposes, including delivering online education through
 * Coursera or other entities.  
 * 
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including 
 * fitness for purpose.
 * 
 * 
 * Modifications 
 * 20171114 - Ian De Silva - Updated to comply with JUnit 4 and to adhere to 
 * 							 coding standards.  Added test documentation.
 */
package edu.ncsu.csc326.coffeemaker;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

/**
 * Unit tests for CoffeeMaker class.
 * 
 * @author Sarah Heckman
 */
public class CoffeeMakerTest {
	
	/**
	 * The object under test.
	 */
	private CoffeeMaker coffeeMaker;
	
	// Sample recipes to use in testing.
	private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;
	private Recipe recipe5;

	/**
	 * Initializes some recipes to test with and the {@link CoffeeMaker} 
	 * object we wish to test.
	 * 
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	@Before
	public void setUp() throws RecipeException {
		coffeeMaker = new CoffeeMaker();
		
		//Set up for r1
		recipe1 = new Recipe();
		recipe1.setName("Coffee");
		recipe1.setAmtChocolate("0");
		recipe1.setAmtCoffee("3");
		recipe1.setAmtMilk("1");
		recipe1.setAmtSugar("1");
		recipe1.setPrice("50");
		
		//Set up for r2
		recipe2 = new Recipe();
		recipe2.setName("Mocha");
		recipe2.setAmtChocolate("20");
		recipe2.setAmtCoffee("3");
		recipe2.setAmtMilk("1");
		recipe2.setAmtSugar("1");
		recipe2.setPrice("75");
		
		//Set up for r3
		recipe3 = new Recipe();
		recipe3.setName("Latte");
		recipe3.setAmtChocolate("0");
		recipe3.setAmtCoffee("3");
		recipe3.setAmtMilk("3");
		recipe3.setAmtSugar("1");
		recipe3.setPrice("100");
		
		//Set up for r4
		recipe4 = new Recipe();
		recipe4.setName("Hot Chocolate");
		recipe4.setAmtChocolate("4");
		recipe4.setAmtCoffee("0");
		recipe4.setAmtMilk("1");
		recipe4.setAmtSugar("1");
		recipe4.setPrice("65");

		//Set up for r5
		recipe5 = new Recipe();
		recipe5.setName("Hot Milk");
		recipe5.setAmtChocolate("0");
		recipe5.setAmtCoffee("0");
		recipe5.setAmtMilk("4");
		recipe5.setAmtSugar("1");
		recipe5.setPrice("45");

		//Set up for r5
		recipe5 = new Recipe();
		recipe5.setName("Hot Drinking Water");
		recipe5.setAmtChocolate("0");
		recipe5.setAmtCoffee("0");
		recipe5.setAmtMilk("0");
		recipe5.setAmtSugar("0");
		recipe5.setPrice("120"); // Look like a price in airport. LoL
	}


	/**
	 * Recipe is invalid, It must throw RecipeException.
	 * @throws RecipeException if there was invalid recipe.
	 */
	@Test(expected = RecipeException.class)
	public void testRecipeException() throws RecipeException {
		recipe5 = new Recipe();
		recipe5.setName("seieki");
		recipe5.setAmtChocolate("-12");
		recipe5.setAmtCoffee("-12");
		recipe5.setAmtMilk("-12");
		recipe5.setAmtSugar("-12");
		recipe5.setPrice("-1500");
	}
	
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with well-formed quantities
	 * Then we do not get an exception trying to read the inventory quantities.
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddInventory() throws InventoryException {
		coffeeMaker.addInventory("4","7","0","9");
	}
	
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with malformed quantities (i.e., a negative 
	 * quantity and a non-numeric string)
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryException() throws InventoryException {
		coffeeMaker.addInventory("4", "-1", "asdf", "3");
	}
	
	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying more than 
	 * 		the coffee costs
	 * Then we get the correct change back.
	 */
	@Test
	public void testMakeCoffee() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(25, coffeeMaker.makeCoffee(0, 75));
	}

	/**
	 * There are 4 valid coffee recipes.
	 * The CoffeeMaker must not be able to add if they are already 3 recipes.
	 */
	@Test
	public void testAddRecipe() {
		coffeeMaker.addRecipe(recipe4);
		coffeeMaker.addRecipe(recipe2);
		coffeeMaker.addRecipe(recipe1);
		assertFalse(coffeeMaker.addRecipe(recipe3));
	}

	/**
	 * There are 4 valid coffee recipes.
	 * The CoffeeMaker must not add if they are already 3 recipes.
	 *
	 */
	@Test
	public void testExceededAddRecipe() {
		coffeeMaker.addRecipe(recipe4);
		coffeeMaker.addRecipe(recipe2);
		coffeeMaker.addRecipe(recipe1);
		assertFalse(coffeeMaker.addRecipe(recipe3));
	}

	/**
	 * There are recipes.
	 * CoffeeMaker must not add duplicate recipes.
	 */
	@Test
	public void testDuplicatedAddRecipe() {
		coffeeMaker.addRecipe(recipe4);
		coffeeMaker.addRecipe(recipe1);
		assertFalse(coffeeMaker.addRecipe(recipe4));
		assertFalse(coffeeMaker.addRecipe(recipe1));
	}

	/**
	 * Test recipe replacement(edit) in CoffeeMaker.
	 */
	@Test
	public void testEditRecipe() {
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.editRecipe(0, recipe5);
		assertEquals(recipe5.getName(), coffeeMaker.getRecipes()[0].getName());
	}

	/**
	 * DNE editing must give null and no error.
	 */
	@Test
	public void testEditDoesNotExistRecipe() {
		assertNull(coffeeMaker.editRecipe(0, recipe5));
	}

	/**
	 * The recipe must be a null after being deleted or does not exist.
	 */
	@Test
	public void testDeleteRecipe() {
		assertNull(coffeeMaker.getRecipes()[0]); // DNE since nothing added
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.deleteRecipe(0);
		assertNull(coffeeMaker.getRecipes()[0]); // DNE cause of deletion
	}

	/**
	 * DNE deletion must give null and no error.
	 */
	@Test
	public void testDeleteDoesNotExistRecipe() {
		assertNull(coffeeMaker.deleteRecipe(2));
	}



}
