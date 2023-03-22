final
====

This project implements a player for multiple related games.

Names: 
* Edem Ahorlu
* Alex Lu
* Mike Garey
* Ryan Krakower

### Timeline

Start Date: 10/23/2020

Finish Date: 12/18/2020

Hours Spent:

Edem Ahorlu: ~35

Alex Lu: ~35

Mike Garay: ~35

Ryan Krakower: ~35


### Primary Roles

Edem Ahorlu: 
My role as a teammate is working on the frontend of the game.
I worked with Alex Lu, another frontend team member, to implement
various features including textures, splash screens, display enemies,
power ups, and scores among others. I also worked on the integration of front end and back end.

Mike Garay:
My role was primarily working on the backend with Ryan.
Together, we implemented the entity hierarchy (entities are the building blocks
that make up our levels) and wrote the Level class. 
I was responsible for making the various interfaces used by the entities,
including power-up logic. I also worked with Alex to make LevelLoader and EntityFactory
to load in levels and create entities through reflection. Additionally,
I wrote basic game configuration and made the api package storing all of our interfaces for
the project.

Ryan Krakower:
My role was primarily working on the backend with Mike.
Together, we implemented the entity hierarchy (entities are the building blocks
that make up our levels) and wrote the Level class. 
I was responsible for the physics of entities, making the HitBox class and collision mechanics. 
I also wrote several of the entity classes, such as Player and Enemy. 
Additionally, I added the Doodle Jump and Flappy Bird texture packs.

Alex Lu: 
I worked on the front end of the game with Edem Ahorlu. I worked on the integration of front end and back
end. I also did a lot of work with controller and controller testing, and built 
the automatic level generation and scroller packages as well as their associated tests


### Resources Used
* StackOverflow
* w3schools
* geeksforgeeks
* Oracle


### Running the Program

Main class: Main.java

Data files needed: 
* resources package
* data package


Features implemented:
* Run 3 functionally different working and playable versions of a scrolling platformer game and two mods of the game:
    - Mario Bros, Mario Infinity (mod)
    - Doodle Jump, Doodle Jump 2 (mod)
    - Flappy Bird
* Enable the player easily switch between all versions of the game
* Implemented Power ups
* Implemented enemies/oppositions to inhibit the player
* Implemented Splash Screens with images
* Display scores
* Keep track of high scores on a leaderboard
* Added different texture packs, which can each be applied to any of the games
* Display caught exceptions
* Switch between different languages
* Switch between different menu/background appearance modes (default, dark, light)
* Allow the user to save progress on game
* Allow the user to set key controls for the game

### Error Handling

* Our program is built to handle a plethora of errors related to bad file input
* Our entire program is driven by different .properties files that configure the model,
  and this is where the user can mess with the file input by changing values in that
  .properties file
  
  ![hello](./doc/propertiesshot.png)
  
    * Note: "breaking" means changing the value to anything other than a valid value (i.e.
      changing "textures=flappykeyinputs.properties" to "textures=hello")
    * Breaking the level value, scroller value, player value,
      or autofile value in a .properties file (if applicable) will 
      prevent the user from being able to choose the game specified by that .properties file 
      in our "select game" screen
        * an error message will then pop up "Loading Game Failed - Bad Config File" 
         but the program won't crash when running
    * Breaking the keys value (i.e. the .properties file that determines what keys map to what 
      actions) will result in a message popping up to tell the player the file is broken when they 
      try to press an invalidly configured key, but the game will not crash
    * Breaking the leaderboard value prevents the user from being able to save their score,
      but the game does not crash
    * Breaking the nextfile value results in the player being sent back to the game select screen
      when reaching the goal that would activate loading of the .properties file at nextfile
    * A faulty texture file value will not crash the game, but will display
      all textures as black boxes to let the user know that something is wrong
* Deleting a button config file for a particular scene and then running the program will display 
  at the top of that scene "Failed to load buttons!" but will not crash the program (the buttons 
  won't be there for that scene though)
* Our error messages are pulled from language files in ./src/resources/resourcebundles and thus 
  will be displayed in other languages if the user has chosen to run the program in those languages 
* Our test library was extensive. We finished with 151 tests in the final stretches of our project, 
  achieving the 80% line coverage target discussed in class and exceeding it with a final coverage
  of 85%.

### Notes/Assumptions

Assumptions or Simplifications:
* We put a lot of our data files in the "resources" package instead of in "data"
* We decided to place some of our test files in this package as well in order to circumvent issues
  related to not being able to locate test files if they weren't in the folder where the classes
  expected to find its files (i.e. a Texturer looks for textures in the 
  /src/resources/images/gameTextures folder)
* Every entity is the same size and shape (a square). This assumption made it easier to 
  read/write levels and to make HitBoxes and collision mechanics. 
  The only downside is that it limits our options for resizing entities.
* The game scene is always the same size.


Interesting data files:
* mariolevel2.properties is a strong example of the flexibility of our project
    * Upon reaching the end of level 1 of our Super Mario game (to accomplish this run Main.java
      then press Select Game -> Super Mario and then beat the first level by touching the goal 
      at the far right), the game loads mariolevel2.properties.
    * The game then switches from using a "manual" scroll mechanic (i.e. the screen scrolls to keep
      up with the player's movements) to an "automatic" scroll mechanic (i.e. the screen scrolls
      automatically, ignoring the player's inputs) in response to the loading of this new data file
      with a new level design displayed as well.
* Our buttons and splash screen textures are configured from xml files in the src/resources/buttons
  package and in the src/resources/images package respectively.
    * The button config xml files are made up of "button"
      elements which hold data on how to build a single button
        * The id element holds the ID of the button, applied via button.setId(id);
        * The centerx and centery elements hold the center x and y coordinates at which we will 
        place the button
        * The width and height elements hold the width and height to be applied to the button
        * The method element holds the method to be called when the button is pressed
    * The splash screen xml file's is made up of "image" elements which hold data on how 
          to construct a single image
        * The id, centerx, centery, width and height elements all function the same way as
          those discussed for the button files
        * The path element contains a filepath leading to the image which will be applied
          within the bounds of (x, y, width, height)
        

Known Bugs:
* Error text can be displayed off center when it appears (i.e. if a data file is corrupted)
* If the player is moving too fast, he may go straight through a block.
* Sometimes collision mechanics do not work correctly if the player
  approaches a block from a corner (he may appear to go through the block).

Extra credit:
* We built a package called autogenerator which is responsible for in-game automatic level 
  generation. A core class called AutoGenerator creates a 2D array of Strings where the values of 
  those Strings represent specific entity types. Then, a Scroller in the scroller package translates 
  the 2D array into a list of entities using an EntityFactory (i.e. it receives "3" and returns 
  "Block" entity) and inserts all of the new entities into the current level object during gameplay. 
  This allows us to effectively generate new segments of the level at runtime without stopping the 
  game.
    * This has allowed us to implement infinite gameplay in the two DoodleJump variants, Flappy
      Bird and Mario Infinity, where the user can continue exploring new sections of the level
      indefinitely.
    * This automatic level generator is extremely flexible, as it reads instructions from a 
      well-formatted xml file on how to build its new levels. This has allowed it to infinitely 
      generate chunks for extremely different levels. For example, it can generate the "two pipe" 
      obstacle for a new Flappy Bird chunk or several separate floating platforms in the sky for a new
      Doodle Jump chunk.
    * The automatic level generator reads from xml files placed in the 
      src/resources/game_configuration/auto package
    * For details on how to write such a file, refer to AUTOGENERATOR.md
    * This also allowed us to create our fourth game which we will discuss shortly

* We included a special easter egg, that only we know how to activate
    * TRIGGER WARNING: if you are not in a good place with the results of the election, do not 
      continue reading instructions on how to activate this easter egg
    * The easter egg launches a new game with special textures and different objectives and level
      style than an ordinary Mario game
    * This game is not accessible in the list of games available via our "Select Game" button and
      can only be loaded if the user presses a specific cheat key on the main menu (pressing this
      cheat key on any other screen will not activate the easter egg by our design)
    * The cheat key is the final letter of our team name

### Impressions

   This was definitely the most enjoyable project of the year to work on. In spite of the increased
team size, we had great team communication and deadline management and most of us believe that this
particular project is our strongest of the year. The free rein to take this project in whatever 
direction we wanted to was initially scary, but later proved to be one of the best parts of the
project because we were able to realize our vision of what the final product would look like, rather
than being confined to what the project specifications. This served as a remarkable catalyst for
self motivation and helped us to achieve the results we wanted.