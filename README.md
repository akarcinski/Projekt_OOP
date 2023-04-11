# Symulation of animal evolution
 
This is the main project for the OOP lab course at the AGH. Before you start your simulation, you can choose hyperparameters of the simulation:
- the size of the map
- maximum number of grasses on the map
- value which is renewed by eating grass
- number of grasses growing every day
- maximum number of animals on the map
- the initial amount of energy
- maximum value of stored energy
- energy needed for the baby's arrival
- the magnitude of the mutation
- the length of the animal genome
- map type
- - Earth - sides of the map are merged: top to bottom and left to right
- - Infernal portal - if an animal wants to cross the border, it will be teleported to a random location
- biome type
- - jungle - in the middle of the map, there is an equator (jungle area has higher grass growing ratio)
- - steppe - all map is the desert (no jungle area)
- mutation type
- - small - the new child has the same genome as its parents
- - big - the new child has partially changed the genome
- behavior type
- - normal - next move is selected based on the following gene
- - crazy - next move is selected based on random gene

You can also load settings from a .csv file.
