import java.util.Map;

class ShortestSubsegmentFinder<Type> {
	Type []content;
	/**
	 * itemsToMatch - contains the items to be matching in the subsegment and their counter
	 * key:   item value
	 * value: counter of how many times an item appears in the current working subsegment
	 */
	Map<Type, Integer> itemsToMatch;
	
	// shared state variables used for finding the shortest subsegment
	int numContent;
	int numItemsToMatch;
	int bestStartIndex;
	int bestEndIndex;
	int bestLength;
	int currIteratingIndex;
	int currStartIndex;
	Type startItem;
	int numDiffItemsEnc;
	int timesEncoutered;
	
	ShortestSubsegmentFinder(Type []content, Map<Type, Integer> itemsToMatch) {
		this.content      = content;
		this.itemsToMatch = itemsToMatch;
		findShortestSubsegment();
	}
	
	public void setContent(Type []content) {
		this.content = content;
	}
	
	private void findShortestSubsegment() {
		initializeVariables();
		findFirstMatchingItem();
		
		for (; currIteratingIndex<numContent; currIteratingIndex++) {
			Type currItem          = content[currIteratingIndex];
			int timesEncoutered    = (itemsToMatch.get(currItem)==null)
					 ? -1 : itemsToMatch.get(currItem);
			
			boolean matchedItem      = itemsToMatch.containsKey(currItem);
			boolean firstEncounter   = (timesEncoutered == 0);
			boolean foundBetterStart = currItem.equals(startItem);
			
			if (matchedItem && firstEncounter) {
				itemsToMatch.put(currItem, 1);
				numDiffItemsEnc += 1;
			}
			
			else if (matchedItem && !firstEncounter && !foundBetterStart)
				itemsToMatch.put(currItem, timesEncoutered+1);
			
			else if (matchedItem && !firstEncounter && foundBetterStart)
				shortenSubsegment();
			
			boolean foundAllItems = (numDiffItemsEnc== numItemsToMatch); 
			if (matchedItem && foundAllItems) {
				chooseBestSubsegment();
				
				// stop if found shortest possible subsegment
				if (bestLength == numItemsToMatch) break;
			}
		}
	}
	
	private void initializeVariables() {
		numContent         = content.length;
		numItemsToMatch    = itemsToMatch.size();
		bestStartIndex     = 0;
		bestEndIndex       = numContent - 1;
		bestLength         = bestEndIndex - bestStartIndex;
		numDiffItemsEnc    = 0;
		currStartIndex     = 0;
		startItem          = null;
		currIteratingIndex = 0;
	}
	
	private void findFirstMatchingItem() {
		for (; currIteratingIndex<numContent; currIteratingIndex++) {
			Type currItem = content[currIteratingIndex];
			if (itemsToMatch.containsKey(currItem)) {
				itemsToMatch.put(currItem, 1);
				startItem           = currItem;
				currStartIndex      = currIteratingIndex;
				bestStartIndex      = currStartIndex;
				numDiffItemsEnc += 1;
				currIteratingIndex++;
				break;
			}
		}
	}
	
	private void chooseBestSubsegment() {
		int newLength = currIteratingIndex - (currStartIndex+1);
		if (newLength < bestLength) {
			bestLength       = newLength;
			bestStartIndex   = currStartIndex;
			bestEndIndex     = currIteratingIndex;
		}
	}
	
	private void shortenSubsegment() {
		int newIndex = currStartIndex + 1;
		for (; newIndex<currIteratingIndex; newIndex++) {
			Type currItem       = content[newIndex];
			currStartIndex      = newIndex;
			startItem           = currItem;
			boolean matchedItem = itemsToMatch.containsKey(currItem);
			int numEncItem      = (itemsToMatch.get(currItem)==null)
					? -1 : itemsToMatch.get(currItem);
				
			if (matchedItem && numEncItem>1)
				itemsToMatch.put(currItem, numEncItem-1);
			
			else if (matchedItem && numEncItem==1)
				 break;
		}
	}
	
	/**
	 * Prints "NO SUBSEGMENT FOUND" if none is found
	 * Otherwise it prints the smallest subsegment from the content
	 */
	public void printShortestSubsegment() {
		if (numDiffItemsEnc < numItemsToMatch) {
			System.out.print("NO SUBSEGMENT FOUND");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		int i = bestStartIndex;
		for (; i<bestEndIndex; i++) {
			sb.append(content[i] + " ");
		}
		sb.append(content[i] + " ");
		System.out.print(sb);
	}
}