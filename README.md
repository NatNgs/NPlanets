# NPlanets
Multiplayer Gestion-Conquest game in Java. The goal is to conqueer all universe using spaceships and ressources available.

# Status

- Game Desgin: **Done**, allowing changes in case of new ideas
- Game Motor: **Indev**, in development
- User Interface: **Indev**, proof or concept in progress (currently not pushed on the git).
- Server/Network: **Planned**, not yet developped

# Gameplay

## Map creation

Before playing, game map is generated (randomly, partially randomly or not).

There is 2 kind of celestial bodies (I will say "Planets" even if it can be sattelites, stars, black holes...) that can be used to make a map:
- Static planet (Always located at the same relative location of his ref)
- Moving planet (moving around his ref, following a predictible equation)  
Equation can describe a circle/elipsis, but it can also be a stroke (going and going back), a square, any polygon...

All planets are moving (or not) around a referential (that can be the center of the map (aka Universe) or another planet).  
This way, a sattelite can turn around another planet, that turn around a star, that turns around a galactic center, that move bizzarely around universe center.  

People can go on some planets, but not on others (like gaz planets, stars, black holes...)  
People can grow on some planets, but not on others (like asteroids, sattelites...), and on others at different rates (Water ? Oxygen ? ... ?)

## Init

Each player will chose planets they own at game start.
(We can also distribute randomly one, a part or all planets to players, or let them choose for only one, or specific number of planets, or using a kind of bet so that players have a specific budget to own planets, but planets most wanted will cost more (and so the player that will get this planet will have less budget to get more planet at game start))

Each player will start with the same number of people on planets (or total same number of people distributed on owned planets).

## Game Turn

The game is turn based.

Each turn, players choose to do all the actions they want (no limit other than resources of the player).  
Each action take a certain time to be done (count in turn, sometime with decimals)

When each players has finished his actions, all actions of the turn runs (according to the decimals, some will start/end before others inside a same turn).

The game continue like that.

## Actions of a player

A player can send ships containing people or ressources from a planet to another  
He can also create, move, upgrade or destroy space stations, send ship to or from it to planets or others  
He can send ships to others ships to increase fleet or doing more complex things

A player can build things on planets to exploit resources, increase defense of production of the planet  
A player can configure his owned planets and stations, to accept resources/people from others players or not for example (alliances)  

A planet is owned by the player that own at least 50% of the people on it.  
Sending people to the planet of another player make you became the owner.  
But if the other player disallow receiving people, you can attack it with fleets.  
That will decrase the number of people living on the planet, convert some of them to you, and add some of yours that flight to the planet. If the attack succeed, you may become the new owner of the planet (but you have destroyed it..)

## Goal ? Game End ?

There can be two kind of games:
- Players vs Players, the last player alive win
- Players vs Ennemy waves (like space invaders), where you should keep alive as long as you can (players may be all in the same team, or not if they want to fight them at the same time...)

In this waves game, many ships with invaders will appear from anywhere far away the center of the universe, and will try to conqueer all the planets using force.

Eventualy, players can be in teams.  
A player can also play alone in waves mode (or playing with and against AI players).


# Credits

Project name:
- NPlanets

Design: 
- NatNgs, since 2013

Development:
- NatNgs, since 2015
