package edu.ncsu.csc326.coffeemaker;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

public class CoffeeMakerStepdefs {
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
    private Recipe recipe39;
    private Recipe[] recipes;
    private RecipeBook stubRecipeBook;
    private CoffeeMaker mockCoffeeMaker;


    /**
     * Create a recipe with given parameters.
     *
     * @param name         is the recipe's name
     * @param amtChocolate is amount of Chocolate
     * @param amtCoffee    is amount of Coffee
     * @param amtMilk      is amount of Milk
     * @param amtSugar     is amount of Sugar
     * @param price        is a price of the recipe
     * @return a recipe.
     * @throws RecipeException if there was an error parsing the ingredient
     *                         amount when setting up the recipe.
     */
    private static Recipe createRecipe(String name, String amtChocolate, String amtCoffee, String amtMilk, String amtSugar, String price) throws RecipeException {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setAmtChocolate(amtChocolate);
        recipe.setAmtCoffee(amtCoffee);
        recipe.setAmtMilk(amtMilk);
        recipe.setAmtSugar(amtSugar);
        recipe.setPrice(price);
        return recipe;
    }

    private static int getRecipeNumber(Recipe[] recipes, String recipeName) {
        int num = 0;
        for (Recipe recipe: recipes) {
            if (recipe.getName().equals(recipeName)) return num;
            num++;
        }
        return 0;
    }

    @Given("A coffeemaker with recipes which is ready to serve.")
    public void setUp() throws RecipeException {
        coffeeMaker = new CoffeeMaker();

        //Set up for r1

        recipe1 = createRecipe("Coffee", "0", "3", "1", "1", "50");

        //Set up for r2
        recipe2 = createRecipe("Mocha", "20", "3", "1", "1", "75");

        //Set up for r3
        recipe3 = createRecipe("Latte", "0", "3", "3", "1", "100");

        //Set up for r4
        recipe4 = createRecipe("Hot Chocolate", "4", "0", "1", "1", "65");

        //Set up for r5
        recipe5 = createRecipe("Hot Drinking Water", "0", "0", "0", "0", "120");
        // Look like a price in airport. LoL

        //Set up for r39
        recipe39 = createRecipe("Hatsune Miku", "3", "9", "3", "9", "39");

        Inventory inventory = new Inventory();
        stubRecipeBook = mock(RecipeBook.class);
        recipes = new Recipe[]{recipe1, recipe3, recipe5, recipe39};
        mockCoffeeMaker = new CoffeeMaker(stubRecipeBook, inventory);
    }

    @When("I order a listed coffee.")
    public void iOrderAListedCoffee() {
        when(mockCoffeeMaker.getRecipes()).thenReturn(recipes);
    }

    @When("I order an unlisted coffee.")
    public void iOrderAnUnlistedCoffee() {
        when(mockCoffeeMaker.getRecipes()).thenReturn(new Recipe[4]);
    }

    @Then("I pay {int} and receive the {int} for the change which the recipe is {string}.")
    public void iPayAndReceiveTheForTheChangeWhichTheRecipeIs(int pay, int change, String recipeName) {
        int recipeNumber = getRecipeNumber(recipes, recipeName);
        assertEquals(change, mockCoffeeMaker.makeCoffee(recipeNumber, pay));
    }
}
