package poker;


import java.util.Arrays;
//Hand class evaluates hands, used arrays here instead of list for simplicity
public class Hand {
	private Card[] availableCards = new Card[7];

	private final static int ONE = 1;
	private final static int TWO = 2;
	private final static int THREE = 3;
	private final static int FOUR = 4;

	// Constructor
	public Hand(){
	}

	//methods
	public void addCard(Card card, int i){
		availableCards[i] = card;
	}

	public Card getCard(int i){
		return availableCards[i];
	}

	public int numCards(){
		return availableCards.length;
	}
	//sorts by rank
	public void sortByRank(){
		Arrays.sort(availableCards, new rankComparator());
	}
	//sorts by suit
	public void sortBySuit(){
		Arrays.sort(availableCards, new suitComparator());
	}
	//sorts by suit then rank
	public void sortBySuitThenRank(){
		Arrays.sort(availableCards, new suitComparator());
		Arrays.sort(availableCards, new rankComparator());
	}
	//sorts by rank then suit
	public void sortByRankThenSuit(){
		Arrays.sort(availableCards, new rankComparator());
		Arrays.sort(availableCards, new suitComparator());
	}

	public String evaluateHand(){
		String handResult = new String();
		int[] rankCounter = new int[13];
		int[] suitCounter = new int[4];

		// initializations
		for (int i=0;i<rankCounter.length;i++){
			rankCounter[i] =0;
		}

		for (int i=4;i<suitCounter.length;i++){
			suitCounter[i] = 0;
		}

		// Loop through sorted cards and total ranks
		for(int i=0; i<availableCards.length;i++){
			rankCounter[ availableCards[i].getRank() ]++;
			suitCounter[ availableCards[i].getSuit() ]++;
		}

		//sort cards for evaluation
		this.sortByRankThenSuit();

		// hands are already sorted by rank and suit for royal and straight flush checks.              
		// check for royal flush
		handResult = evaluateRoyal(rankCounter, suitCounter);

		// check for straight flush
		if (handResult == null || handResult.length() == 0){
			handResult = evaluateStraightFlush(rankCounter, suitCounter);
		}

		// check for four of a kind
		if (handResult == null || handResult.length() == 0){
			handResult = evaluateFourOfAKind(rankCounter);
		}

		// check for full house
		if (handResult == null || handResult.length() == 0){
			handResult = evaluateFullHouse(rankCounter);
		}

		// check for flush
		if (handResult == null || handResult.length() == 0){
			handResult = evaluateFlush(rankCounter, suitCounter);
		}

		// check for straight
		if (handResult == null || handResult.length() == 0){
			// re-sort by rank, up to this point we had sorted by rank and suit
			// but a straight is suit independent.
			this.sortByRank();
			handResult = evaluateStraight(rankCounter);
		}

		// check for three of a kind
		if (handResult == null || handResult.length() == 0){
			handResult = evaluateThreeOfAKind(rankCounter);
		}

		// check for two pair
		if (handResult == null || handResult.length() == 0){
			handResult = evaluateTwoPair(rankCounter);
		}

		// check for one pair
		if (handResult == null || handResult.length() == 0){
			handResult = evaluateOnePair(rankCounter);
		}

		// check for highCard
		if (handResult == null || handResult.length() == 0){
			handResult = evaluateHighCard(rankCounter);
		}

		return handResult;
	}

	private String evaluateRoyal(int[] rankCounter, int[] suitCounter){
		String result = "";

		// Check for Royal Flush (10 - Ace of the same suit).
		// check if there are 5 of one suit, if not royal is impossible        
		if ((rankCounter[9] >= 1 &&       /* 10 */
				rankCounter[10] >= 1 &&   /* Jack */
				rankCounter[11] >= 1 &&  /* Queen */
				rankCounter[12] >= 1 &&  /* King */
				rankCounter[0] >= 1)    /* Ace */
				&& (suitCounter[0] > 4 || suitCounter[1] > 4 ||
						suitCounter[2] > 4 || suitCounter[3] > 4)){

			// min. requirements for a royal flush have been met,
			// now loop through records for an ace and check subsequent cards.
			// Loop through the aces first since they are the first card to
			// appear in the sorted array of 7 cards.
			royalSearch:
				for (int i=0;i<3;i++){
					// Check if first card is the ace.
					// Ace must be in position 0, 1 or 2
					if (availableCards[i].getRank() == 0){
						// because the ace could be the first card in the array
						// but the remaining 4 cards could start at position 1,
						// 2 or 3 loop through checking each possibility.
						for (int j=1;j<4-i;j++){
							if ((availableCards[i+j].getRank() == 9 &&
									availableCards[i+j+1].getRank() == 10 &&
									availableCards[i+j+2].getRank() == 11 &&
									availableCards[i+j+3].getRank() == 12)
									&&
									(availableCards[i].getSuit() == availableCards[i+j].getSuit() &&
									availableCards[i].getSuit() == availableCards[i+j+1].getSuit() &&
									availableCards[i].getSuit() == availableCards[i+j+2].getSuit() &&
									availableCards[i].getSuit() == availableCards[i+j+3].getSuit())){
								// Found royal flush, break and return.
								result = "Royal Flush!! Suit: " + Card.suitAsString(availableCards[i].getSuit());
								break royalSearch;                             
							}
						}
					}                              
				}
		}              
		return result;
	}

	// Straight flush is 5 consecutive cards of the same suit.
	private String evaluateStraightFlush(int[] rankCounter, int[] suitCounter){
		String result = "";

		if (suitCounter[0] > 4 || suitCounter[1] > 4 ||
				suitCounter[2] > 4 || suitCounter[3] > 4){

			// Loop through available cards looking for 5 consecutive cards of the same suit,
			// start in reverse to get the highest value straight flush
			for (int i=availableCards.length-1;i>3;i--){
				if ((availableCards[i].getRank()-ONE == availableCards[i-ONE].getRank() &&
					availableCards[i].getRank()-TWO == availableCards[i-TWO].getRank() &&
					availableCards[i].getRank()-THREE == availableCards[i-THREE].getRank() &&
					availableCards[i].getRank()-FOUR == availableCards[i-FOUR].getRank())
					&&
					(availableCards[i].getSuit() == availableCards[i-ONE].getSuit() &&
					availableCards[i].getSuit() == availableCards[i-TWO].getSuit() &&
					availableCards[i].getSuit() == availableCards[i-THREE].getSuit() &&
					availableCards[i].getSuit() == availableCards[i-FOUR].getSuit())){
					// Found royal flush, break and return.
					result = "Straight Flush!! " + Card.rankAsString(availableCards[i].getRank()) + " high of " + Card.suitAsString(availableCards[i].getSuit());
					break;
				}
			}
		}
		return result;
	}

	// Four of a kind is 4 cards with the same rank: 2-2-2-2, 3-3-3-3, etc...
	private String evaluateFourOfAKind(int[] rankCounter){
		String result = "";

		for (int i=0;i<rankCounter.length;i++){
			if (rankCounter[i] == FOUR){
				result = "Four of a Kind, " + Card.rankAsString(i) +"'s";
				break;
			}
		}      
		return result;
	}

	// Full house is having 3 of a kind of one rank, and two of a kind of
	// a second rank.  EX: J-J-J-3-3
	private String evaluateFullHouse(int[] rankCounter){
		String result = "";
		short threeOfKindRank = -1;
		short twoOfKindRank = -1;

		for (int i=rankCounter.length;i>0;i--){
			if ((threeOfKindRank < (short)0) || (twoOfKindRank < (short)0)){
				if ((rankCounter[i-ONE]) > 2){
					threeOfKindRank = (short) (i-ONE);                                     
				}
				else if ((rankCounter[i-ONE]) > 1){
					twoOfKindRank = (short)(i-ONE);
				}
			}
			else
			{
				break;
			}
		}

		if ((threeOfKindRank >= (short)0) && (twoOfKindRank >= (short)0)){
			result = "Full House: " + Card.rankAsString(threeOfKindRank) + "'s full of " + Card.rankAsString(twoOfKindRank) + "'s";
		}

		return result;
	}

	// Flush is 5 cards of the same suit.
	private String evaluateFlush(int[] rankCounter, int[] suitCounter){
		String result = "";

		// verify at least 1 suit has 5 cards or more.
		if (suitCounter[0] > 4 || suitCounter[1] > 4 ||
				suitCounter[2] > 4 || suitCounter[3] > 4){

			for (int i=availableCards.length-1;i>3;i--){
				if (availableCards[i].getSuit() == availableCards[i-ONE].getSuit() &&
						availableCards[i].getSuit() == availableCards[i-TWO].getSuit() &&
						availableCards[i].getSuit() == availableCards[i-THREE].getSuit() &&
						availableCards[i].getSuit() == availableCards[i-FOUR].getSuit()){
					// Found royal flush, break and return.
					result = "Flush!! " + Card.rankAsString(availableCards[i].getRank()) + " high of " + Card.suitAsString(availableCards[i].getSuit());
					break;
				}
			}                      
		}


		return result;
	}

	// Straight is 5 consecutive cards, regardless of suit.
	private String evaluateStraight(int[] rankCounter){
		String result = "";

		// loop through rank array to check for 5 consecutive
		// index with a value greater than zero
		for (int i=rankCounter.length;i>4;i--){
			if ((rankCounter[i-1] > 0) &&
					(rankCounter[i-2] > 0) &&
					(rankCounter[i-3] > 0) &&
					(rankCounter[i-4] > 0) &&
					(rankCounter[i-5] > 0)){
				result = "Straight " + Card.rankAsString(i-1) + " high";
				break;
			}
		}
		return result;
	}

	// Three of a kind is 3 cards of the same rank.
	private String evaluateThreeOfAKind(int[] rankCounter){
		String result = "";

		// loop through rank array to check for 5 consecutive
		// index with a value greater than zero
		for (int i=rankCounter.length;i>0;i--){
			if (rankCounter[i-1] > 2){
				result = "Three of a Kind " + Card.rankAsString(i-1) + "'s";
				break;
			}
		}
		return result;
	}

	// Two pair is having 2 cards of the same rank, and two
	// different cards of the same rank.  EX: 3-3-7-7-A
	private String evaluateTwoPair(int[] rankCounter){
		String result = "";
		short firstPairRank = -1;
		short secondPairRank = -1;

		for (int i=rankCounter.length;i>0;i--){
			if ((firstPairRank < (short)0) || (secondPairRank < (short)0)){                        
				if (((rankCounter[i-ONE]) > 1) && (firstPairRank < (short)0)){
					firstPairRank = (short) (i-ONE);                                       
				}
				else if ((rankCounter[i-ONE]) > 1){
					secondPairRank = (short)(i-ONE);
				}
			}
			else
			{
				// two pair found, break loop.
				break;
			}
		}

		// populate output
		if ((firstPairRank >= (short)0) && (secondPairRank >= (short)0))
		{
			if (secondPairRank == (short)0)
			{
				result = "Two Pair: " + Card.rankAsString(secondPairRank) + "'s and " + Card.rankAsString(firstPairRank) + "'s";
			}
			else
			{
				result = "Two Pair: " + Card.rankAsString(firstPairRank) + "'s and " + Card.rankAsString(secondPairRank) + "'s";
			}                      
		}

		return result;
	}

	// One is is two cards of the same rank.
	private String evaluateOnePair(int[] rankCounter){
		String result = "";

		for (int i=rankCounter.length;i>0;i--){
			if((rankCounter[i-ONE]) > 1){
				result = "One Pair: " + Card.rankAsString(i-ONE) + "'s";       
				break;
			}
		}
		return result;
	}

	// high card is the highest card out of the 7 possible cards to be used.
	private String evaluateHighCard(int[] rankCounter){
		String result = "";

		for (int i=rankCounter.length;i>0;i--){
			if((rankCounter[i-ONE]) > 0){
				result = "High Card: " + Card.rankAsString(i-ONE);
				break;
			}
		}
		return result;
	}

}