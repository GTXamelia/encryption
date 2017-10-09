/**
 * Program Name: File Parser
 * Programmer Name: Cian Gannon
 * Student ID: G00337022
 * 
 * Description:
 * This section of code reader a website character by character and the encrypts
 * the character and stores in in a file named after the webpage.
 * If a character is unable to be encrypted (character not in porta cypher)
 * the character will be put in hte file straight away skipping the encryption
 * process therefore saving a little bit of time and stopping the program from
 * crashing from being unable to locate ([];'.,/"£^*&) e.g in the porta cypher
 * 
 * N.B.
 * Some websites store their css file on a different server and link to that
 * file in the HTML others link to a file near the HTML code on a local server
 * These website will show only HTML code as the CSS file they are looking for 
 * doesn't exist as it is not downloaded
 * 
 * Also websites can't be decypted using a link (websites arent encypted)
 * So it downloads the website to a file named after the website and then the user
 * can use file decyption to decypt the website.
 * 
 * If you realll need to decypt a website using this code will do so also it will 
 * just tell the user it is encrypting a website rather then decrypting one.
 * 
 * TL;DR:
 * CSS elements may be missing
 * Website will show it only being encrypted but can still be decrypted using this.
 */
package ie.gmit.java2;

//All the imports that are used for this .java file
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EncryptURL {
	
	public static void main(String URLLink, String key, TextArea parseReadableOnly, long timeStart, TextArea timeTaken, TextArea encryptedTextArea, TextArea notEncryptedTextArea) throws IOException {
		parseReadableOnly.append("Initialising  vairiables...");
		//Porta cypher used to encrypt data
		final String[][] tableau = {
				{"KEYS", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"},
				{"AB", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"},
				{"CD", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "N", "M", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"},
				{"EF", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "N", "O", "L", "M", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"},
				{"GH", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "N", "O", "P", "K", "L", "M", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"},
				{"IJ", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "N", "O", "P", "Q", "J", "K", "L", "M", "A", "B", "C", "D", "E", "F", "G", "H", "I"},
				{"KL", "S", "T", "U", "V", "W", "X", "Y", "Z", "N", "O", "P", "Q", "R", "I", "J", "K", "L", "M", "A", "B", "C", "D", "E", "F", "G", "H"},
				{"MN", "T", "U", "V", "W", "X", "Y", "Z", "N", "O", "P", "Q", "R", "S", "H", "I", "J", "K", "L", "M", "A", "B", "C", "D", "E", "F", "G"},
				{"OP", "U", "V", "W", "X", "Y", "Z", "N", "O", "P", "Q", "R", "S", "T", "G", "H", "I", "J", "K", "L", "M", "A", "B", "C", "D", "E", "F"},
				{"QR", "V", "W", "X", "Y", "Z", "N", "O", "P", "Q", "R", "S", "T", "U", "F", "G", "H", "I", "J", "K", "L", "M", "A", "B", "C", "D", "E"},
				{"ST", "W", "X", "Y", "Z", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "E", "F", "G", "H", "I", "J", "K", "L", "M", "A", "B", "C", "D"},
				{"UV", "X", "Y", "Z", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "A", "B", "C"},
				{"WX", "Y", "Z", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "A", "B"},
				{"YZ", "Z", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "A"}
			};
		
		//Array list to hold data to be later output to a file
		List<Character> contents = new ArrayList<Character>();
		
		// Initialize BufferedReader, FileWriter and BufferedWriter
		BufferedReader br = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		//Variables
		char input;
		char convert;
		int line;
		int count = 0;
		int row = 0;
		int column = 0;
		int encrypted = 0;
		int notEncrypted = 0;
		long timeEnd;
		String c;
		String keyfinder = "";
		String inputLine = "";
		String stats ="";
		
		//Creates a refined URL without any of the attachments
		//E.G. https://www.google.ie/ becomes google
		//The appendage are removed from the URL so a file
		//can be saved as (Encrypted)google.txt for simple
		//storage and sorting of encrypted files
		String refined = URLLink;
		if(refined.contains("https://www.")){
			refined = refined.replaceAll("https://www.", "");
			refined = refined.split("\\.", 2)[0];
		}else if(refined.contains("https://")){
			refined = refined.replaceAll("https://", "");
			refined = refined.split("\\.", 2)[0];
		}
		
		//Sets up file name for data to be stored to
		File file = new File("(Encrypted)"+refined+".txt");
		parseReadableOnly.append("Completed\n");
		
		//Runs file and if errors occur it will display to the user what has gone wrong and how to append it
		try{
			parseReadableOnly.append("Connecting to website...");
			//Establishes connection to website
			URL url = new URL(URLLink);
			
			//All these are used to read and write to a file
		    br = new BufferedReader(new InputStreamReader(url.openStream()));
		    fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			parseReadableOnly.append("Completed\n");
			
			parseReadableOnly.append("Starting encryption process...");
			
			//All values are stores as intergers to sift through the unusable characters such as (;'.,/<>*^%)
			//This means if a number is not 65 or 122 or between those two numbers they are stored in the list array
			//If they number is between these two number or is these two numbers then they are turned into strings
			//which would then be used to find their position in the porta cypher
			while((line = br.read()) != -1){
				
				//All values are stores as intergers to sift through the unusable characters such as (;'.,/<>*^%)
				//This means if a number is not 65 or 122 or between those two numbers they are stored in the list array
				//If they number is between these two number or is these two numbers then they are turned into strings
				//which would then be used to find their position in the porta cypher
				//LD;DR Number from 65 to 122 represent the integer values of letters which can be encrypted
				if(line>=65 && line<=122){
				input = (char) line;
				input = Character.toUpperCase(input);
				keyfinder = keyfinder +""+  key.charAt(count);
				
					//Loops through the top row starting at "A" to find the row number
					for(int i=1;i<=26;i++){
						c = String.valueOf(input);
						if(tableau[0][i].contains(c)){
							row = i;
							i=50;
						}
					}
					
					//Loops through the left column starting at "AB" to find the key location
					for(int i=1;i<=13;i++){
						if(tableau[i][0].contains(keyfinder)){
							column = i;
							keyfinder = "";
							i=13;
						}
					}
					//With both locations found the encrypted character has been found and selected
					
					//With each while loop the encryption key goes to the next char in the key
					//If the counter is equal to the key length then it resets and the counter starts anew
					if(count >= (key.length())-1){
						count=0;
					}else{
						count++;
					}
					
					//This adds the encrypted character to the list array
					//It then counts the character that was encrypted to show
					//the user later on how many characters were encrypted 
					convert = tableau[column][row].charAt(0);
					contents.addAll(Arrays.asList(convert));
					encrypted++;
					
				//If the number is not from 65 to 122 then this codes runs
				//This code simply turns the number to a char and then 
				//adds that character to the list array
				}else{
					input = (char) line;
					input = Character.toUpperCase(input);
					contents.addAll(Arrays.asList(input));
					notEncrypted++;
				}
			}
			//closes BufferedReader
			br.close();
			
			parseReadableOnly.append("Completed\n");
			
		//If a FileNotFoundException error occurs it is caught and a message is output to the
		//user telling them what may have gone wrong.
		//This also stops the program from crashing so it can continue.
		}catch (FileNotFoundException el){
			parseReadableOnly.append("File could not be found.\n"
									+"\nPlease ensure you entered the correct filen ame."
									+"\nPlease ensure you didn't enter a reduant .txt fortmat at end of file name.");
		//If any other error that couldn't be predicted occurs a message will display to the
		//user and tell them what happened.
		}catch (Exception e){
			parseReadableOnly.append("An unexpected Error has occured. Please try again.\n\n");
		}
		
		//This block of code first checks if the file where the encrypted data will be
		//stored exists and if it doesn't a file will be created.
		//It then character by character will store all information in the file.
		try {
			//save to this filename
			if (!file.exists()) {
				file.createNewFile();
				
			}
			
			//Loops threw the list array and outputs each character to the file
			for(int i=0;i<=(contents.size()-1);i++){
				inputLine = inputLine +""+ contents.get(i);
				bw.write(inputLine);
				inputLine="";
			}
			
		//If the file cannot be written to or doesn't exist the user will be informed
		}catch(NullPointerException e){
			parseReadableOnly.append("File could not be written to as file name was incorrect.\n");
		}
		
		//Closes BufferedWriter
		bw.close();
		
		//Calculates time taken to run code
		timeEnd = System.nanoTime() - timeStart;
		double seconds = ((double) timeEnd) / 1000000000.0;
		
		//Displays the times taken to the user
		timeTaken.setText(stats = stats+""+seconds+" Seconds");
		stats = "";
		
		//Displays the number of encrypted character to the user
		encryptedTextArea.setText(stats = stats+""+encrypted);
		stats = "";
		
		//Displays the number of characters that could not be encrypted to the user
		notEncryptedTextArea.setText(stats = stats+""+notEncrypted);
		
		//Tells the user the code has ran without interruption
		parseReadableOnly.append("File was encrypted without any interuption."
								+"\nFile was saved to "+file+"\n\n");	

	}//End of "main"
}//End of class "DownloadURL


