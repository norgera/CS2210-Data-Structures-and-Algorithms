/**
 * @author Nathan Orgera
 * @date December 7, 2023
 */

//Imports
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;

//Makes a maze using a graph
public class Maze {

    //Instance variables
    private Graph graph;
    private GraphNode entrance;
    private GraphNode exit;
    private int numOKeys;

    //Constructor takes in a file name and builds a maze based on the values inside
    public Maze (String inputFile) throws MazeException {

        //Makes a new array to store all the lines in the file
        ArrayList<String> temp = new ArrayList<>();

        //Large try catch will catch a file not found
        try {
            Scanner scanner = new Scanner(new File(inputFile));

            //Scans the file
            while (scanner.hasNextLine()) {
                temp.add(scanner.nextLine());
            }
            scanner.close();

            //Checks the first 4 lines
            try{
                for (int z = 0; z < 4; z++) {Integer.parseInt(temp.get(z));}
            } catch (NumberFormatException e) {
                throw new MazeException("File not of proper format: First 4 lines contain un-parse-able ints");
            }

            //Checks other aspects of the file and stores some data from the first 4 lines
            if (temp.size() < 5) {throw new MazeException("File has too few lines");}
            if (temp.size() % 2 == 0) {throw new MazeException("File has an even number of lines");}

            this.numOKeys = Integer.parseInt(temp.get(3));

            int lineLength = Integer.parseInt(temp.get(1));
            lineLength = (lineLength * 2) - 1;

            this.graph = new Graph( Integer.parseInt(temp.get(1)) * Integer.parseInt(temp.get(2)) );

            //Initializes some counters
            int curNodeName = -1;
            int prevNodeName = 0;
            int wallCount = 0;

            //Giant for loop iterates through each line after the first 4
            for (int i = 4; i < temp.size(); i++) {
                if (temp.get(i).length() != lineLength) {throw new MazeException("File not of proper format: Lines 4 onward are not of the same size");}

                //Checks if the line is of the form RHRHRH
                if (i % 2 == 0) {
                    //Iterates throgh each character of the line
                    for (int p = 0; p < lineLength; p++) {
                        //If the character is at an even index, check if it's an entrance, exit, room, or other
                        if (p % 2 == 0) {
                            //R
                            curNodeName += 1;
                            switch (temp.get(i).charAt(p)) {
                                //Entrance
                                case 's':
                                    try {
                                        //Assign entrance
                                        this.entrance = graph.getNode(curNodeName);
                                        //curNodeName += 1;
                                        //System.out.println("Entrance");
                                    } catch (GraphException e) {throw new MazeException("ERROR 54");}
                                    break;
                                    //Exit
                                case 'x':
                                    //Assign exit
                                    try {
                                        this.exit = this.graph.getNode(curNodeName);
                                        //curNodeName += 1;
                                        //System.out.println("Exit");
                                    } catch (GraphException e) {throw new MazeException("ERROR 61");}
                                    break;
                                    //Room
                                case 'o':
                                    //curNodeName += 1;
                                    //System.out.println("Room");
                                    break;
                                default:
                                    throw new MazeException("File not of proper format: Lines not formatted correctly 65");
                            }

                            //If the character is at an odd index checks if it's a door, a corridor or wall
                            //H
                        } else {
                            try {
                                //Door
                                int tempDoor = Integer.parseInt(String.valueOf(temp.get(i).charAt(p)));
                                if (tempDoor > 9 || tempDoor < 0) {throw new MazeException("File not of proper format: Lines not formatted correctly 72 " + tempDoor);}
                                try {
                                    this.graph.insertEdge(graph.getNode(curNodeName), graph.getNode(curNodeName+1), tempDoor, "door");
                                    //System.out.println("Door");
                                } catch (GraphException e) {throw new MazeException("ERROR 78");}
                            } catch (NumberFormatException e) {
                                switch (temp.get(i).charAt(p)) {
                                    //Wall
                                    case 'w':
                                        //System.out.println("Wall");
                                        wallCount += 1;
                                        break;
                                        //Corridor
                                    case 'c':
                                        try {
                                            this.graph.insertEdge(graph.getNode(curNodeName), graph.getNode(curNodeName+1), 0, "corridor");
                                            //System.out.println("Corridor");
                                        } catch (GraphException z) {throw new MazeException("ERROR 89");}
                                        break;
                                    default:
                                        throw new MazeException("File not of proper format: Lines not formatted correctly 87");
                                }
                            }
                        }
                    }
                }
                //If the line is of the form VWVWVW
                else {
                    //Set up the counter
                    prevNodeName = curNodeName+1 - Integer.parseInt(temp.get(1));

                    //For each character in the line
                   for (int p = 0; p < lineLength; p++) {
                       //if the character index is even
                       if (p % 2 == 0) {
                           //System.out.println(":" + prevNodeName + " " + (prevNodeName + Integer.parseInt(temp.get(1))) + " " + curNodeName);

                           //Check if the characer is a wall or corridor
                           //V
                           try {
                               //Door
                               int tempDoor = Integer.parseInt(String.valueOf(temp.get(i).charAt(p)));
                               if (tempDoor > 9 || tempDoor < 0) {throw new MazeException("File not of proper format: Lines not formatted correctly 101");}
                               try {
                                   this.graph.insertEdge(graph.getNode(prevNodeName), graph.getNode(prevNodeName + Integer.parseInt(temp.get(1))), tempDoor, "door");
                                   //System.out.println("Door");
                               } catch (GraphException e) {throw new MazeException("ERROR 109 " + e);}
                           } catch (NumberFormatException e) {
                               switch (temp.get(i).charAt(p)) {
                                   //Wall
                                   case 'w':
                                       wallCount += 1;
                                       //System.out.println("Wall");
                                       break;
                                       //Corridor
                                   case 'c':
                                       try {
                                           this.graph.insertEdge(graph.getNode(prevNodeName), graph.getNode(prevNodeName + Integer.parseInt(temp.get(1))), 0, "corridor");
                                           //System.out.println("Corridor*");
                                       } catch (GraphException z) {throw new MazeException("ERROR 120 " + z);}
                                       break;
                                   default:
                                       throw new MazeException("File not of proper format: Lines not formatted correctly 116");
                               }
                           }
                           prevNodeName += 1;

                           //If the character is an odd index, confirm that it is a wall
                           //W
                       } else {
                           //Wall
                           if (temp.get(i).charAt(p) != 'w') {throw new MazeException("File not of proper format: Lines not formatted correctly 122");}
                           wallCount += 1;
                           //System.out.println("Wall");
                       }
                   }
                }
            }
            //Catcher for the file name
        } catch (FileNotFoundException e) {
            throw new MazeException("File: " + inputFile + " not found");
        }
    }

    //Getter for the graph, throwing an error if it's null
    public Graph getGraph() throws MazeException {
        if (this.graph == null) {throw new MazeException("Graph is null");}
        return graph;
    }

    //Stack used in path
    private Stack<GraphNode> stack;

    //Solves the maze by calling the recursive function path, if there is a path then an iterator of the stack is returned
    public Iterator<GraphNode> solve() {
        this.stack = new Stack<>();
        if (path(this.entrance, this.exit, 0)) {
            return stack.iterator();
        } else {
            return null;
        }
    }

    //Takes in two nodes, a start and an end; and an int of the number of keys used on a given path
    private boolean path(GraphNode s, GraphNode d, int keysUsed) {

        //If the keys used exceed the limit return false
        if (keysUsed > numOKeys) {return false;}

        //Push the node to the stack
        stack.push(s);

        //If the current node is the final return true
        if (s.getName() == d.getName()) {
            return true;
        }

        //Mark s
        s.mark(true);
        Iterator<GraphEdge> thing;

        //try catch for a graph exception
        try {
            thing = this.graph.incidentEdges(s);
        } catch (GraphException e) {return false;}

        //While the iterator has a next
        while (thing.hasNext()) {
            //Temp becomes next
            GraphEdge temp = thing.next();
            //Checks temp's first and second endpoint for the correct one, then checks if the node is marked, then checks if the edge is a door, if so call and add the keyvalue of the door, otherwise call normally
            if (temp.firstEndpoint().getName() != s.getName()){
                if (!temp.firstEndpoint().isMarked()) {
                    if (temp.getLabel().equals("door")) {
                        if (path(temp.firstEndpoint(), d, keysUsed + temp.getType())) {return true;}
                    } else {
                        if (path(temp.firstEndpoint(), d, keysUsed)) {return true;}
                    }
                }
            } else {
                if (!temp.secondEndpoint().isMarked()) {
                    if (temp.getLabel().equals("door")) {
                        if (path(temp.secondEndpoint(), d, keysUsed + temp.getType())) {return true;}
                    } else {
                        if (path(temp.secondEndpoint(), d, keysUsed)) {return true;}
                    }
                }
            }
        }
        // Marks the maze
        s.mark(false);
        //Then removes the unwanted nodes from the stack
        stack.pop();
        //Returns false if the ifs failed
        return false;
    }
}
