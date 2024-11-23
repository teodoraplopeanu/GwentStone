# GwentStone

<div align="center"><img src="https://tenor.com/view/witcher3-gif-9340436.gif" width="500px"></div>

## Description
**GwentStone** is a custom card game implemented in Java, inspired by popular games such as **Hearthstone** and **Gwent**. The project showcases advanced object-oriented programming (OOP) principles and efficient use of data structures to create a dynamic, interactive gameplay experience.

The game emphasizes strategic card mechanics and leverages modular design to manage complex interactions between players, cards, and the game board.

---

## Features

### Core Gameplay
- **Card Management**: Cards have attributes like mana cost, attack damage, health, and unique abilities. Heroes and special minions possess superpowers that can influence the game.
- **Turn-Based Combat**: Players alternate turns, playing cards, using abilities, and attacking. Victory is achieved by reducing the opponent's hero's health to zero.
- **Game Board Mechanics**: A 4x5 matrix represents the board where cards are played. Each slot influences gameplay dynamics like adjacency and targeting.

### Additional Functionalities
- **Debug Commands**: A suite of debug commands allows developers to inspect and manipulate game states for testing.
- **Game Statistics**: Tracks and reports match outcomes, including total games played and wins per player.
- **Error Handling**: Comprehensive error management ensures graceful handling of invalid commands or game states.

---

## Project Structure

### `main` Package
Contains core classes responsible for the overall game flow and interaction with input/output operations.

- **`Game`**: 
  - Initializes the game environment (players, decks, game board, etc.).
  - Executes gameplay commands and produces results.
- **`GameBoard`**: 
  - Represents the 4x5 game board.
  - Manages card properties like frozen states, determines adjacency relationships, and facilitates gameplay logic.
- **`GameBoardActions`**: Implements commands affecting cards on the board, such as attacks, abilities, and status effects.
- **`GameFlow`**: Tracks the game state, including turns, active player, and win conditions.
- **`Player`**: Stores attributes for each player, including hero, mana pool, current hand, and deck.
- **`Deck`**: Represents a player’s deck of cards.
- **`Hand`**: Represents the cards currently available to a player.
- **`Statistics`**: Collects and reports game metrics, such as the number of games played or won by each player.
- **`Error`**: Handles error scenarios, ensuring invalid commands or states are managed without crashing the game.

### `cards` Package
Contains classes related to cards and their functionality.

- **`Card`**: Base class for all cards, defining common properties and methods.
- **`Hero`**: Represents the hero card with unique abilities.
  - **`heroes`**: Subclasses for each hero, overriding superpower behavior.
- **`Minion`**: Represents minion cards.
  - **`superminions`**: Specialized minions with unique abilities, implemented via method overrides.

### `command` Package
Handles gameplay commands and debugging tools.

- **`Command`**: Base class for all commands.
- **Specialized Classes**: Implement specific commands, including debugging features for inspecting game states.

---

## Functionality

### Initialization
- The game environment is set up using input data, including players, decks, and initial mana pools.

### Turn-Based Gameplay
- Players alternate turns, drawing cards, using abilities, and engaging in combat.

### Card Interactions
- Cards are defined by attributes (mana cost, attack, health) and special abilities that can affect other cards or the game state.

### Victory Conditions
- A match ends when a hero’s health is reduced to zero, with the results recorded in the statistics module.

---

## Gameplay Mechanics and Rules

### Overview
This is a strategic, turn-based card game where two players engage in a battle by summoning minions, casting abilities, and utilizing their heroes’ unique skills. The ultimate objective is to reduce the opponent’s hero’s health to **0** before they can do the same to you.

---

### Objective
The first player to reduce the opponent’s hero health to **0** wins the game.

### Setup
1. Each player starts with:
   - A deck of **minion cards**.
   - One **hero card** with unique abilities.
   - A set amount of **mana** to summon cards (e.g., 10 mana per turn).
   - A fixed **hero health** (e.g., 30 points at the start).

2. Players alternate turns. Each turn involves:
   - Drawing cards from their deck.
   - Spending mana to summon minions or activate abilities.
   - Attacking with minions or the hero.

---

## Card Types and Attributes

### 1. **Minion Cards**
Minion cards are the primary units for attacking and defending. Each card has the following attributes:
- **Mana**: The cost to summon the card.
- **Health**: The life points of the card. When health reaches 0, the card is removed from the board.
- **Attack**: The damage the card deals to enemy minions or the opposing hero.

### 2. **Hero Cards**
Each player controls one hero card throughout the game. Hero cards are powerful and come with unique abilities. They have:
- **Mana Cost**: The cost to use a hero's ability.
- **Health**: Starting at a fixed value (e.g., 30), health decreases when the hero is attacked.
- **Unique Ability**: Each hero has a signature ability, which can be used strategically during gameplay.

---

## Game Flow

1. **Start of the Game**
   - Players shuffle their decks and draw their starting hand (e.g., 5 cards).
   - A coin toss determines which player goes first.

2. **Player Turn**
   Each player’s turn consists of:
   - Drawing a card from their deck.
   - Gaining mana (incremental mana growth may apply, e.g., gaining +1 maximum mana per turn).
   - Playing minion cards by spending mana.
   - Activating hero abilities.
   - Declaring attacks with their minions or hero.

3. **Combat**
   - Attacks can target opposing minions or the enemy hero.
   - Damage is dealt simultaneously (e.g., both attacking and defending minions lose health equal to the opponent's attack value).
   - Minions with **0 health** are removed from the board.

4. **End of Turn**
   - Players end their turn when they’ve spent their mana or made all desired moves.
   - The opponent begins their turn.

5. **Winning the Game**
   - A player wins when their opponent's hero health is reduced to **0**.

---

# Characters and Cards

## 1. Hero Cards
Each player is assigned a random hero at the beginning of the game. Heroes have unique abilities and fixed health (30). Their abilities cost mana and can only be used once per turn.

### Heroes and Abilities:
- **Lord Royce** (*Ability: Sub-Zero*)  
  - Freezes all minions on the enemy row for one turn.
  - **Can target only enemy rows.**

- **Empress Thorina** (*Ability: Low Blow*)  
  - Destroys the minion with the highest health on an enemy row.
  - **Can target only enemy rows.**

- **King Mudface** (*Ability: Earth Born*)  
  - Grants +1 health to all minions on an allied row.
  - **Can target only allied rows.**

- **General Kocioraw** (*Ability: Blood Thirst*)  
  - Grants +1 attack to all minions on an allied row.
  - **Can target only allied rows.**

---

## 2. Minion Cards
Minion cards are deployed to rows based on their type. They serve offensive and defensive roles, with stats and abilities tailored to various strategies.

### Attributes:
- **Mana Cost**: Determines the mana required to play the card.
- **Health**: Tracks the durability of the card; when it reaches 0, the card is removed from the board.
- **Attack Damage**: The damage dealt to enemies.
- **Description**: A flavor text or short lore.
- **Colors**: Defines the card’s design.
- **Name**: Identifies the card.

### Minion Types:
- **Sentinel, Berserker**:  
  Placed on the **back row** for strategic defense or support.

- **Goliath, Warden**:  
  Placed on the **front row** with a special **Tank ability**, requiring enemies to target them first.

---

## 3. Special Minion Cards
These minions have unique abilities that can influence the game dynamically. Their effects last beyond a single round.

### Special Minions and Abilities:
- **The Ripper** (*Ability: Weak Knees*)  
  - Reduces an enemy minion’s attack by 2 (minimum 0).
  - **Must be placed on the front row.**

- **Miraj** (*Ability: Skyjack*)  
  - Swaps health with an enemy minion.
  - **Must be placed on the front row.**

- **The Cursed One** (*Ability: Shapeshift*)  
  - Swaps the attack and health of an enemy minion. If the minion’s attack becomes 0, it is destroyed.
  - **Must be placed on the back row.**

- **Disciple** (*Ability: God's Plan*)  
  - Grants +2 health to an allied minion.
  - **Must be placed on the back row.**


### Heroes
#### **1. Fire Mage**
- **Mana Cost**: 4
- **Ability**: Deals 5 damage to all enemy minions.
- **Hero Health**: 30

#### **2. Shadow Priest**
- **Mana Cost**: 3
- **Ability**: Heals a friendly minion or hero by 6 health.
- **Hero Health**: 30

#### **3. Storm Warrior**
- **Mana Cost**: 5
- **Ability**: Summons a 3/3 lightning minion with charge (attacks immediately).
- **Hero Health**: 30

---

## Strategies and Tips

- **Resource Management**: Spend mana wisely each turn. Always plan ahead for the next turn.
- **Board Control**: Prioritize eliminating powerful enemy minions to maintain control of the board.
- **Hero Abilities**: Use abilities strategically—they can turn the tide of battle.
- **Synergy**: Build a deck that complements your hero’s abilities and enhances your strategy.

