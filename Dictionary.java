import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

public class Dictionary{
	
	public void encoder(String fileIn){
		String fileName = fileIn;
		List<String> dictionary = new ArrayList<String>();
		List<Integer> encodedList = new ArrayList<>();
		String text = "";
		int singleEntries = 0;
		
		try {
			for (String line : Files.readAllLines(Paths.get(fileName))) {
				for (String part : line.split("\\s+")) {
					String i = String.valueOf(part);
					text = text + " " + i;
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		text = text.substring(1);
		String convert = "";
		
		//For single letter entries
		//If there isn't enough text to make a 255 entry dictionary
		if(text.length() < 255){
			for(int i = 0; i < text.length(); i++){
				convert = Character.toString(text.charAt(i));
				if(dictionary.contains(convert) == false){
					dictionary.add(convert);
					singleEntries++;
				}
			}
		}
		
		//For single letter entries
		//For dictionary when it can be 255
		else{
			for(int i = 0; i < text.length(); i++){
				convert = Character.toString(text.charAt(i));
				if(dictionary.contains(convert) == false){
					dictionary.add(convert);
					singleEntries++;
				}
			}
		}
		
		String checker = "";
		
		//For non-single letter cases
		for(int i = 0; i < text.length(); i++){
			checker = checker + Character.toString(text.charAt(i));
			if(dictionary.size() > 255){
				break;
				}
			
			if(dictionary.contains(checker) == false){
				dictionary.add(checker);
				checker = Character.toString(text.charAt(i));
			}
		}
		
		//Encoding - This is incorrect, only checks for double and single letters in dictionary
		for(int i = 0; i < text.length(); i++){
			convert = Character.toString(text.charAt(i));
			
			if(dictionary.contains(convert) == true){
				if(i + 1 >= text.length()){
					convert = Character.toString(text.charAt(i));
					encodedList.add(dictionary.indexOf(convert));
					break;
				}
					
				convert = Character.toString(text.charAt(i)) + Character.toString(text.charAt(i + 1));
				
				if(dictionary.contains(convert) == true){
					encodedList.add(dictionary.indexOf(convert)); 
					i++;
				}
				else{
					convert = Character.toString(text.charAt(i));
					encodedList.add(dictionary.indexOf(convert));
				}
			}
		}
		
		System.out.println("Index	Entry");
		System.out.println("- - - - - - -");
		for(int i = 0; i < dictionary.size(); i++){
			System.out.println(i + "	" + dictionary.get(i));
		}
		
		float dataBefore = 8 * text.length();	
		float dataAfter = 8 * encodedList.size();
		float compressionRatio = dataBefore / dataAfter;
		
		decoder(dictionary, encodedList, singleEntries);
		System.out.println();

		System.out.println("Compression Ratio: " + compressionRatio);
	}
	
	public void decoder(List<String> dictionary, List<Integer> encodedList, int singleEntries){
		String decoded = "";
		String encoded = "";

		System.out.println();
		//Putting encoded list into string
		//Decoding from the encoded list
		for(int i = 0; i < encodedList.size(); i++){
			encoded += encodedList.get(i) + " ";
			decoded += dictionary.get(encodedList.get(i));
		}
		
		System.out.println("Encoded Text:	" + encoded);
		System.out.println();
		System.out.println("Decoded Text:	" + decoded);
		System.out.println();
	}
}
