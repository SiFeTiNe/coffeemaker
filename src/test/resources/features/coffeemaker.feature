Feature: Make a coffee
  A customer has enough money to purchase a coffee with the valid recipe and enough ingredient to be used from coffeemaker.

  Background: purchase a coffee.
    Given A coffeemaker with recipes which is ready to serve.

  Scenario: purchase a coffee with enough money.
    When I order a listed coffee.
    Then I pay 150 and receive the 30 for the change which the recipe is "Hot Drinking Water".

  Scenario: purchase a coffee with exact change.
    When I order a listed coffee.
    Then I pay 39 and receive the 0 for the change which the recipe is "Hatsune Miku".

  Scenario: purchase a coffee with not enough money.
    When I order a listed coffee.
    Then I pay 39 and receive the 39 for the change which the recipe is "Coffee".

  Scenario: purchase an invalid coffee with enough money.
    When I order an unlisted coffee.
    Then I pay 128 and receive the 128 for the change which the recipe is "WAAA-040".