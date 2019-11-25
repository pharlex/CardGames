import java.util.Random;

public class Deck {
	private Card[] deck;
	private Card[] discardPile;
	private int nextCard = 0;//used to track which card is next to be drawn next
	private int discardNum = 0;//tracks the next number in the discardPile array
	public Deck(){//creates a single deck of cards
		deck = new Card[52];
		discardPile = new Card[52];
		int cNum = 0;//used to track the number you are on in the array
		for(int i=0; i<4; i++){
			for(int j=2; j<11; j++){
				deck[cNum] = new NumberCard(j, i);
				cNum++;
			}
			for(int k=11; k<15; k++){
				if(k==11){
					deck[cNum] = new FaceCard("Jack", i);
					cNum++;
				}
				else if(k==12){
					deck[cNum] = new FaceCard("Queen", i);
					cNum++;
				}
				else if(k==13){
					deck[cNum] = new FaceCard("King", i);
					cNum++;
				}
				else if(k==14){
					deck[cNum] = new FaceCard("Ace", i);
					cNum++;
				}
			}
		}
	}
	public Deck(int n){//creates n number of decks of cards
		int cNum = 0;//same as above cNum variable
		deck = new Card[n*52];
		discardPile = new Card[n*52];
		Card[][] decks = new Card[4][];
		for(int i=0; i<n; i++){//create the number of decks from the number passed in
			decks[i] = new Deck().getDeck();
		}
		for(int i=0; i<n; i++){//put all the decks together into one big deck
			for(int j=0; j<52; j++){
				deck[cNum] = decks[i][j];
				cNum++;
			}
		}
	}
	
	public Card[] getDeck(){
		return deck;
	}
	
	public void shuffle(){//shuffles the cards in the deck
		discardNum = 0;
		nextCard = 0;
		int sTimes = deck.length/52;//shuffle based on number of decks mixed together
		int r1;//random numbers used to swap 2 cards
		int r2;
		Card temp;
		Random rand = new Random();
		for(int i=0; i<500*sTimes; i++){
			r1 = rand.nextInt(deck.length);
			r2 = rand.nextInt(deck.length);
			temp = deck[r1];
			deck[r1] = deck[r2];
			deck[r2] = temp;
		}
	}
	
	public Card draw(){//returns the next card in the deck to be added to the players hand
		Card nCard = deck[nextCard];
		nextCard++;
		return nCard;
	}
	
	public void discardCard(Card c){
		discardPile[discardNum] = c;
		discardNum++;
	}
	
	public Card[] getDiscardPile(){
		return discardPile;
	}
	
}
