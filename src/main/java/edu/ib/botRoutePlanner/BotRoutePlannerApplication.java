package edu.ib.botRoutePlanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class BotRoutePlannerApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(BotRoutePlannerApplication.class, args);

		//Importing content of .txt files
		List<String> content = new ArrayList<>();
		if (args.length == 2){
			for(String s : args){
				content.add(readFile(s));
			}
		}

		//---------- SETUP OF STORAGE --------------

		//Initializing storage grid
		Grid grid = new Grid(content.get(0));

		//Initializing bot
		Bot bot = new Bot(content.get(1));

		//Initializing destitation module
		grid.initializeDestinationModule(content.get(1));

		//Initializing job
		Job job = new Job(content.get(1));

		//Initializing Storage variables
		Storage storage = new Storage(grid, bot, job);


		//------------- ORDERS --------------------

		//Finding nearest product
		Product nearestProduct = storage.findNearestProduct();

		//PATH FINDING of product
		List<int[]> path = new ArrayList<>();
		path.add(new int[]{bot.getX(), bot.getY()});

		//Adding path to desired product
		List<int[]> pathToProduct = storage.findRouteToStation(new Point(nearestProduct.getX(), nearestProduct.getY()));
		storage.makeStepWithBot(pathToProduct);
		for (int[] step : pathToProduct){
			path.add(step.clone());
		}

		//Finding nearest collecting station
		storage.getProductFromModule(nearestProduct);

		//Path to collecting station
		Point nearestStation = storage.findNearestStation();
		List<int[]> pathToStation = storage.findRouteToStation(nearestStation);
		storage.makeStepWithBot(pathToStation);
		for (int[] step : pathToStation){
			path.add(step.clone());
		}

		//Saving results
		writeResult("result" , composeOutput((storage.getBot().getPath().size()-1), storage.getCurrentJob().getTime(),
				path
		));
	}

	/**
	 * Function for reading .txt files
	 * @param filename:String - name of file
	 * @return content:String - content of file
	 * @throws IOException
	 */
	public static String readFile(String filename) throws IOException {
		String content = "";
		String currentPath = new java.io.File(".").getCanonicalPath();

		File file = new File(currentPath + "/" + filename);
		Scanner sc = new Scanner(file);

		while (sc.hasNextLine())
			content = content + sc.nextLine() + "\n";

		return content;
	}

	/**
	 * Function for saving data into a .txt file
	 * @param filename - name of file
	 * @param data - desired data
	 * @return
	 * @throws IOException
	 */
	public static String writeResult(String filename, String data) throws IOException {
		String content = "";
		String currentPath = new java.io.File(".").getCanonicalPath();
		Path path = Path.of(currentPath + "/" + filename + ".txt");

		// Writing into the file
		Files.writeString(path, data);

		return content;
	}

	/**
	 * Function that connects provided data into one String
	 * @param steps:int - no. of steps
	 * @param time:double - time of job
	 * @param path:List<int[]> - list of steps
	 * @return output:String - coneccted data
	 */
	public static String composeOutput(int steps, double time, List<int[]> path){
		String output = steps + "\n" + time + "\n";
		for(int[] step : path){
			output = output + Arrays.toString(step).replace("[", "").replace("]","").replace(",","")
					+ "\n";
		}
		return output;
	}

}
