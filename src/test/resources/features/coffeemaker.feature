Feature: Make a coffee
  A customer has enough money to purchase a coffee with the valid recipe and enough ingredient to be used from coffeemaker.

  Background: purchase a coffee.
    Given a recipe from the customer.

  Scenario: purchase a coffee with enough money.
    When I pay 150 for the order.
    Then I receive the 30 for the change which the recipe is Hot Drinking Water.

  Scenario: purchase a coffee with exact money.
    When I pay 39 for the order.
    Then I receive the 0 for the change which the recipe is Hatsune Miku.

  Scenario: purchase a coffee with not enough money.
    When I pay 39 for the order.
    Then I receive the 39 for the change which the recipe is Coffee.

  Scenario: purchase an invalid coffee with enough money.
    When I pay 128 for the order.
    Then I receive the 128 for the change which the recipe is WAAA-040.