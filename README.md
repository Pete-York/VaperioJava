# VaperioJava
An implementation of Vaperio in Java, in order to allow use of the following framework: https://github.com/ljialin/SimpleAsteroids. This repo is a fork of that project.

All of the work in the java implementation is in VaperioJava/src/vaperio

The packages are as follows:
controllers: handles human input through the keyboard
core: the core game-engine which handles modelling the Vaperio game, and tracking the game-state
design: this is for handling the design-space of the game, which is the aspects of the game engine which can be tweaked for design purposes
test: this contains functional tests of the game engine and tests for agents in the game.
view: handles displaying the state of the model to the user
