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
   
(include git clone command)
(include all environment setup steps)

> Be sure to include BOTH Windows and Unix command  
> Be sure to mention if the commands only work on a specific platform (eg. AWS, GCP)

- All the `code` required to get started
- Images of what it should look like

## Usage

> Here, you instruct other people on how to use your project after they’ve installed it. This would also be a good place to include screenshots of your project in action.

## Contributors

> Frances Hogg. 
> Tom Magrino, Eric Tzeng, and John DeNero developed this project's starter code.
> The artwork was drawn by Alana Tran, Andrew Huang, Emilee Chen, Jessie Salas, Jingyi Li, Katherine Xu, Meena Vempaty, Michelle Chang, and Ryan Davis.

