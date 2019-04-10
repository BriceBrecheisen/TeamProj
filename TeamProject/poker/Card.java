package poker;

import java.util.*;

//ggdfgdfgfd
public class Card{
        private int rank;
        private int suit;
               
        private static String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        private static String[] suits = {"Diamonds", "Clubs", "Hearts", "Spades"};
 
        //Constructor
        public Card(int rank, int suit)
        {
                this.rank = rank;
                this.suit = suit;
        }
 
        // Getter and Setters
        public int getSuit()
        {
                return suit;
        }
 
        public int getRank()
        {
                return rank;
        }
       
        public void setSuit(int suit)
        {
                this.suit = suit;
        }
       
        public void setRank(int rank)
        {
                this.rank = rank;
        }
 
        // methods
        public static String rankAsString(int rank){
                return ranks[rank];
        }
       
        public static String suitAsString(int suit){
                return suits[suit];
        }
       
        public @Override String toString(){
                return rank + " of " + suit;
        }
       
        // Print card to string
        public String printCard(){
                return ranks[rank] + " of " + suits[suit];
        }
       
        // Determine if two cards are the same (Ace of Diamonds == Ace of Diamonds)
        public static boolean sameCard(Card card1, Card card2){
                return (card1.rank == card2.rank && card1.suit == card2.suit);
        }      
       
}

//these are comparators for the ranks and suits, created these with help from StackOverflow
 
class rankComparator implements Comparator<Object>
{
        public int compare(Object card1, Object card2) throws ClassCastException
        {
                // verify two Card objects are passed in
        	if (!((card1 instanceof Card) && (card2 instanceof Card)))
        	{
        		throw new ClassCastException("A Card object was expeected.  Parameter 1 class: " + card1.getClass()
                   + " Parameter 2 class: " + card2.getClass());
                }
               
                int rank1 = ((Card)card1).getRank();
                int rank2 = ((Card)card2).getRank();
               
                return rank1 - rank2;
        }
}
 
class suitComparator implements Comparator<Object>
{
        public int compare(Object card1, Object card2) throws ClassCastException
        {
                // verify two Card objects are passed in
        	if (!((card1 instanceof Card) && (card2 instanceof Card)))
        	{
        		throw new ClassCastException("A Card object was expeected.  Parameter 1 class: " + card1.getClass()
                   + " Parameter 2 class: " + card2.getClass());
                }
               
                int suit1 = ((Card)card1).getSuit();
                int suit2 = ((Card)card2).getSuit();
               
                return suit1 - suit2;
        }
}