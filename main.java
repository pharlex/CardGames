import javafx.stage.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class main extends Application{

	static Game game;
	public static void main(String[] args) {
		
		Application.launch(args);

	}

	@Override
	public void start(Stage stage){
		
		Pane pane = new Pane();
		pane.setMinHeight(700);
		pane.setMinWidth(700);
		Button blackJack = new Button("Black Jack");
		blackJack.setLayoutX(30);
		blackJack.setLayoutY(30);
		HBox ps = new HBox();
		HBox ds = new HBox();
		Button plus = new Button("+");
		Button minus = new Button("-");
		Button plus2 = new Button("+");
		Button minus2 = new Button("-");
		minus.setDisable(true);
		minus2.setDisable(true);
		
		Text pNumLabel = new Text("How many players are playing?");
		Text dNumLabel = new Text("How many decks do you wish to use?");
		Text playerAmount = new Text("2");
		Text deckAmount = new Text("1");
		
		ps.getChildren().addAll(plus, new Text(" "), playerAmount, new Text(" "), minus);
		ds.getChildren().addAll(plus2, new Text(" "), deckAmount, new Text(" "), minus2);
		ps.setLayoutX(30);
		ps.setLayoutY(550);
		ds.setLayoutX(30);
		ds.setLayoutY(600);
		pNumLabel.setLayoutX(30);
		pNumLabel.setLayoutY(540);
		dNumLabel.setLayoutX(30);
		dNumLabel.setLayoutY(590);
		
		//event handlers
		blackJack.setOnMouseClicked(me ->{//sets up and starts a game of black jack
			game = new BlackJack(Integer.parseInt(deckAmount.getText()), Integer.parseInt(playerAmount.getText()));
			game.play(stage);
		});
		
		plus.setOnMouseClicked(me->{
			playerAmount.setText((Integer.parseInt(playerAmount.getText())+1)+"");
			if(Integer.parseInt(playerAmount.getText())==7){
				plus.setDisable(true);
			}
			minus.setDisable(false);
		});
		
		minus.setOnMouseClicked(me->{
			playerAmount.setText((Integer.parseInt(playerAmount.getText())-1)+"");
			if(Integer.parseInt(playerAmount.getText())==2){
				minus.setDisable(true);
			}
			plus.setDisable(false);
		});
		
		plus2.setOnMouseClicked(me->{
			deckAmount.setText((Integer.parseInt(deckAmount.getText())+1)+"");
			if(Integer.parseInt(deckAmount.getText())==4){
				plus2.setDisable(true);
			}
			minus2.setDisable(false);
		});
		
		minus2.setOnMouseClicked(me->{
			deckAmount.setText((Integer.parseInt(deckAmount.getText())-1)+"");
			if(Integer.parseInt(deckAmount.getText())==1){
				minus2.setDisable(true);
			}
			plus2.setDisable(false);
		});
		
		
		pane.getChildren().addAll(ps, ds, pNumLabel, dNumLabel);
		pane.getChildren().addAll(blackJack);//adds buttons for each game
		Scene scene = new Scene(pane);
		stage.setTitle("Game Select");
        stage.setScene(scene); 
        stage.setHeight(700);
        stage.setWidth(700);
        stage.show();
		
	}

}
