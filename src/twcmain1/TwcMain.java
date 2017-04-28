package twcmain1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import timewordcouple1.*;

public class TwcMain {

	public static void main(String[] args) throws IOException, ParseException {

		/** */

		String text = null;
		String listFunction = null;
		String fileName = null;
		String firstToken = null;

		Scanner input = new Scanner(System.in);

		DateFormat df = new SimpleDateFormat("HH:mm:ss"); // This will recognize
															// date format

		// Create an ArrayList to store and list the words. I want to see that
		// later everywhere. This should not change during running the program.
		List<TimeWordCouple> listOfWords = new ArrayList<TimeWordCouple>();

		// Menu
		do {

			System.out.println("Please enter a command: \n\"input\" or \n\"list\" or \n\"search\" or \n\"end\"");
			text = input.nextLine();

			switch (text) {
			case "input":
				do {
					System.out.println(
							"Choose from the list: \n\"new\" for a new list or \n\"exist\" to continue an existing list or \n\"back\" to go back to main menu.");

					listFunction = input.nextLine();

					/*
					 * When the user finished a new list or expanded a list for
					 * some reason listFunction = input.nextLine(); think that
					 * an empty line command is given to it and so that go to
					 * default case and rerun a cycle in do-while repeating the
					 * first sysout. System.out.println(listFunction); //help to
					 * debug if(listFunction.matches("")) {
					 * 
					 * }
					 */

					switch (listFunction) {
					case "new":
						System.out.println("Create a new file and fill the ArrayList...\n");

						try {

							System.out.println("Give me the file name what you want to create \"fileName.txt\": ");
							// Creating a file with the given file name, this
							// can be a function by refactoring later
							fileName = input.nextLine();
							File file = new File(fileName);
							file.createNewFile();

							System.out.println("Write words to input.");
							System.out.println("You can type your time and words now:");
							System.out.println("hh:mm:ss foreign_word mother_tongue_word [or type \"back\" to finish]");

							BufferedWriter bw = new BufferedWriter(new FileWriter(file)); // This
																							// is
																							// for
																							// output
							// BufferedReader br = new BufferedReader(new
							// FileReader(file)); // This is for input

							/**
							 * now, I have to create TimeWordCouple objects and
							 * put them into ArrayList, and later write the
							 * ArrayList to file.
							 */
							int i = 0; // later "linesInFile"

							do {
								// Read the line from console and separate data
								// couples = input.nextLine(); //hh:mm:ss word1
								// word2
								// Finds and returns the next complete token
								// from this scanner: .next();

								firstToken = input.next();
								if (firstToken.matches("back")) {
									break;
								}

								Date date = df.parse(firstToken); // This is
																	// parsing
																	// to date
																	// type

								TimeWordCouple twc = new TimeWordCouple(date, input.next(), input.next()); // Finds
																											// and
																											// returns
																											// the
																											// next
																											// complete
																											// token
																											// from
																											// this
																											// scanner.
								listOfWords.add(i++, twc);
								System.out.println(
										"hh:mm:ss foreign_word mother_tongue_word [or type \"back\" to finish]");
							} while (true);
							/** Objects are created and list is filled. */

							System.out.println(
									"Printing out the new list with the newly recorded couples from ArrayList to the screen");
							System.out.println(listOfWords.toString());

							System.out.println("\nWrite list into file and close file.\n");
							/** Write list into file. */
							bw.write(listOfWords.toString());
							bw.flush();
							bw.close();

							// br.close();

						} catch (IOException e) {
							e.printStackTrace();
						}

						break;

					case "exist":
						System.out.println("Open a file to read and write...\n");
						// use an existing list from file

						try {
							/**
							 * Here I have to list files in the containing
							 * folder to the user to be able to choose a file.
							 */
							// Listing files in folder
							File path = new File(".");
							String[] list = path.list();
							for (int i = 0; i < list.length; i++)
								System.out.println(list[i]);

							System.out.println(
									"Give me the file name what you want to continue to extend \"fileName.txt\": ");

							// Choosing file
							fileName = input.nextLine();
							File file = new File(fileName);

							System.out.println(
									"File has chosen. Reading lines from the file and filling the ArrayList\n");

							// Read input lines from the existing file and
							// create ArrayList from data
							// To be able to list by date, need to recognize
							// date format in the file

							Scanner scannerFile = new Scanner(file);
							int linesInFile = 0;

							while (scannerFile.hasNextLine()) {
								if (scannerFile.hasNext())
									scannerFile.next(); // skip "[" and "," at
														// the beginning of each
														// row

								if (scannerFile.hasNext()) {
									String token = scannerFile.next();

									Date date = df.parse(token);

									TimeWordCouple twc = new TimeWordCouple(date, scannerFile.next(),
											scannerFile.next());
									listOfWords.add(linesInFile++, twc);
								} else {
									break;
								}

							} // while
							scannerFile.close();
							/**
							 * Objects are created and list is filled. Now
							 * continue to write.
							 */

							System.out.println("Printing out the existing list from ArrayList to the screen");
							System.out.println(listOfWords.toString());

							BufferedWriter bw = new BufferedWriter(new FileWriter(file)); // This
																							// is
																							// for
																							// output.
																							// Have
																							// to
																							// be
																							// after
																							// the
																							// scanning
																							// period.

							System.out.println("Write words to input.");
							System.out.println("You can type your time and words now:");
							System.out.println("hh:mm:ss foreign_word mother_tongue_word [or type \"back\" to finish]");

							do {
								// Read a line from console and separate data
								firstToken = input.next();
								if (firstToken.matches("back")) {
									break;
								}

								Date date = df.parse(firstToken); // This is
																	// parsing
																	// to date
																	// type

								TimeWordCouple twc = new TimeWordCouple(date, input.next(), input.next());

								listOfWords.add(linesInFile++, twc);

								System.out.println(
										"hh:mm:ss foreign_word mother_tongue_word [or type \"back\" to finish]");

							} while (true);
							/** Objects and list is created. */

							System.out.println(
									"Printing out the existing list with the newly recorded couples from ArrayList to the screen");
							System.out.println(listOfWords.toString());

							System.out.println("\nWrite list into file and close file.\n");
							/** Write list into file. */
							bw.write(listOfWords.toString());
							bw.flush();
							bw.close();

						} catch (IOException e) {
							e.printStackTrace();
						}

						break;

					case "back":
						System.out.println("Back to Main menu. ");
						listFunction = "back";
						break;
					default:
						System.out.println("Command not recognized.");
						break;
					}
				} while (listFunction != "back");
				break;

			case "list":
				// This menu assume that a list is existing.
				// If a list not existing redirect to create new

				/**
				 * Firstly I have to read a list from file to ArrayList. I want
				 * to choose which file to read. After I have the ArrayList, I
				 * have to sort it as chosen from the options.
				 * 
				 */
				do {

					System.out.println("Type \"open\" to open a listfile or \"back\" to back to main menu]:");
					// use an existing list from file

					listFunction = input.nextLine();

					switch (listFunction) {
					case "open":

						try {
							/**
							 * Here I have to list files in the containing
							 * folder to the user to be able to choose a file.
							 */
							// Listing files in folder
							File path = new File(".");
							String[] list = path.list();
							for (int i = 0; i < list.length; i++)
								System.out.println(list[i]);

							System.out.println("Give me the file name what you want to list \"fileName.txt\": ");

							// Choosing file
							fileName = input.nextLine();
							File file = new File(fileName);

							System.out.println(
									"File has chosen. Reading lines from the file and filling the ArrayList\n");

							// Read input lines from the existing file and
							// create ArrayList from data
							// To be able to list by date, need to recognize
							// date format in the file

							Scanner scannerFile = new Scanner(file);
							int linesInFile = 0;

							while (scannerFile.hasNextLine()) {
								if (scannerFile.hasNext())
									scannerFile.next(); // skip "[" and "," at the beginning of each row
								
								if (scannerFile.hasNext()) {
									String token = scannerFile.next();

									Date date = df.parse(token);

									TimeWordCouple twc = new TimeWordCouple(date, scannerFile.next(),
											scannerFile.next());
									listOfWords.add(linesInFile++, twc);
								} else {
									break;
								}

							} // while
							scannerFile.close();
							/**
							 * Objects are created and list is filled. Now continue to sort.
							 */

							System.out.println("Printing out the existing list from ArrayList to the screen");
							System.out.println(listOfWords.toString());

						} catch (IOException e) {
							e.printStackTrace();
						}

						do {
							System.out.println(
									"How you want to sort the list: abc1 (foreign) or abc2 (mother tongue) or time or type \"back\" to open an other file");
							listFunction = input.nextLine();

							switch (listFunction) {
							case "abc1":
								System.out.println("Listing in abc1 (foreign) order...");
								/**Sort to abc1 (foreign) order and print it*/
								Collections.sort(listOfWords, (twc1, twc2) -> twc1.getForeignWord().compareTo( twc2.getForeignWord() ) );
								System.out.println(listOfWords.toString());
								break;
								
							case "abc2":
								System.out.println("Listing in abc2 (mother tongue) order...");
								Collections.sort(listOfWords, (twc1, twc2) -> twc1.getMotherTongue().compareTo( twc2.getMotherTongue() ) );
								System.out.println(listOfWords.toString());
								break;
								
							case "time":
								System.out.println("Listing in time order...");
								Collections.sort(listOfWords);	//Because TimeWordCouple implements Comparable<TimeWordCouple> and so compareTo();
								System.out.println(listOfWords.toString());
								break;
								
							case "back":
								System.out.println("Back to Main menu. ");
								listFunction = "back";
								break;
								
							default:
								System.out.println("Command not recognized.");
								break;
								
							}
						} while (listFunction != "back");
						break;

					case "back":
						System.out.println("Back to main menu. ");
						listFunction = "back";
						break;
					default:
						System.out.println("Command not recognized.");
						break;
					}

				} while (listFunction != "back");
				break;

			case "search":
				// This menu assume that a list is existing. Search is only
				// possible in a sorted list.
				// If a list not existing throw exception or redirect to create
				// new
				System.out.println("Searching in an existing list for a given word...\n");
				do {
					System.out.println("Choose from the list: abc or time or back\n");
					listFunction = input.nextLine();

					switch (listFunction) {
					case "abc":
						System.out.println("Searching in abc order...\n");
						// sort in abc order to search
						break;
					case "time":
						System.out.println("Searching in time order...\n");
						// sort in time order to search
						break;
					case "back":
						System.out.println("Back to Main menu. ");
						listFunction = "back";
						break;
					default:
						System.out.println("Command not recognized.");
						break;
					}
				} while (listFunction != "back");
				break;

			case "end":
				System.out.println("End of interaction.");
				text = "end";
				break;

			default:
				System.out.println("Command not recognized.");
				break;
			}// switch text

		} while (text != "end");

		input.close();

	}// static void main

}// Class
