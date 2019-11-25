import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class BlackJack implements Game{
	
	private Deck deck;
	private Player[] players;
	private int[] totals;
	private int pTurn = 0;//tracks which player's turn it is
	private Pane pane = new Pane();

	public BlackJack(int numOfDecks, int plyrs){//initialize number of decks used for the game and number of players playing
		if(numOfDecks==1){
			deck = new Deck();
		}
		else if(numOfDecks>1){
			deck = new Deck(numOfDecks);
		}
		players = new Player[plyrs];
		for(int i=0; i<plyrs; i++){
			players[i] = new Player(i);
		}
		totals = new int[plyrs];
	}

	public void play(Stage stage){
		
		pane.setMinHeight(700);
		pane.setMinWidth(700);
		
		Button deal = new Button("Deal");
		Button quit = new Button("Quit");
		Button hit = new Button("Hit");
		Button pass = new Button("Pass");
		Button newGame = new Button("New Game");
		
		deal.setLayoutX(300);
		deal.setLayoutY(300);
		quit.setLayoutX(600);
		quit.setLayoutY(620);
		hit.setLayoutX(300);
		hit.setLayoutY(600);
		pass.setLayoutX(350);
		pass.setLayoutY(600);
		newGame.setLayoutX(450);
		newGame.setLayoutY(620);
		
		hit.setVisible(false);
		pass.setVisible(false);
		pane.getChildren().add(deal);
		pane.getChildren().add(quit); 
		pane.getChildren().add(hit);
		pane.getChildren().add(pass);
		
		Text[] playerLabels = new Text[players.length];
		Text[] playerCards = new Text[players.length];
		
		//click event handlers
		deal.setOnMouseClicked(me->{//sets up the hit and pass buttons and adds a place for each player as well as deals out 2 cards to each player
			deck.shuffle();
			deal();
			deal.setVisible(false);
			hit.setVisible(true);
			pass.setVisible(true);
			for(int i=0; i<players.length; i++){//sets up all the labels for the players and their hands
				playerLabels[i] = new Text();
				playerLabels[i].setText("Player " + i);
				playerLabels[i].setLayoutX(i*100+10);
				playerLabels[i].setLayoutY(20);
				
				playerCards[i] = new Text();
				playerCards[i].setLayoutX(i*100+10);
				playerCards[i].setLayoutY(50);
				
				String t = "";
				for(int j=0; j<players[i].getHand().size(); j++){
					t += players[i].getHand().get(j).getValue() + "\n";
				}
				playerCards[i].setText(t);
				pane.getChildren().add(playerLabels[i]);
				pane.getChildren().add(playerCards[i]);
			}
			
			playerLabels[0].setStroke(Color.RED);
			pane.getChildren().add(newGame);
			
			
		});
		
		hit.setOnMouseClicked(me->{
			hit(pTurn);
		});
		
		pass.setOnMouseClicked(me->{
			pass();
		});
		
		quit.setOnMouseClicked(me->{
			stage.close();
		});
		
		newGame.setOnMouseClicked(me->{
			pTurn = 0;
			pane.getChildren().clear();
			pane.getChildren().add(deal);
			pane.getChildren().add(quit);
			pane.getChildren().add(hit);
			pane.getChildren().add(pass);
			hit.setVisible(false);
			pass.setVisible(false);
			deal.setVisible(true);
			for(int i=0; i<players.length; i++){
				players[i].discardHand();
				totals[i] = 0;
			}
			deck.shuffle();
		});
		
		
		Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setHeight(700);
        stage.setWidth(700);
        stage.setTitle("Black Jack");
        stage.show();
	}
	
	public void deal(){//deal cards to the players for the start of the game
		for(int i=0; i<players.length*2; i++){
			if(i<players.length){
				players[i].drawCard(deck.draw());
			}
			else{
				players[i-players.length].drawCard(deck.draw());
			}
			
		}
		for(int i=0; i<players.length; i++){
			totals[i] = playerTotal(players[i]);
		}
		for(int i=0; i<players.length; i++){
			if(totals[i]==21){//if someone's hand is 21 end the game since it is black jack
				pane.getChildren().get(2).setVisible(false);
				pane.getChildren().get(3).setVisible(false);
				compareTotals();
			}
		}
		
	}
	
	public void hit(int plyrNum){//deals a card to the player who called for a hit
		players[plyrNum].drawCard(deck.draw());
		int hSize = players[plyrNum].getHand().size();
		String t = ((Text)pane.getChildren().get(5+plyrNum*2)).getText();
		t += players[plyrNum].getHand().get(hSize-1).getValue() + "\n";
		((Text)pane.getChildren().get(5+plyrNum*2)).setText(t);
		isBust(players[plyrNum]);
	}
	
	public void pass(){//updates the player turn and checks if the game has ended
		((Text)pane.getChildren().get(4+pTurn*2)).setStroke(Color.BLACK);
		pTurn++;
		if(isGameOver()){
			compareTotals();
		}
	}
	
	public void isBust(Player p){//returns true if the players card values add up to over 21
		int total = playerTotal(p);
		if(total>21){
			totals[p.getPlayerId()] = 0;//set the players total to 0 since they busted
			pass();//call since this players turn is over
		}
	}
	
	public int playerTotal(Player p){
		int total = 0;
		for(int i=0; i<p.getHand().size(); i++){//add the total value of the players hand
			if(p.getHand().get(i) instanceof NumberCard){
				total+=Integer.parseInt(((Card) p.getHand().get(i)).getValue());
			}
			else{
				if(((Card) p.getHand().get(i)).getValue().equals("Ace")){
					if(total+11<=21){
						total+=11;
					}
					else{
						total+=1;
					}
				}
				else{
					total+=10;
				}
			}
		}
		totals[p.getPlayerId()] = total;
		return total;
	}
	
	public boolean isGameOver(){//checks for if the last players turn has played out
		if(pTurn>=players.length){
			return true;
		}
		((Text)pane.getChildren().get(4+pTurn*2)).setStroke(Color.RED);
		return false;
	}
	
	public void compareTotals(){//checks to see who the winner or winners are and displays the results
		int winnerTotal = totals[0];
		Player winner = players[0];
		ArrayList<Player> winners = new ArrayList<Player>();
		winners.add(winner);
		for(int i=1; i<totals.length; i++){
			if(winnerTotal<totals[i]){//if the current winners total is less then the new player is the current winner
				winner = players[i];
				winnerTotal = totals[i];
				winners.clear();
				winners.add(winner);
			}
			else if(winnerTotal==totals[i]){
				if(winner.getHand().size()>players[i].getHand().size()){//if the players' totals are the same but one player has fewer cards that player is the winner
					winner = players[i];
					winnerTotal = totals[i];
					winners.clear();
					winners.add(winner);
				}
				else if(winner.getHand().size()==players[i].getHand().size()){//if the players totals and number of cards are the same they are tied as winners along with anyone else who is tied
					winners.add(players[i]);
				}
			}
		}
		
		Text results = new Text();
		results.setLayoutX(30);
		results.setLayoutY(600);
		if(winners.size()==1){//only one player is a winner
			results.setText("The winner is player " + winner.getPlayerId() + " with " + winnerTotal + " points. Congratulations!");
		}
		else{//more than one player is a winner
			String text = "The winners are players ";
			for(int i=0; i<winners.size(); i++){
				text += winners.get(i).getPlayerId() + " ";
				if(i<winners.size()-1){
					text += " and ";
				}
			}
			results.setText(text + "all of which had  " + winnerTotal + " points and " + winner.getHand().size() + " cards. Congratulations!");
		}
		
		if(winnerTotal==0){
			results.setText("Everyone busted nobody wins");
		}
		
		//hide the hit and pass buttons and add the results
		pane.getChildren().get(2).setVisible(false);
		pane.getChildren().get(3).setVisible(false);
		pane.getChildren().add(results);
	}
	
}
