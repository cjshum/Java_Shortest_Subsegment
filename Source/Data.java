import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

class Data {
	ConcurrentHashMap<String, Integer> wordsToMatch;
	String originalWords[];
	String lowerCaseWords[];
	int numMatchingWords;
	
	Data() {
		Scanner standardInput = new Scanner(System.in);
		
		String paragraph = standardInput.nextLine()
				.replaceAll("[^a-zA-Z\\s]", "");
		originalWords  = paragraph.split(" ");
		lowerCaseWords = paragraph.toLowerCase().split(" ");
		
		numMatchingWords = standardInput.nextInt();
		standardInput.nextLine();
		
		wordsToMatch = new ConcurrentHashMap<>();
		for (int i=0; i<numMatchingWords; ++i) {
			String currWord = standardInput.nextLine().toLowerCase();
			wordsToMatch.put(currWord, 0);
		}
		numMatchingWords = wordsToMatch.size();
		
		standardInput.close();
	}
}
