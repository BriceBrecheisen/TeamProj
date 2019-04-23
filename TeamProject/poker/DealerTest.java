package poker;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

public class DealerTest {

	private ArrayList<Card> deck;
	private ArrayList<Card> community;

	private int rando;

	private int s,r;

	private Dealer d;

	@Before
	public void setUp() throws Exception 
	{
		d = new Dealer();
		rando = ((int)Math.random()*52); 
		r = ((int)Math.random()*12);
		s = ((int)Math.random()*3);

	}

	@Test
	public void testDealer() {
		deck = new ArrayList<Card>();
		community = new ArrayList<Card>();

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 13; j++)
				deck.add(new Card(j,i));
	}


	@Test
	public void testSetcommCard() 
	{
		Card commCard = new Card(r,s);

		d.setcommCard(commCard, 0);

		Card test = d.getcommCard(0);

		assertNotNull("Check test card", test);
	}

	@Test
	public void testCommSize() 
	{
		int test = d.commSize();
		
		assertEquals(d.commSize(),test);
		
	}


}
