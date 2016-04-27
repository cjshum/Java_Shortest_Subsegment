import java.util.Map;

class ShortestSubsegmentFinder<Type> {
	// variables storing the data
	Type []content;
	// key:   item value
	// value: counter of how many times an item appeared in
	//        the current subsegment that is being probed
	Map<Type, Integer> itemsToMatch;
	
	// variables used for finding the shortest subsegment
	int currIndex;
	int numContent;
	int numItemsToMatch;
	int bestStartIndex;
	int bestEndIndex;
	int bestLength;
	int numItemsEncountered;
	int currStart;
	Type startItem;
	
	ShortestSubsegmentFinder(Type []content, Map<Type, Integer> itemsToMatch) {
		this.content      = content;
		this.itemsToMatch = itemsToMatch;
		findShortestSubsegment();
	}
	
	public void setContent(Type []content) {
		this.content = content;
	}
	
	private void findShortestSubsegment() {
		numContent          = content.length;
		numItemsToMatch     = itemsToMatch.size();
		bestStartIndex      = 0;
		bestEndIndex        = numContent - 1;
		bestLength          = bestEndIndex - bestStartIndex;
		numItemsEncountered = 0;
		currStart           = 0;
		startItem           = null;
		
		// loop until the first matching item is found
		currIndex = 0;
		for (; currIndex<numContent; currIndex++) {
			Type currItem = content[currIndex];
			if (itemsToMatch.containsKey(currItem)) {
				itemsToMatch.put(currItem, 1);
				startItem           = currItem;
				currStart           = currIndex;
				bestStartIndex      = currStart;
				numItemsEncountered += 1;
				currIndex++;
				break;
			}
		}
		
		for (; currIndex<numContent; currIndex++) {
			Type currItem          = content[currIndex];
			boolean matchedItem    = itemsToMatch.containsKey(currItem);
			int timesEncoutered    = (itemsToMatch.get(currItem)==null)
					 ? 0 : itemsToMatch.get(currItem);
			boolean firstEncounter = (timesEncoutered == 0);
			
			if (matchedItem && firstEncounter) {
				itemsToMatch.put(currItem, 1);
				numItemsEncountered += 1;
			}
			
			boolean foundBetterStart = currItem.equals(startItem);
			if (matchedItem && !firstEncounter && foundBetterStart)
				shortenSubsegment();
			
			if (matchedItem && !firstEncounter && !foundBetterStart)
				itemsToMatch.put(currItem, timesEncoutered+1);
			
			boolean foundAllItems = (numItemsEncountered == numItemsToMatch); 
			if (matchedItem && foundAllItems) {
				int newLength = currIndex - (currStart+1);
				if (newLength < bestLength) {
					bestLength       = newLength;
					bestStartIndex   = currStart;
					bestEndIndex     = currIndex;
				}
				
				// stop if found shortest possible subsegment
				if (newLength == numItemsToMatch) break;
			}
		}
	}
	
	private void shortenSubsegment() {
		int newIndex = currStart + 1;
		for (; newIndex<currIndex; newIndex++) {
			Type currItem       = content[newIndex];
			currStart           = newIndex;
			startItem           = currItem;
			boolean matchedItem = itemsToMatch.containsKey(currItem);
			int numEncItem      = (itemsToMatch.get(currItem)==null)
					? 0 : itemsToMatch.get(currItem);
			
			if (matchedItem && numEncItem==1)
				 break;
				
			if (matchedItem && numEncItem>1)
				itemsToMatch.put(currItem, numEncItem-1);
		}
	}
	
	/**
	 * Prints "NO SUBSEGMENT FOUND" if none is found
	 * Otherwise it prints the smallest subsegment from the content
	 */
	public void printShortestSubsegment() {
		if (numItemsEncountered < numItemsToMatch) {
			System.out.print("NO SUBSEGMENT FOUND");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		for (; bestStartIndex<bestEndIndex; bestStartIndex++) {
			sb.append(content[bestStartIndex] + " ");
		}
		sb.append(content[bestStartIndex] + " ");
		System.out.print(sb);
	}
}