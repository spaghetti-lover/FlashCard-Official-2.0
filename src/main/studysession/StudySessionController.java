package main.studysession;

import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.player.PlayerController;
import models.Card;
import models.Deck;
import services.DataService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class StudySessionController {
    private Parent root;
    private Stage stage;
    private Scene scene;



    private Deck deck;
    private Card question;
    private Card answer;
    private DataService ds = DataService.getInstance();

    //Danh dau xem khi nao hien cau tra loi
    private boolean showAnswer = false;
    private Card selectedCard;
    private int currentCardIndex;

    @FXML
    Label questionLabel;
    @FXML
    Button nextBtn;
    @FXML
    Button preBtn;


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

    public void initStudySession(Deck deck) {
        this.deck = deck;
        currentCardIndex = 0;
        if (this.deck != null) {
            selectedCard = deck.getCards().get(currentCardIndex);
            questionLabel.setText(selectedCard.getQuestion());
            attachEventHandlers();
        } else {
            System.out.println("No Cards Available");
        }
    }

    //Dung de gan gia tri cho currentCardIndex
    public void initStudySession(Deck deck, int currentCardIndex) {
        this.deck = deck;
        this.currentCardIndex = currentCardIndex;
        if (this.deck != null) {
            selectedCard = deck.getCards().get(currentCardIndex);
            questionLabel.setText(selectedCard.getQuestion());
            attachEventHandlers();
        } else {
            System.out.println("No Cards Available");
        }
    }

    // Xu ly su kien khi an chuot vao cau hoi va dap an
    public void attachEventHandlers() {
        questionLabel.setOnMouseClicked(event -> {
            this.toggleAnswer();
        });
    }

    public void toggleAnswer() {
        showAnswer = !showAnswer;
        //Flip card
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.4), questionLabel);
        rotateTransition.setAxis(Rotate.X_AXIS);
        rotateTransition.setByAngle(360);
        rotateTransition.play();
        if (showAnswer && selectedCard != null) {
            questionLabel.setText(selectedCard.getAnswer());
        } else if (selectedCard != null) {
            questionLabel.setText(selectedCard.getQuestion());
        } else { // no card selected
            System.out.println("No card selected");
        }

    }

    public void toggleNext() {
        if (currentCardIndex <= deck.cardsSize() - 1) {
            currentCardIndex++;
            new SlideInRight(questionLabel).play();
            initStudySession(deck, currentCardIndex);
        } else {
            System.out.println("Reached the end of the deck");
        }

    }

    public void togglePrev() {
        if (currentCardIndex > 0) {
            currentCardIndex--;
            new SlideInLeft(questionLabel).play();
            initStudySession(deck, currentCardIndex);
        } else {
            System.out.println("No card before");
        }
    }


}
