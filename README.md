# MazeSolver

Java implementation of the Solving Maze algorithm presented by Computerphile.

### Usage

You can check out the releases page to download binaries. 
[Java 16](https://adoptopenjdk.net) needs to be installed.

_NOTE_: If you download the os/platform specific binary (`*-<platform>.zip`) you do not need Java installed on your system.

From the bin folder, you can run:

- Linux & Mac

```shell script

$./maze-solver

```

- Windows

```shell script

bin>.\maze-solver

```

### Details

The main idea is that there is maze represented by a binary image.
The image contains white pixels which represent free tiles and black pixels which represent impassable / blocked tiles.
There is a single free tile on the top row, which represents the starting point.
There is also a single free tile on the last row, which represents the destination / maze exit.
The goal is to find the shortest path from the starting point to the maze exit.

This project is inspired by the [maze solving algorithm from Computerphile](https://www.youtube.com/watch?v=rop0W4QDOUI).
There is a [dedicated repository](https://github.com/mikepound/mazesolving) for this. The original implementation is in Python.
I have also used the [same mazes](https://github.com/mikepound/mazesolving/tree/master/examples).

### Supported algorithms

The currently supported algorithms are:
- Dijkstra (default)
- A*
- Breadth-First-Search (BFS)
- Depth-First-Search (DFS)

The algorithm / strategy can be specified when running the application:

```shell script

$./maze-solver -s ${strategy}

```

Where strategy can be one of the following:
- dijkstra
- a-star
- bfs
- dfs

### Dependencies

The only dependency is [JUnit](https://junit.org/junit5/) ([Eclipse Public License](https://github.com/junit-team/junit5/blob/master/LICENSE.md)).

### Examples and notes

__NOTE:__ Be careful when using bigger mazes as it will use a lot of CPU and RAM.

A file chooser will be launched upon executing the jar file, either directly or via command line, prompting you to select one of the mazes, unless the maze path is already specified using either the -mp or --maze-path options.

Upon selecting the maze, the shortest path will be searched and after that a file will be written in the same directory as the original file 
with the name solved_\<original-file-name\>.

At the moment only PNG format is supported.

Some sample data:

- tiny.png

![original](docs/images/original_tiny.png) ![solved](docs/images/solved_tiny.png)

- small.png

![original](docs/images/original_small.png) ![solved](docs/images/solved_small.png)

- normal.png

![original](docs/images/original_normal.png) ![solved](docs/images/solved_normal.png)

Here is an example of a bigger one (4k x 4k pixels):
![monster](docs/images/solved_perfect4k.png)

And an even bigger one (10k x 10k):
![nemesis](docs/images/solved_perfect10k.png)

You can find multiple solved mazes in the docs directory.
