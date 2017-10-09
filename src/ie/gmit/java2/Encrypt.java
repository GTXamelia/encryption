/**
 * Program Name: Encryption Project(GMIT)
 * Programmer Name: Cian Gannon
 * Student ID: G00337022
 * 
 * Description:
 * The Ecryption.java file encrypts all data from a .txt file chosen by the user
 * The encrypted data is then stored in a buffered reader along with any characters
 * that were unable to be encrypted.(Chatacter not in the porta cypher.)
 * The data from the buffered reader is then output to a new .txt file.
 * The new .txt file is called the same as the original with a (Encypted) Prefix
 * at the start of the file name.
 */
package ie.gmit.java2;

//Imports used in this .java file
import java.awt.TextArea;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Encrypt {// Start of Encrypt class
	
	public static void main(String fileNameParse,String key, TextArea parseReadableOnly, long timeStart, TextArea timeTaken, TextArea encryptedTextArea, TextArea notEncryptedTextArea, String encryptionType) throws IOException {//Start of main
		parseReadableOnly.append("Initialising  vairiables...");
		// Porta cypher used to encrypt data
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
		
		// Array list to hold data to be later output to a file
		List<Character> contents = new ArrayList<Character>();
		
		// Initialize BufferedReader, FileWriter and BufferedWriter
		BufferedReader br = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		//Variables 
		boolean check = false;
		char input;
		char convert;
		File file = new File("("+encryptionType+")"+fileNameParse+".txt");
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
		
		parseReadableOnly.append("Completed\n");
		
		//Runs file and if errors occur it will display to the user what has gone wrong and how to append it
		try{
			//Declares BufferedReader, FileWriter and BufferedWriter
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileNameParse+".txt")));
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			
			//Write out the file from Buffered Reader
			parseReadableOnly.append("Starting "+ encryptionType +"ion process...");
			check = true;
			
			//While BufferedReader is accepting data this code will run otherwise BufferedReader is -1 and this will stop
			while((line = br.read()) != -1){
				
				//All values are stores as intergers to sift through the unusable characters such as (;'.,/<>*^%)
				//This means if a number is not 65 or 122 or between those two numbers they are stored in the list array
				//If they number is between these two number or is these two numbers then they are turned into strings
				//which would then be used to find their position in the porta cypher
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
			parseReadableOnly.append("Completed\n");
		}catch (FileNotFoundException el){
			parseReadableOnly.append("File could not be found."
									+"\nPlease ensure you entered the correct file name."
									+"\nPlease ensure you didn't enter a reduant .txt fortmat at end of file name.\n\n");
			check = false;
		}catch (Exception e){
			parseReadableOnly.append("An unexpected Error has occured. Please try again.\n\n");
		}
		
		try {
			//save to this filename
			if (!file.exists()&&check!=false) {
				file.createNewFile();
				
			}
			
			//Loops threw the list array and outputs each character to the file
			for(int i=0;i<=(contents.size()-1);i++){
				inputLine = inputLine +""+ contents.get(i);
				bw.write(inputLine);
				inputLine="";
			}
			
		}catch(NullPointerException e){
			parseReadableOnly.append("File could not be written to as file name was incorrect.\n\n");
		}
		
		//Closes bufferedReader and bufferedWriter
		bw.close();
		br.close();
		
		//Calculates how long it took to encrypt file
		timeEnd = System.nanoTime() - timeStart;
		double seconds = ((double) timeEnd) / 1000000000.0;
		
		//This is used to display the time it took to  file
		timeTaken.setText(stats = stats+""+seconds+" Seconds");
		stats ="";
		
		//Displays to the user the characters encrypted
		encryptedTextArea.setText(stats = stats+""+encrypted);
		stats ="";
		
		//Displays to the user the characters that were unable to be encrypted
		notEncryptedTextArea.setText(stats = stats+""+notEncrypted);
		
		//Tell the user the program has ran without error
		parseReadableOnly.append("File was "+ encryptionType +"ed without any interuption."
								+"\nFile was saved to "+ file +"\n\n");	
				
		}//End of Main
}//End of Encrypt class