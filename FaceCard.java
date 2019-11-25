
public class FaceCard implements Card {
	
	private String value;
	private int suit;
	private int color;
	
	public FaceCard(String cv, int cs){//initializes all the variables
		value = cv;
		suit = cs;
		color = cs%2;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public int getCardSuit() {
		return suit;
	}

	@Override
	public int getCardColor() {
		return color;
	}
	
}
