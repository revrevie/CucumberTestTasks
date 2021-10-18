Feature: Acceptance criteria
  Scenario: Add items to wishlist and cart
    Given I add 4 different products to my wishlist
    When I view my wishlist table
    Then I find total 4 selected items in my wishlist
    When I search for the lowest price product
    And I am able to add the lowest price item to my cart
    Then I am able to verify the item in my cart