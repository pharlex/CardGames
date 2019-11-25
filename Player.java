import java.util.ArrayList;

public class Player {
	
	private ArrayList<Card> hand = new ArrayList<Card>();
	private int playerId;
	
	public Player(int id){
		playerId = id;
	}
	
	public int getPlayerId(){
		return playerId;
	}
	
	public void drawCard(Card c){
		hand.add(c);
	}
	
	public void discardCard(Card c){//removes the specified card from the players hand
		int loc = hand.indexOf(c);
		if(loc>=0){//makes sure the card is in the players hand
			hand.remove(loc);
		}
	}
	
	public void discardHand(){
		hand.clear();
	}
	
	public ArrayList<Card> getHand(){
		return hand;
	}
	
}
