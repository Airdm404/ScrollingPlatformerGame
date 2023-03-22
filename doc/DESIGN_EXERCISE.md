# OOGA Lab Discussion
## Mike Garay meg89 Alex Lu atl16 Edem Ahorlu eka13 Ryan Krakower rmk44


## Fluxx

### High Level Design Ideas

Classes: Card class, Game class, Player class, Rule class, Goal class, CardDeck class

### CRC Card Classes

This class's purpose or value is to represent a card that the Player has
public class Card {
 getCard(): abstract, returns the card
}

This class's purpose or value is to represent a Rule card
public class Rule extends Card {
 getRule() - gets the Rule associated with the card
}

This class's purpose or value is to represent a Goal card
public class Goal class extends Card {
 getGoal() - gets the Goal associated with the card
}

This class's purpose or value is to represent a Action card
public class Action extends Card {
.getAction() - gets the Action associated with the card
}

This class's purpose or value is to represent a deck of cards
public class CardDeck {
.getCard() - abstract, overriden but returns a card
}

This class's purpose or value is to run the game for the user, loops through turns
public class Game {
 getNextAction() - substitutes in either a subclass or a predetermined method that determines what
action the game should perform next in order to give a Player a turn
 checkIfGoalMet() - checks to see if the goal is met, if so ends the game with a win for the player
that won
}

This class's purpose or value is to contain different variables (Keeper, Creeper, Goal, Rules) to store the current 
configuration of the game
public class Theme {
getKeepers()
getCreepers()
getGoal()
getRules()
}

### Use Cases

### Use Cases

Use Cases:

