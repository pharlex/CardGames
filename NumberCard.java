
public class NumberCard implements Card {
	
	private String value;
	private int suit;
	private int color;
	
	public NumberCard(int cv, int cs){
		value = Integer.toString(cv);
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
