GWJeroo is a library that lets students run
[Jeroo](http://home.cc.gatech.edu/dorn/jeroo) programs in
[GridWorld](http://www.horstmann.com/gridworld/).
GWJeroo is intended for students in Pre-AP and AP classes who are transitioning
from Jeroo to Java.

I developed this when I was a high school computer science teacher. I no longer
teach, so I don't expect to update it.

## Features

* GWJeroo code looks almost identical to Jeroo code.
* GWJeroo can use any feature in the Java language including variables, for
  loops, and out.println().
* GWJeroo is just a standard Java library that is distributed in a jar file. It
  compiles and runs in any normal Java IDE.
* When a GWJeroo program runs, students run their program one line at a time
  using Gridworld's step button.
* GWJeroo can read .jev files from Jeroo.
* In GWJeroo, the class Jeroo extends the class Actor. This means you can call
  any of Actor's methods, such as setColor() or moveTo() (But I don't plan on
  telling my students this too quickly. (Things like moveTo() make some
  problems too easy!))
* The island can have any number of Jeroos. You can also have multiple classes
  that extend Jeroo, so you can have different types of Jeroos.
<!--And of course, there is javadoc style documentation.-->

# Usage
To use GWJeroo, you need to put the file gwjeroo.jar in the classpath. This is
the only file from this project that students will need.

# Getting Started

I have included three sample programs to help you get started:

* SimpleJeroo.java - This is the solution to the Look Homeward Angel from the
	original Jeroo worked in GWJeroo.  It demonstrates how similar they are,
	and shows how to load a jev file.

* ExtendedJeroo.java - This is a more complex example that shows how to create
	a new type of Jeroo.  You have to create a subclass of Jeroo to create new
	methods.

* HookUpJeroo.java - After you have been teaching Java for a while, you can go
	back to this one.  It is intended to teach creating accessors and modifier
	methods.  Here's the story:  a bunch of Jeroos are going to the prom and
	they are looking for dates.  They wander around aimlessly until they run in
	to someone that meets their standards.  As the Jeroos wander, they lower
	their standards.  Students generate some interesting bugs while solving
	this problem.

# Misc.
Here's some assignments and resources other people created for GWJeroo:
http://www.mathwarehouse.com/cs/gwjeroo/ and
http://www.mrmonline.org/cs/gwjeroo/ .

I made a few small changes to the original GridWorld source code.  They are
documented in the file gridworld.diff .

