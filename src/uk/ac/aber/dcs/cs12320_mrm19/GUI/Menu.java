package uk.ac.aber.dcs.cs12320_mrm19.GUI;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import uk.ac.aber.dcs.cs12320_mrm19.App;


public class Menu extends GUI {
    /**
     *
     * @param app
     */
    public Menu(App app) {
        this.app = app;

    }

    /**
     * GUI for Start Up Screen
     */
    @Override
    public void screenUpdate() {

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        Label label = new Label("Patience - mrm19");
        label.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 50; -fx-text-fill: #008080;");

        Button goToGame = new Button("Start");
        goToGame.setPrefWidth(200);
        Button endGame = new Button("Exit");
        endGame.setPrefWidth(200);

        goToGame.setOnAction(event -> app.setScene(new Game(app)));
        endGame.setOnAction(event -> app.exit());

        vBox.getChildren().addAll(label, goToGame, endGame);
        app.setRoot(vBox);
    }

    /**
     * Start Up menu
     */
    @Override
    public void menuList() {
        System.out.println("******************************** Patience - mrm19 ********************************");
        System.out.println("1 - Start");
        System.out.println("2 - Exit");
    }

    /**
     *
     * @param choice user inputs
     */
    @Override
    public void input(String choice) {

        String[] args = choice.split(" ");

        switch (choice) {
            case "1":
                app.setScene(new Game(app));
                break;
            case "2":
                app.exit();
                break;
            default:
                System.out.println("Invalid Input");
        }
    }
}
