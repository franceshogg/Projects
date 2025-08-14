# ANTS VS BEES

## Project Description

This is a game coded in Python where ants defend their colony from invading bees. The game takes place in a tunnel. Each turn, new bees may enter, the player can place ants, and then all insects act: Ants gather food or attack, while bees move forward or sting. The bees win if a bee reaches the tunnel’s end or kills the QueenAnt, and the ants win if all bees are defeated.

## Technologies Used

* Python 3
* UC Berkeley `ucb` module (for command-line interaction and tracing)
* Object-Oriented Programming (OOP)
* Standard Python libraries:
   * `random`
   * `collections.OrderedDict`

## Features

List of features ready and TODOs for future development
* Diverse Ant Types with Unique Abilities
  * Includes damage-dealing (ThrowerAnt, FireAnt), support (HarvesterAnt), defensive (WallAnt), and utility ants (HungryAnt, BodyguardAnt, TankAnt).
  * Container ants can hold other ants in the same space for added strategy. 
* Fully Functional Game Loop with Turn-Based Logic
  * Game progresses via turns managed by GameState, simulating ant and bee behavior.
  * Win/loss conditions handled cleanly with custom exceptions.
* Modular, Object-Oriented Architecture
  * Clean class hierarchy: Insect → Ant and Bee with inheritance.
  * Easy to extend with new ant or bee types.
  * Places form a flexible linked-map structure.
* Dynamic Map with Environmental Hazards
  * Includes wet (Water) and dry tunnel layouts.
  * Only waterproof insects survive in water (ScubaThrower, Bees).

To-do list:
* Add different levels to play on harder modes.
  * Increase the speed and strengh of bees and cost of ants.
  * Create "thrower bees" that can throw damage at ants.
* Create tutorial to explain how to play and what each type of ant does.

## Getting Started
   
- Ensure Python3 is installed on your computer.
- Clone the repository
  - git clone https://github.com/franceshogg/Ants-Game-Python.git
- Move to correct directory, then move into "Ants" folder:
  - cd .../Ants-Game-Python/Ants
- Run game:
  - UNIX: python3 gui.py
  - Windows: python gui.py

## Usage

* Click "Play Ants" button.
* The different types of Ants are at the top, as well as the amount of food you have. Click the ant you want to use and click on the tile where you want to put it.
* Eliminate Bees to ensure they do not reach the end of the tunnel or kill the QueenAnt.
* Once all Bees are eliminated, you win. 
* Types of Ants:
  * HarvesterAnt – Produces 1 food per turn to support the colony.
  * ThrowerAnt – Attacks the nearest bee within range by throwing a leaf.
  * ShortThrower – Variant of ThrowerAnt; only attacks bees up to 3 places away.
  * LongThrower – Variant of ThrowerAnt; only attacks bees at least 5 places away.
  * ScubaThrower – Waterproof version of ThrowerAnt; survives in water.
  * SlowThrower – Doesn’t deal damage, but slows bees (they act every other turn for 3 turns).
  * FireAnt – Deals damage to all bees in its place when attacked; extra burst on death.
  * TankAnt – Container that also deals 1 damage to all bees in its place every turn.
  * WallAnt – High-health ant that blocks bees but doesn’t attack or produce food.
  * BodyguardAnt – Container that protects another ant in the same place.
  * HungryAnt – Instantly eats a bee in its place, but needs 3 turns to chew afterward.
  * QueenAnt – Throws like a ScubaThrower and doubles the damage of ants behind her. If she dies, the game ends.
  * AntRemover – Special utility ant used in the GUI to remove ants from the board.

## Contributors

Frances Hogg. Tom Magrino, Eric Tzeng, and John DeNero developed this project's starter code. The artwork was drawn by Alana Tran, Andrew Huang, Emilee Chen, Jessie Salas, Jingyi Li, Katherine Xu, Meena Vempaty, Michelle Chang, and Ryan Davis.

