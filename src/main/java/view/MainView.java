package view;

import java.util.Scanner;

/**
 * This class contains the main function and is used to start the game
 */
public class MainView {
	/**
	 * This is the main function which is executed first and initializes variables 
	 * before start of the game
	 * 
	 * @param args Command-line arguments
	 * @return void It does not return anything
	 */
	public static void Main(String[] args) {
		Scanner l_scannerObject = new Scanner(System.in);
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		System.exit(0);
	}

}