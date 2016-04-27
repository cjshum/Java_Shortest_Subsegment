public class Main {
	public static void main(String args[]) throws Exception {
		Data data = new Data();
		ShortestSubsegmentFinder<String> ssf =
				new ShortestSubsegmentFinder<>(data.lowerCaseWords, data.wordsToMatch);
		ssf.printShortestSubsegment(data.originalWords);
	}
}