package twcmain1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import timewordcouple1.*;
import twccomparartotforeign.TWCComparatorForeign;
import twccomparatordate.TWCComparatorDate;
import twccomparatormothertongue.TWCComparatorMotherTongue;

public class TwcMain {

	public static void main(String[] args) throws IOException, ParseException {

		/** */

		String text = null;
		String listFunction = null;
		String fileName = null;
		String firstToken = null;

		Scanner input = new Scanner(System.in);

		// This will recognize date format
		DateFormat df = new SimpleDateFormat("HH:mm:ss");

		// Menu
		do {

			System.out.println("Please enter a command: \n\"input\" or \n\"list\" or \n\"search\" or \n\"end\"");
			text = input.next();

			switch (text) {
			case "input":

				do {
					System.out.println(
							"Choose from the list: \n\"new\" for a new list or \n\"exist\" to continue an existing list or \n\"back\" to go back to main menu.");
					
					//input.nextLine(); would read empty String
					listFunction = input.next();

					switch (listFunction) {
					case "new":
						List<TimeWordCouple> listOfWords = new ArrayList<TimeWordCouple>();

						System.out.println("Create a new file and fill the ArrayList...\n");

						try {

							System.out.println("Give me the file name what you want to create \"fileName.txt\": ");
							// Creating a file with the given file name, this
							fileName = input.next();
							File file = new File(fileName);
							file.createNewFile();

							System.out.println("Write words to input.");
							System.out.println("You can type your time and words now:");
							System.out.println("hh:mm:ss foreign_word mother_tongue_word [or type \"back\" to finish]");

							// This is for output
							BufferedWriter bw = new BufferedWriter(new FileWriter(file));

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

								Date date = df.parse(firstToken);

								TimeWordCouple twc = new TimeWordCouple(date, input.next(), input.next());
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
						List<TimeWordCouple> listOfWords2 = new ArrayList<TimeWordCouple>();

						System.out.println("Open a file to read and write...\n");
						// use an existing list from file

						try {
							listingFilesInMainFolder();

							System.out.println(
									"Give me the file name what you want to continue to extend \"fileName.txt\": ");

							// Choosing file
							fileName = input.next();
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
									// skip "[" and "," at the beginning of each
									// row
									scannerFile.next();

								if (scannerFile.hasNext()) {
									String token = scannerFile.next();

									Date date = df.parse(token);

									TimeWordCouple twc = new TimeWordCouple(date, scannerFile.next(), scannerFile.next());
									listOfWords2.add(linesInFile++, twc);
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
							System.out.println(listOfWords2.toString());

							// This have to be placed after scanning period
							BufferedWriter bw = new BufferedWriter(new FileWriter(file));

							System.out.println("Write words to input.");
							System.out.println("You can type your time and words now:");
							System.out.println("hh:mm:ss foreign_word mother_tongue_word [or type \"back\" to finish]");

							do {
								// Read a line from console and separate data
								firstToken = input.next();
								if (firstToken.matches("back")) {
									break;
								}

								Date date = df.parse(firstToken);

								TimeWordCouple twc = new TimeWordCouple(date, input.next(), input.next());
								listOfWords2.add(linesInFile++, twc);

								System.out.println("hh:mm:ss foreign_word mother_tongue_word [or type \"back\" to finish]");

							} while (true);
							/** Objects and list is created. */

							System.out.println(
									"Printing out the existing list with the newly recorded couples from ArrayList to the screen");
							System.out.println(listOfWords2.toString());

							System.out.println("\nWrite list into file and close file.\n");
							/** Write list into file. */
							bw.write(listOfWords2.toString());
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

					System.out.println("Type \"open\" [to open a listfile] or \n\"back\" [to back to main menu]");
					// use an existing list from file

					listFunction = input.next();

					switch (listFunction) {
					case "open":
						List<TimeWordCouple> listOfWords3 = new ArrayList<TimeWordCouple>();

						try {
							listingFilesInMainFolder();

							System.out.println("Give me the file name what you want to list \"fileName.txt\": ");

							// Choosing file
							fileName = input.next();
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
							// skip "[" and "," at the beginning of each row
									scannerFile.next(); 

								if (scannerFile.hasNext()) {
									String token = scannerFile.next();

									Date date = df.parse(token);

									TimeWordCouple twc = new TimeWordCouple(date, scannerFile.next(), scannerFile.next());
									listOfWords3.add(linesInFile++, twc);
								} else {
									break;
								}

							} // while
							scannerFile.close();
							/**
							 * Objects are created and list is filled. Now
							 * continue to sort.
							 */

							System.out.println("Printing out the existing list from ArrayList to the screen");
							System.out.println(listOfWords3.toString());

						} catch (IOException e) {
							e.printStackTrace();
						}

						do {
							System.out.println(
									"How you want to sort the list: \nabc1 (foreign) or \nabc2 (mother tongue) or \ntime or \nback [to open an other file]");
							listFunction = input.next();

							switch (listFunction) {
							case "abc1":
								System.out.println("Listing in abc1 (foreign) order...");
								/** Sort to abc1 (foreign) order and print it */
								Collections.sort(listOfWords3, (twc1, twc2) -> twc1.getForeignWord().compareTo(twc2.getForeignWord()));
								System.out.println(listOfWords3.toString());
								
								System.out.println("Do you want to save in a file? \"yes\" or \"no\"");
								if ( input.next().matches("yes") ) {
									
									System.out.println("Type in the filename: ");

								// Creating a file with the given file name, this
									fileName = input.next();
									File file = new File(fileName);
									file.createNewFile();
									// This is for output
									BufferedWriter bw = new BufferedWriter(new FileWriter(file));
								
									System.out.println("\nWrite list into file and close file.\n");
									bw.write(listOfWords3.toString());
									bw.flush();
									bw.close();
								}								
								break;


							case "abc2":
								System.out.println("Listing in abc2 (mother tongue) order...");
								Collections.sort(listOfWords3, (twc1, twc2) -> twc1.getMotherTongue().compareTo(twc2.getMotherTongue()));
								System.out.println(listOfWords3.toString());
								
								System.out.println("Do you want to save in a file? \"yes\" or \"no\"");
								if ( input.next().matches("yes") ) {
									
									System.out.println("Type in the filename: ");

								// Creating a file with the given file name, this
									fileName = input.next();
									File file = new File(fileName);
									file.createNewFile();
									// This is for output
									BufferedWriter bw = new BufferedWriter(new FileWriter(file));
								
									System.out.println("\nWrite list into file and close file.\n");
									bw.write(listOfWords3.toString());
									bw.flush();
									bw.close();
								} 								
								break;

								
							case "time":
								System.out.println("Listing in time order...");
								// Because TimeWordCouple implements
								// Comparable<TimeWordCouple> and so
								// compareTo();
								// Collections.sort(listOfWords);
								Collections.sort(listOfWords3, (twc1, twc2) -> twc1.getDate().compareTo(twc2.getDate()));
								System.out.println(listOfWords3.toString());

								System.out.println("Do you want to save in a file? \"yes\" or \"no\"");
								if ( input.next().matches("yes") ) {
									
									System.out.println("Type in the filename: ");

								// Creating a file with the given file name, this
									fileName = input.next();
									File file = new File(fileName);
									file.createNewFile();
									// This is for output
									BufferedWriter bw = new BufferedWriter(new FileWriter(file));
								
									System.out.println("\nWrite list into file and close file.\n");
									bw.write(listOfWords3.toString());
									bw.flush();
									bw.close();
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
				/**
				 * This menu assume that a list is existing. Search is only
				 * possible in a sorted list. If a list not existing throw
				 * exception or redirect to create new
				 */
				System.out.println("Searching in existing lists for a given word...\n");
				do {
					// open a file or back

					System.out.println("Choose from the list:");
					System.out.println("\"abc\" [to search for a word in files, it can be foreign or mother tongue word] or");
					System.out.println("\"time\" [to search for a time in a specific file] or");
					System.out.println("\"back\" [to back main menu]");
					listFunction = input.next();

					switch (listFunction) {
					case "abc":
						System.out.println("Searching in abc order...\n");
						System.out.print("Give me the file name or file names to search in");
						System.out.println(" \n\"fileName1.txt\" \"fileName2.txt\" ... or \nback [to back main menu]:");

						/**
						 * Here I have to list files in the containing folder to
						 * the user to be able to choose a file.
						 */

						listingFilesInMainFolder();

						// Choosing file(s) or back
						String inputFileNames = null;
						input.nextLine(); // This is needed for some reason. Not clear for why.
						inputFileNames = input.nextLine(); // put the whole row
						
						System.out.println("The input file names: " + inputFileNames);
						Scanner inputStringScanner = new Scanner(inputFileNames); //.useDelimiter("\\s*")
						// \s Any Whitespace, * Zero or more repetitions

						System.out.println("Give me the search word:");
						String searchWord = input.next();
						System.out.println("Your search word is: " + searchWord + "\n");

						try {
							if (inputFileNames.matches("back")) {
								System.out.println("Back to main menu.");
								break;
							} else {
								do {
									ArrayList<TimeWordCouple> listOfWordsTemporary = new ArrayList<TimeWordCouple>();
									int linesInFile = 0; // removed here not to sum files
									fileName = inputStringScanner.next();
									//This could be sorted out:
									System.out.println("inputString FileName: " + fileName);

									File file = new File(fileName);
									/**
									 * Read input lines from the existing files
									 * and create ArrayList from data. To be
									 * able to list by date, need to recognize
									 * date format in the file
									 */

									Scanner scannerFile = new Scanner(file);

									while (scannerFile.hasNextLine()) {
										if (scannerFile.hasNext())
											// skip "[" and "," at the beginning
											// of each row
											scannerFile.next();
										if (scannerFile.hasNext()) {
											String token = scannerFile.next();

											Date date = df.parse(token);

											TimeWordCouple twc = new TimeWordCouple(date, scannerFile.next(), scannerFile.next());
											listOfWordsTemporary.add(linesInFile++, twc);
										} else {
											break;
										}
									} // while scanner hasNextLine
									scannerFile.close();
									
									//This could be sorted out:
									//List the file content to see the words in
									System.out.println("Content of listofwordtemporary: \n");
									System.out.println(listOfWordsTemporary.toString());
									
									//System.out.println("List sorted in abc1 (foreign) order to search in foreign words\n");
									Collections.sort(listOfWordsTemporary, (twc1, twc2) -> twc1.getForeignWord().compareTo(twc2.getForeignWord()));
									//System.out.println(listOfWords.toString());

									int index = Collections.binarySearch(listOfWordsTemporary, new TimeWordCouple(null, searchWord, null), new TWCComparatorForeign());

									if (index >= 0)
										System.out.println("Found in foreign words list in: " + fileName + " " + listOfWordsTemporary.get(index));
									else
										System.out.println("There is no such element in foreign words list in \"" + fileName + "\".\n");
									
									//System.out.println("Listing in abc2 (mother tongue) order to search in mother tongue words.\n");
									Collections.sort(listOfWordsTemporary, (twc1, twc2) -> twc1.getMotherTongue().compareTo(twc2.getMotherTongue()));
									//System.out.println(listOfWords.toString());
									
									index = Collections.binarySearch(listOfWordsTemporary, new TimeWordCouple(null, null, searchWord), new TWCComparatorMotherTongue());

									if (index >= 0)
										System.out.println("Found in mother tongue words list in: " + fileName + " " + listOfWordsTemporary.get(index));
									else
										System.out.println("There is no such element in mother tongue words list in \"" + fileName + "\".\n");
									
									System.out.println("Searching in the next file...");
								} while (inputStringScanner.hasNext());
							} // else
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;

					case "time":
						List<TimeWordCouple> listOfWords4 = new ArrayList<TimeWordCouple>();

						System.out.println("Searching in time order...\n");
						System.out.print("Give me the file name to search in");
						System.out.println(" \"fileName1.txt\" or \nback [to back main menu]:");
						
						/**
						 * Here I have to list files in the containing folder to
						 * the user to be able to choose a file.
						 */

						// Listing files in main folder
						listingFilesInMainFolder();

						// Choosing file(s) or back
						String inputFileName = input.next();
						Scanner inputStringScanner1 = new Scanner(inputFileName);

						try {

							int linesInFile = 0;

							if (inputFileName.matches("back")) {
								System.out.println("Back to main menu.");
								break;
							} else {
								fileName = inputStringScanner1.next();
								File file = new File(fileName);
								/**
								 * Read input lines from the existing file
								 * and create ArrayList from data. To be
								 * able to list by date, need to recognize
								 * date format in the file
								 */

								Scanner scannerFile = new Scanner(file);

								while (scannerFile.hasNextLine()) {
									if (scannerFile.hasNext())
										// skip "[" and "," at the beginning
										// of each row
										scannerFile.next();
									if (scannerFile.hasNext()) {
										String token = scannerFile.next();

										Date date = df.parse(token);

										TimeWordCouple twc = new TimeWordCouple(date, scannerFile.next(), scannerFile.next());
										listOfWords4.add(linesInFile++, twc);
									} else {
										break;
									}
								} // while scanner hasNextLine
								scannerFile.close();
								/**
								 * Objects are created and list is filled
								 * with file. Now continue to sort and
								 * search in file.
								 */
								//List the file content to see the words in
								System.out.println(listOfWords4.toString());

								System.out.println("Give me the search time:");
								Date searchTime;

								//Here not possible to use input.nextLine(); that would read empty line
								searchTime = df.parse( input.next() );

								System.out.println("Your search Time is: " + TimeWordCouple.getDateTime(searchTime) );
								
								System.out.println("List sorted in time order to search ");
								Collections.sort(listOfWords4, (twc1, twc2) -> twc1.getDate().compareTo(twc2.getDate()));
								//System.out.println(listOfWords.toString());

								int index = Collections.binarySearch(listOfWords4, new TimeWordCouple(searchTime, null , null), new TWCComparatorDate());

								if (index >= 0)
									System.out.println("Found in: " + fileName + " " + listOfWords4.get(index));
								else
									System.out.println("There is no such element in " + fileName + ".");

							} // else

						} catch (IOException e) {
							e.printStackTrace();
						}
						
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

	//*List only .txt files in the project directory*/
	public static void listingFilesInMainFolder() {
		File dir = new File(".");
		String[] list = dir.list(new FilenameFilter() { 
			public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".txt");
			}});
		
		for (int i = 0; i < list.length; i++)
			System.out.println(list[i]);
	}

}// Class
