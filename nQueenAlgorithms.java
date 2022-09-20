import java.util.ArrayList;
import java.util.Random;

public class nQueenAlgorithms {
		
	//count the amount of correct solutions that have been found for nQueen.
	public static int solutionCount = 0;
	
	//Create the ArrayLists that hold the population and the offspring to be selected.
	public static ArrayList<gameGenetics> totalPopulation = new ArrayList<gameGenetics>();
	public static ArrayList<gameGenetics> offSpring = new ArrayList<gameGenetics>();
	//Create the boolean value that identified if the optimal offspring result has been found.
	public static boolean optimalOffspringFound;
	
	
	//main method
	public static void main(String[] args) throws Exception {
		//generate all possible solutions of 8-Queen 
		generateNQueenSolutions(makeMatrixOfNSize(10),0,true,10);
		
		//run the genetic algorithm with six generations at a n-queen size of 8.
		nQueenGeneticAlgorithm(5,8);
	}
	
	/**
	 * This method run the algorithm that solves the nQueen game.
	 * @param game This is blank matrix that is used for the algorithm to solve
	 * @param row this is the starting row (zero) for the algorithm to start by
	 * @param hasLimit Defines if there is a solving limit.
	 * @param limit The limit of solution to be generated (if the hasLimit variable is true)
	 * @throws Exception throws exception to the dependencies
	 */
	public static void generateNQueenSolutions(char[][] game, int row, 
		boolean hasLimit, int limit) throws Exception {
		//Throw exception if the bound is negative
		if((hasLimit) && (limit < 0)) {
			throw new Exception("[Error!] You cannot have a negative function limit bound.");
		}
		//Generate prompts prior to executing process.
		if(hasLimit) {
			if(limit > 1) {
				System.out.println("Printing "+limit+" solutions for a "
						+game.length+"x"+game.length+" matrix of "+game.length+" Queen.");
			}
			else {
				System.out.println("Printing "+limit+" solution for a "
						+game.length+"x"+game.length+" matrix of "+game.length+" Queen.");
			}
		}
		else {
			System.out.println("Printing all solutions for a "
					+game.length+"x"+game.length+" matrix of "+game.length+" Queen.");
		}

		queenBacktrackAlgorithm(game,row,hasLimit,limit);
		//If the inputs is zero then return a "no solution" prompt
		if(solutionCount == 0) {
			System.out.println("No Solutions Found");
		}
		//print the completion prompt
		System.out.println("Process Complete");
		//reset the found solutions counter back to zero.
		solutionCount = 0;
	}

	/**
	 * This method traverses 'peak' (non-conflicting solutions) and 'fall' (conflicts)
	 * using a backtracking version algorithm.
	 * @param game This is the game matrix that is being solved
	 * @param row This is the starting row (always at zero)
	 * @param hasLimit This imposes a limit if a numberical limit is desired 
	 * for the variable 'limit' below.
	 * @param limit (if enabled, see 'hasLimit') The limit of the amout of solutions 
	 * that are being solved.
	 */
	public static void queenBacktrackAlgorithm(char[][] game, int row, boolean hasLimit, int limit) {
        //If row low has been filled then return the solution
		if (row == game.length) {
			//update the solution counter
        	solutionCount++;
        	//printout the solution counter
        	System.out.println("Solution: "+solutionCount);
        	//printout the given solution at that algorithmic iteration
        	printBoard(game);
        	//break the function call
            return;
        }
		//for every column until the limit of the game matrix
        for(int column = 0; column < game.length; column++) {
            //if given queen at the location (row x column) @ the given matrix
        	if(isQueenSafe(row,column,game)) {
        		//update the char at the matrix to 'Q' at the given point (row x column)
                game[row][column] = 'Q';
                //if the solution limit has not been reached and has been enabled
                if(solutionCount < limit || !(hasLimit)) {
                	//call the function with a incremented row 
                	queenBacktrackAlgorithm(game,(row+1),hasLimit,limit);
                }
                //backtrack to the char at the given point (row x column) to '_'.
                game[row][column] = '_';
            }
        }
	}

	/**
	 * This method aggregates the methods isUpSafe(), isDiagonalUpRightSafe(),
	 * & isDiagonalUpLeftSafe() and returns a boolean value that reflects 
	 * all of those functions.
	 * @param qRow The row at the given matrix
	 * @param qColumn The column at the given matrix
	 * @param game the matrix to analyze
	 * @return return a aggregated boolean value.
	 */
	public static boolean isQueenSafe(int qRow, int qColumn, char[][] game) {
		//return a boolean value of all of the functions.
		if(isUpSafe(qRow,qColumn,game) && isDiagonalUpRightSafe(qRow,qColumn,game)
			&& isDiagonalUpLeftSafe(qRow,qColumn,game)) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method return a boolean is the diagonal pattern above the 
	 * given point is safe.
	 * @param qRow This given point of the row
	 * @param qColumn The given point of the column
	 * @param game The game matrix
	 * @return return a boolean true value if the diagonal up left is safe.
	 */
	public static boolean isDiagonalUpLeftSafe(int qRow, int qColumn, char[][] game) {
		//check for each diagonal up left with a offset to exclude the given point
		while(qRow > 0 && qColumn > 0) {
			qRow--;
			qColumn--;
			if(game[qRow][qColumn] == 'Q') {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method return a boolean if the diagonal pattern above the 
	 * given point is safe.
	 * @param qRow This given point of the row
	 * @param qColumn The given point of the column
	 * @param game The game matrix
	 * @return return a boolean true value if the diagonal up right is safe.
	 */
	public static boolean isDiagonalUpRightSafe(int qRow, int qColumn, char[][] game) {
		//check for each diagonal up right with a offset to exclude the given point
		while(qRow > 0 && qColumn < game.length-1) {
			qRow--;
			qColumn++;
			if(game[qRow][qColumn] == 'Q') {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method returns a boolean if line above the point is safe
	 * @param qRow This given point of the row
	 * @param qColumn The given point of the column
	 * @param game The game matrix
	 * @return return a boolean true value if the up is safe.
	 */
	public static boolean isUpSafe(int qRow, int qColumn, char[][] game) {
		//check for each up with a offset to exclude the given point
		for(int row = qRow; row >= 0; row--) {
			if(game[row][qColumn] == 'Q') {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Create a blank 2D matrix at the given size
	 * @param size This is the size of the matrix
	 * @return returns a char[][] matrix.
	 */
	public static char[][] makeMatrixOfNSize(int size) {
		//create a matrix at the size
		char[][] game = new char[size][size];
		//fill the matrix with blanks
		for(int row = 0; row < game.length; row++) {
			for(int column = 0; column < game[row].length; column++) {
				game[row][column] = '_';
			}
		}
		//return the result
		return game;
	}
	
	/**
	 * This method printout the board
	 * @param board this is the matrix to be printed out.
	 */
	public static void printBoard(char[][] board) {
		//for every row/element in the board
		for(int row = 0; row < board.length; row++) {
			for(int column = 0; column < board[row].length; column++) {
				//if the current index is at the end of the matrix, impose the 
				//respective formating.
				if((column < board[row].length-1)) {
					System.out.print("|"+board[row][column]);
				}
				else {
					System.out.print("|"+board[row][column]+"|");
				}
			}
			System.out.println("");
		}
		System.out.println("");
	}

	
	
	
	/**
	 * This function performs the full genetic algorithmic solving function for nQueen
	 * at a generation size.
	 * @param amountOfGenerations The desired amount of generations
	 * @param nQueenSize The side of the nQueen board
	 * @throws Exception throws exception on the dependencies given. 
	 */
	public static void nQueenGeneticAlgorithm(int amountOfGenerations, int nQueenSize) throws Exception{
		//calculate the initial amount of required matrix sample starting population.
		int startingPopulation = (int) Math.pow(2, amountOfGenerations);
		//use the calculated initial amount and create the entire starting population.
		createInitialPopulation(startingPopulation,nQueenSize);
		//reset the variable to false
		optimalOffspringFound = false;
		//start the index varible to zero
		int entry = 0;
		//while the optimal offspring has not been found and the index is less than the ArrayList size.
		while(entry < totalPopulation.size()-1 && !optimalOffspringFound) {
			//run the reproduction process to yield the given offspring to sample for the next gen.
			breedAndSelectBestOffSpring(entry,entry+1);
			//update the index variable by two.
			entry = entry + 2;
		}
		//printout the total population.
		printTotalPopulation();
		//print the results
		System.out.println("");
		System.out.println("Best result found in the genetic n-queen algorithm: ");
		System.out.println(totalPopulation.get(totalPopulation.size()-1));
		totalPopulation.get(totalPopulation.size()-1);
	}
		
	/**
	 * This algorithm takes in two parent indexes from the total population and creates 
	 * five child matrixes, then the oldest best matrix is selected in order 
	 * @param parent1Index index of parent1 in reference to the totalPopulation arraylist.
	 * @param parent2Index index of parent2 in reference to the totalPopulation arraylist.
	 * @throws Exception throws exception as part of dependency.
	 */
	public static void breedAndSelectBestOffSpring(int parent1Index, int parent2Index) throws Exception {

		System.out.println("[Process] Creating and Selecting the best offspring from parent1 (ID#: "
				+parent1Index+ ") and parent2 (ID#: "+parent2Index+").");
		//store parent fields
		int parentGeneration = totalPopulation.get(parent1Index).getGeneration();
		int parent1ID = parent1Index;
		int parent2ID = parent2Index;
		//get the genetic information from the parents
		int[] parent1Genetics = totalPopulation.get(parent1Index).getGenetics();
		int[] parent2Genetics = totalPopulation.get(parent2Index).getGenetics();
		//create new offspring nodes with the reproduced matrix data from the parents
		for(int i = 0; i < 5; i++) {
			offSpring.add(new gameGenetics(parentGeneration+1,
					parent1ID,parent2ID,breed(parent1Genetics,parent2Genetics),false));
			//mutate offspring node genetics
			offSpring.get(i).mutateGenetics();
		}
		//printout the offspring
		for(int i = 0; i < offSpring.size(); i++) {
			System.out.println("\t"+offSpring.get(i));
		}
		//for all of the children (descending order)
		int bestMatchIndex = offSpring.size()-1;
		for(int i = offSpring.size()-1; i >= 0; i--) {
			//update the best selection if the current child node is better than the prior.
			if(offSpring.get(i).getFitness() >= offSpring.get(bestMatchIndex).getFitness()) {
				bestMatchIndex = i;
			}
		}

		//update the viable offspring node with a official ID
		offSpring.get(bestMatchIndex).assignID();
		//if the offspring node is perfect then break the selection/reproduction process.
		if(offSpring.get(bestMatchIndex).getFitness() == 100.0 
			&& (offSpring.get(bestMatchIndex).getGeneration() > 0)) {
			optimalOffspringFound = true;
		}
		
		//printout prompt
		System.out.println("\t[Update] Best Match Found:");
		System.out.println("\t\t"+offSpring.get(bestMatchIndex));
		//add of the successful offspring nodes to the total population node
		totalPopulation.add(offSpring.get(bestMatchIndex));
		//delete all data to the offspring arraylist.
		offSpring.clear();
	}

	/**
	 * This method takes in two parent arrays and randomly splices
	 * their partial arrays into one reproduced array.
	 * @param parent1 "DNA" signature on parent1 in an array
	 * @param parent2 "DNA" signature on parent2 in an array
	 * @return returns the recombined array
	 * @throws throws exception if the arrays are of unequal in terms of length
	 */
	public static int[] breed(int[] parent1, int[] parent2) throws Exception {
		//throw exception
		if(parent1.length != parent2.length) {
			throw new Exception("[Error] Two array of unequal length");
		}
		//create random object
		Random randomMergePoint = new Random();
		//create random split point 
		int split = randomMergePoint.nextInt(parent1.length-1);
		//return array
		int[] result = new int[parent1.length];
		//add the randomly spliced array from parent1 to the returned result
		for(int par1 = 0; par1 < split; par1++) {
			result[par1] = parent1[par1];
		}
		//add the randomly spliced array from parent2 to the returned result
		for(int par2 = split; par2 < parent2.length; par2++) {
			result[par2] = parent2[par2];
		}
		//return result.
		return result;
	}

	/**
	 * This method printout the entire population of the totalPopulation ArrayList.
	 */
	public static void printTotalPopulation() {
		//printout all of the matrix nodes in the arraylist.
		System.out.println("[Process] Printing out the entire population.");
		for(int i = 0; i < totalPopulation.size(); i++) {
			System.out.println(totalPopulation.get(i));
		}
	}

	/**
	 * This method creates the initial population for the totalPopulations 
	 * for the rest of the processes.
	 * @param populationSize This is the population to generate
	 * @param nQueenSize the size of the nQueen matrix to make
	 */
	public static void createInitialPopulation(int populationSize,int nQueenSize) {
		//create random population objects
		for(int pop = 0; pop < populationSize; pop++) {
			totalPopulation.add(new gameGenetics(0,-1,-1,createRandomGenes(nQueenSize),true));
		}
	}

	/**
	 * This method creates random ("DNA") matrix data for the given
	 * matrix input size.
	 * @param size this is the size to generate for the matrix
	 * @return return the nQueen data array.
	 */
	public static int[] createRandomGenes(int size) {
		//create random object.
		Random randomSelection = new Random();
		//create the array
		int[] randomSample = new int[size];
		//populate the array with random data
		for(int i = 0; i < randomSample.length; i++) {
			randomSample[i] = randomSelection.nextInt(randomSample.length);
		}
		//return array
		return randomSample;
	}

}

class gameGenetics {
//create the attributes/objects required for the constructor
int[] DNA;
int parent1;
int parent2;
int generation;
int ID;
boolean isOfficialEntry;
double genenticFitness;
int individualID = 0;
Random randonGeneMutation = new Random();

//default constructor
public gameGenetics(int generation, int parent1, int parent2, int[] DNA, boolean isOfficialEntry) {
	//if the officialEntry is true, then assign as ID number
	if(isOfficialEntry == true) {
		this.ID = individualID;
		individualID++;
	}
	//else, assign a "null" value of -1 (this is for non-viable offspring)
	else {
		this.ID = -1;
	}
	this.isOfficialEntry = isOfficialEntry;
	this.parent1 = parent1;
	this.parent2 = parent2;
	this.generation = generation;
	this.DNA = DNA;
	this.genenticFitness = geneticFitnessCheck(DNA);
	
}

/**
 * This is a getter method for parent1
 * @return returns the reference of parent1
 */
public int getParent1() {
	return this.parent1;
}

/**
 * This method is a setter method 
 * for parent1 reference.
 * @param parent1 this is the value that is parsed into the parent reference.
 */
public void setParent1(int parent1) {
	this.parent1 = parent1;
}

/**
 * This is a getter method for parent2
 * @return returns the reference of parent2
 */
public int getParent2() {
	return this.parent2;
}

/**
 * This method is a setter method 
 * for parent2 reference.
 * @param parent1 this is the value that is parsed into the parent reference.
 */
public void setParent2(int parent2) {
	this.parent2 = parent2;
}

/**
 * This method returns the array of the "genetic"
 * information that a population object has.
 * @return returns a array of the nQueen data.
 */
public int[] getGenetics() {
	return this.DNA;
}

/**
 * This method is a setter method for the 
 * "genetic" information that the population
 * object has.
 * @param DNA This is the array of the nQueen data.
 */
public void setGenetics(int[] DNA) {
	this.DNA = DNA;
}

/**
 * This method mutates the genetics (The DNA array) of the given object
 */
public void mutateGenetics() {
	//create random int for the mutation function
	int gene = randonGeneMutation.nextInt(this.DNA.length);
	int mutation = randonGeneMutation.nextInt(this.DNA.length);
	//print the update
	System.out.println("\tIndividual Offspring (ID #"+this.ID+") has gene "+gene+" mutated from "
			+this.DNA[gene]+" to "+mutation);
	this.DNA[gene] = mutation;
	//update the data in the genentics array
	this.genenticFitness = geneticFitnessCheck(DNA);
}
	
/**
 * This is a getter method for the generation field
 * @return return the value of the generation for the
 * given object.
 */
public int getGeneration() {
	return this.generation;
}

/**
 * This is a getter method for getting officiality 
 * status of the given object.
 * @return returns the boolean status of the 
 */
public boolean getOfficialityStatus() {
	return this.isOfficialEntry;
}

/**
 * This is a getter method for the ID field
 * @return returns the value of the field ID.
 */
public int getID() {
	return this.ID;
}

/**
 * This method assigns an official ID to the given
 * object provided that its current ID number is -1.
 */
public void assignID() {
	if(this.isOfficialEntry == false && this.ID == -1) {
		this.ID = individualID;
		individualID++;
	}
}

/**
 * This method returns the given genetic fitness
 * of the given DNA data of the object.
 * @return
 */
public Double getFitness() {
	return this.genenticFitness;
}

/**
 * This is a toString method that printout all of the object data.
 */
public String toString() {
	return "[ID: "+this.ID+" | Generation: "+this.generation+" | index of Parent1: "
			+this.parent1+" | index of Parent2: "+this.parent2
			+" | Genome: "+printArray(this.DNA)+"| Fitness: "+this.genenticFitness+"%]";
}

/**
 * This is a method returns a string of the given array.
 * @param array this is the array that is converted to a 
 * string
 * @return returns a string value of the given array. 
 */
private static String printArray(int[] array) {
	//output string
	String output = "";
	output = output + "{";
	//for every row/column iterate and update output
	for(int i = 0; i < array.length; i++) {
		if(!(i < array.length-1)) {
			output = output + array[i];
		}
		else {
			output = output + array[i]+",";
		}
	}
	output = output +"}";
	//return result
	return output;
}

/**
 * This method returns a double percentage value of the 
 * genetic 'fitness' of the DNA array for the given object.
 * @param compressedMatrix
 * @return
 */
public static Double geneticFitnessCheck(int[] compressedMatrix) {
	//expand to a full matrix from the given
	char[][] expandedMatrix = expandToFullMatrix(compressedMatrix);
	//create a array to store all of the boolean values
	boolean[] results = new boolean[compressedMatrix.length];
	//amount of viable position returns
	int amountOfFitResults = 0;
	
	//for every row
	for(int row = 0; row < expandedMatrix.length; row++) {
		results[row] = isLocationFit(row,compressedMatrix[row],expandedMatrix);
		//if the result at the position is true then update the amountOfFitResults.
		if(results[row] == true) {
			amountOfFitResults++;
		}
	}
	//return the fitness score
	return((((double)amountOfFitResults)/((double)compressedMatrix.length))*100);
}

/**
 * 
 * @param qRow
 * @param qColumn
 * @param game
 * @return
 */
public static boolean isLocationFit(int qRow, int qColumn, char[][] game) {
	//create the variables required for all opperations
	int row = qRow;
	int column = qColumn;	

	//check if the vertical line down is safe
	for(int indexRow = (qRow+1); indexRow < game.length; indexRow++) {
		//if the position is unsafe
		if(game[indexRow][qColumn] == 'Q') {
			//if the position is unsafe
			return false;
		}
	}
	//reset variables
	row = qRow;
	column = qColumn;
	//check if the diagonal line down left is safe
	while(row < game.length-1 && column > 0) {
		row++;
		column--;
		//if the position is unsafe
		if(game[row][column] == 'Q') {

			return false;
		}
	}
	//reset variables
	row = qRow;
	column = qColumn;
	//check if diagonal line down right is safe
	while(row < game.length-1 && column < game.length-1) {
		row++;
		column++;
		//if the position is unsafe
		if(game[row][column] == 'Q') {
			return false;
		}
	}
	

	//check if vertical line up is safe
	for(int indexRow = (qRow-1); indexRow >= 0; indexRow--) {
		//if the position is unsafe
		if(game[indexRow][qColumn] == 'Q') {
			return false;
		}
	}
	//reset variables
	row = qRow;
	column = qColumn;		
	//check if diagonal up left line is safe
	while(row > 0 && column > 0) {
		row--;
		column--;
		//if the position is unsafe
		if(game[row][column] == 'Q') {
			return false;
		}
	}
	//reset variables
	row = qRow;
	column = qColumn;
	//check if diagonal line up right is safe
	while(row > 0 && column < game.length-1) {
		row--;
		column++;
		//if the position is unsafe
		if(game[row][column] == 'Q') {
			return false;
		}
	}
	//return true if the all other checks are safe
	return true;
}



/**
 * This method take the compressedMatrix (The 'DNA' data)
 * into the full expended n-size matrix.
 * @param compressedMatrix The genetics data that is used to expand the 
 * full game
 * @return return the 2D matrix of the game
 */
public static char[][] expandToFullMatrix(int[] compressedMatrix) {
	
	char[][] convertedOutput = makeMatrixOfNSize(compressedMatrix.length);
	for(int row = 0; row < compressedMatrix.length; row++) {
		convertedOutput[row][compressedMatrix[row]] = 'Q';
	}
	return convertedOutput;
}

/**
 * Create a blank 2D matrix at the given size
 * @param size This is the size of the matrix
 * @return returns a char[][] matrix.
 */
public static char[][] makeMatrixOfNSize(int size) {
	//create a matrix at the size
	char[][] game = new char[size][size];
	//fill the matrix with blanks
	for(int row = 0; row < game.length; row++) {
		for(int column = 0; column < game[row].length; column++) {
			game[row][column] = '_';
		}
	}
	//return the result
	return game;
}


/**
 * This method printout the board
 * @param board this is the matrix to be printed out.
 */
public static void printBoard(char[][] board) {
	//for every row/element in the board
	for(int row = 0; row < board.length; row++) {
		for(int column = 0; column < board[row].length; column++) {
			//if the current index is at the end of the matrix, impose the 
			//respective formating.
			if((column < board[row].length-1)) {
				System.out.print("|"+board[row][column]);
			}
			else {
				System.out.print("|"+board[row][column]+"|");
			}
		}
		System.out.println("");
	}
	//System.out.println("");
}

}

