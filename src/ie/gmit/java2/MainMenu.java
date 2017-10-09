/**
 * Program Name: Encryption Project(GMIT)
 * Programmer Name: Cian Gannon
 * Student ID: G00337022
 * 
 * Description:
 * This is the user interface that allows the user to interact
 * with the code with ease. The user is given several options 
 * and methods to interact with the code. They can chose whether
 * to decipher or encrypt a file or URL.
 * 
 * The interface will also display statistics, such as the time it 
 * took to encrypt and decipher a file.
 */
package ie.gmit.java2;

// Imports used in the MainMenu.java file
import java.awt.EventQueue;
import java.awt.TextArea;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class MainMenu {

	private JFrame frame;
	private JTextField textFieldEncrypt;
	private JButton btnDecrypt;
	private JTextField textFieldKey;
	private JTextField textFieldKey2;
	private JTextField textFieldURL;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//This is the JFrame settings
				frame = new JFrame();//Sets the frame up
				frame.setResizable(false);//Disables ability to resize frame
				frame.setTitle("Encryption Project - G00337022");//Sets program header
				frame.setBounds(100, 100, 490, 500);// Sets JFrame size
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Allows user to exit the program
				frame.getContentPane().setLayout(null);//Sets layout to allow me to put anything where I want
				
				//This is the settings for textarea where all information is output to the user
				TextArea parseReadableOnly = new TextArea();
				parseReadableOnly.setBounds(10, 10, 464, 254);
				frame.getContentPane().add(parseReadableOnly);
				parseReadableOnly.setEditable(false);
				
				//This TextArea box is used to show how long it took to encrypt and decipher a file.
				TextArea timeTaken = new TextArea();
				timeTaken.setText("0");
				timeTaken.setBounds(10, 441, 150, 20);
				frame.getContentPane().add(timeTaken);
				
				//This TextArea show how many characters were able to be encrypted.
				TextArea encryptedTextArea = new TextArea();
				encryptedTextArea.setText("0");
				encryptedTextArea.setBounds(168, 441, 150, 20);
				frame.getContentPane().add(encryptedTextArea);
				
				//This TextArea shows how many characters were not able to be encrypted
				//(characters not in the porta cypher (,;[]';{}) )
				TextArea notEncryptedTextArea = new TextArea();
				notEncryptedTextArea.setText("0");
				notEncryptedTextArea.setBounds(324, 441, 150, 20);
				frame.getContentPane().add(notEncryptedTextArea);
				
				//This JLabel is used to show text above the textFieldKey input box
				JLabel lblNewLabel_2 = new JLabel("Encryption Key:");
				lblNewLabel_2.setBounds(10, 297, 120, 14);
				frame.getContentPane().add(lblNewLabel_2);
				
				//This JLabel is above the timeTaken TextArea input box
				JLabel lblTimeRecorded = new JLabel("Time Recorded:");
				lblTimeRecorded.setBounds(10, 423, 150, 14);
				frame.getContentPane().add(lblTimeRecorded);
				
				//This  JLabel appears above the textFieldKey
				JLabel lblFileName = new JLabel("File Name:");
				lblFileName.setBounds(140, 297, 204, 14);
				frame.getContentPane().add(lblFileName);
				
				//This JLabel appears above the encryptedTextArea TextArea
				JLabel lblLettersEncrypted = new JLabel("Char(s) En/Decrypted:");
				lblLettersEncrypted.setBounds(168, 423, 150, 14);
				frame.getContentPane().add(lblLettersEncrypted);
				
				//This JLabel appears above the notEncryptedTextArea Text Area
				JLabel lblNewLabel = new JLabel("Char(s)Not En/Decrypted:");
				lblNewLabel.setBounds(324, 423, 150, 14);
				frame.getContentPane().add(lblNewLabel);
				
				//This JLabel appears above the textFieldKey2 JTextField
				JLabel lblDecryptionKey = new JLabel("Decryption Key:");
				lblDecryptionKey.setBounds(354, 297, 120, 14);
				frame.getContentPane().add(lblDecryptionKey);
				
				//This JLabel appears above the textFieldURL JTextField
				JLabel lblUrlName = new JLabel("URL Link:");
				lblUrlName.setBounds(140, 333, 204, 14);
				frame.getContentPane().add(lblUrlName);
				
				//This JTextField is used to accept a decryption key from the user
				textFieldKey2 = new JTextField();
				textFieldKey2.setColumns(10);
				textFieldKey2.setBounds(354, 312, 120, 20);
				frame.getContentPane().add(textFieldKey2);
				
				//This JTextField is used to accept a URL from the user
				textFieldURL = new JTextField();
				textFieldURL.setBounds(140, 348, 204, 20);
				frame.getContentPane().add(textFieldURL);
				textFieldURL.setColumns(10);
				
				//This JTextField is used to accept an encryption key from the user
				textFieldKey = new JTextField();
				textFieldKey.setBounds(10, 312, 120, 20);
				frame.getContentPane().add(textFieldKey);
				textFieldKey.setColumns(10);
				
				//This JTextField is used to accept a file name from the user
				textFieldEncrypt = new JTextField();
				textFieldEncrypt.setBounds(140, 312, 204, 20);
				frame.getContentPane().add(textFieldEncrypt);
				textFieldEncrypt.setColumns(10);
				
				parseReadableOnly.append("Welcome to my encyption project!\n"
										+"As part of my coursework for Data Structures & Algorithms.\n"
										+"Any questions that you may have, may have been ansered in read me.\n\n");
				
				//This JButton is used to encrypt files and URLs that the user enters
				JButton btnEncrypt = new JButton("Encrypt");
				btnEncrypt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//Declares all data in textfields that was entered by the user
						String fileNameParse = textFieldEncrypt.getText();
						String key = textFieldKey.getText();
						String URLLink = textFieldURL.getText();
						String EncryptionType = "Encrypt";
						
						//Starts timer once button is pressed
						long timeStart;
						timeStart = System.nanoTime();
						
						//Makes key all upper case
						key = key.toUpperCase();
						
						//If both data fields are not empty the file encryption will run
						//The file encryption will also bring with it all needed variables.
						//Else if both are empty it will check if URL encryption is empty and
						//if URL encryption is not empty it will URL encryption.
						//If URL encryption is true then it check to see if user has a valid
						//URL by checking if it contains "https://www.". If this is true the 
						//Program to encrypt a URL will begin.
						//Otherwise the user will be informed their URL format is incorrect
						//If neither of the over arching if statements are true then the user
						//will be informed that no data fields are occupied and to fill in 
						//relevant information
						if(!(fileNameParse.isEmpty())&&!(key.isEmpty())){
							try {
								Encrypt.main(fileNameParse,key,parseReadableOnly,timeStart,timeTaken,encryptedTextArea,notEncryptedTextArea,EncryptionType);
							} catch (Exception e) {
								
							}
						}else if(!(URLLink.isEmpty())&&!(key.isEmpty())){
							if(URLLink.contains("https://www.")){
								try {
									EncryptURL.main(URLLink,key,parseReadableOnly,timeStart,timeTaken,encryptedTextArea,notEncryptedTextArea);
								} catch (Exception e) {
									
								}
							}else{
								parseReadableOnly.append("Incorrect URL type.\n"
														+"Please ensure the URL contains \"https://www.\" \n"
														+"If the URL doesn't contain \"https://www.\" a connection cannot be established.\n\n");
							}
							
						}else{
							parseReadableOnly.append("You have left data blank, please check all valid field and try again.\n\n");
						}
					}
				});
				btnEncrypt.setBounds(10, 347, 120, 23);
				frame.getContentPane().add(btnEncrypt);
				
				//This JButton is used to decipher files
				btnDecrypt = new JButton("Decrypt");
				btnDecrypt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//Declares all data in textfields that was entered by the user
						String fileNameParse = textFieldEncrypt.getText();
						String key = textFieldKey2.getText();
						String EncryptionType = "Decrypt";
						
						//Starts timer once button is pressed
						long timeStart;
						timeStart = System.nanoTime();
						
						//Makes key all uppercase
						key = key.toUpperCase();
						
						//If statement runs if both data fields that are needed are empty
						//And then alert the user to this problem telling them to enter valid information.
						//Else the file will start to run bringing needed vairialbes with it.
						if(fileNameParse.isEmpty()||key.isEmpty()){
							parseReadableOnly.append("You have left the file name blank.\n Or have left the key blank.\n\n");
						}else{
							parseReadableOnly.append("Trying to find file: \""+ fileNameParse +".txt\" \n\n");
							try {
								Encrypt.main(fileNameParse,key,parseReadableOnly,timeStart,timeTaken,encryptedTextArea,notEncryptedTextArea,EncryptionType);
							} catch (IOException e) {
								
							}
						}
					}
				});
				btnDecrypt.setBounds(354, 347, 120, 23);
				frame.getContentPane().add(btnDecrypt);
				
				//This button is used to clear text in parseReadableOnly
				//Click button and activates one line of code that sets all text in parseReadableOnly
				//to "" which makes it look empty and allows user to complete more tests without old ones present.
				JButton btnClearText = new JButton("Clear Text");
				btnClearText.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						parseReadableOnly.setText("");
					}
				});
				btnClearText.setBounds(354, 270, 120, 23);
				frame.getContentPane().add(btnClearText);
	}
}
