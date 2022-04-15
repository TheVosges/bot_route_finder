
Bot Route Finder
==========

<p>
A java project that manages bot in modernized storage. Porpuse of this bot is to find desired product in storage grid which contains diffrent modules (diffrent speed of product recovery and speed of transit).
<center>
            
![](https://i.imgur.com/SRxSxik.png)
            <br>
Image 1. Ocado storage 
      
</center>
    
Storage is represented by 3D grid X/Y/N - where X and Y size of grid and N - number of products that can be stored in module. 


## Installing

Download this project to desired directionary. Open terminal in main folder and write:
```bash=
$ mvn package
$ cd target
$ java -jar botRoutePlanner-0.0.1-SNAPSHOT.jar <example_grid.txt> <example_job.txt>
```

## Intro
The library contains following classes:
* Bot - class that represent a bot and its parameters
* Grid - class that represents grid of storage, its modules, size, module size and products
* Job - class that represent one job - its product name and how much time it took to complete
* ModuleType - enum that stores data about diffrent modules
* Product - class that represent a product and its posistion in grid
* Storage - **main** class that represents whole storage - its grid, bot and what job it is currently on

and controll class:
* BotRoutePlannerApplication - class that controlls all initialization of data, processing of files, pathfinding and saving.

