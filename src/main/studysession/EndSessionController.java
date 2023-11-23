package main.studysession;

import animatefx.animation.SlideInLeft;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.player.PlayerController;
import models.Deck;


public class EndSessionController {
    private Parent root;
    private Stage stage;
    private Scene scene;

    private Deck deck;

    @FXML
    Button backBtn;
    @FXML
    Label doneBar;
    @FXML
    Label forgotBar;
    @FXML
    Label needLearnBar;

    public void switchToPlayer(ActionEvent event) {

        System.out.println("Switch to player");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/player.fxml"));
            Parent root = loader.load();
            PlayerController controller = loader.getController();
            controller.initPlayer(this.deck);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add("/resources/css/player.css");
            stage.setScene(scene);
            new SlideInLeft(root).play();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void initEndSession(Deck deck) {
        this.deck = deck;
        doneBar.setText(String.valueOf(deck.cardsSize()));
        forgotBar.setText("0");
        needLearnBar.setText("0");
    }

}
