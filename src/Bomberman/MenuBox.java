package Bomberman;

import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MenuBox {
    private static final int WIDTH = 994;
    private static final int HEIGHT = 568;
    private static final String[] menuText = {
            "ONE PLAYER", "TWO PLAYERS", "TUTORIALS",
            "CREDITS", "QUIT",
    };
    private static final Font FONT = Font.font("", FontWeight.BOLD, 18);
    private static final Font FONT2 = Font.font("", FontWeight.BOLD, 27);

    private ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();
    private final Scene scene;
    public Scene getScene() {
        return this.scene;
    }

    private VBox menuBox;
    private int currentItem = 0;

    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);

        // Background
        try {
            Image image = new Image("file:res/imageBG.png");
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(WIDTH);
            imageView.setPreserveRatio(true);
            root.getChildren().addAll(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Background image not Found");
        }

        // Frame with animated letters
        ContentFrame frame2 = new ContentFrame(createMiddleContent());

        // Row containing the above Frame
        HBox hbox = new HBox(30, frame2);
        hbox.setTranslateX(330);
        hbox.setTranslateY(20);

        menuBox = new VBox(10,
                new MenuItem(menuText[0]),
                new MenuItem(menuText[1]),
                new MenuItem(menuText[2]),
                new MenuItem(menuText[3]),
                new MenuItem(menuText[4]));
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setTranslateX(410);
        menuBox.setTranslateY(200);

        getMenuItem(0).setActive(true);
        setOnItemAction();

        root.getChildren().addAll(hbox, menuBox);
        return root;
    }

    private void setOnItemAction() {
        ((MenuItem)(menuBox.getChildren().get(0))).setOnActivate(() -> {
            BorderPane forward = BombermanGame.showLevelThenPLay(BombermanGame.currentLevel, false);
            BombermanGame.forwardScene.setRoot(forward);
            BombermanGame.onlyStage.setScene(BombermanGame.forwardScene);
        });
        ((MenuItem)(menuBox.getChildren().get(1))).setOnActivate(() -> {
            BorderPane forward = BombermanGame.showLevelThenPLay(BombermanGame.currentLevel, true);
            BombermanGame.forwardScene.setRoot(forward);
            BombermanGame.onlyStage.setScene(BombermanGame.forwardScene);
        });
        ((MenuItem)(menuBox.getChildren().get(2))).setOnActivate(() -> {
            System.out.println("Tutorial? No.");
            try {
                tutorialsWindow("file:res/imageBG2.png",0);
            } catch (IOException e) {
                e.fillInStackTrace();
            }
            //
        });
        ((MenuItem)(menuBox.getChildren().get(3))).setOnActivate(() -> {
            System.out.println("Credit: Minh Dat Hang Hoa");
            try {
                tutorialsWindow("file:res/imageBG2.png", 1);
            } catch (IOException e) {
                e.fillInStackTrace();
            }
            //
        });
        ((MenuItem)(menuBox.getChildren().get(4))).setOnActivate(() -> {
            System.exit(0);
        });
    }

    private Node createMiddleContent() {
        String title = "Bomberman Version 1.0";
        HBox letters = new HBox(0);
        letters.setAlignment(Pos.CENTER);
        for (int i = 0; i < title.length(); i++) {
            Text letter = new Text(title.charAt(i) + "");
            letter.setFont(FONT2);
            letter.setFill(Color.WHITE);
            letters.getChildren().add(letter);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(2), letter);
            tt.setDelay(Duration.millis(i * 50));
            tt.setToY(-25);
            tt.setAutoReverse(true);
            tt.setCycleCount(TranslateTransition.INDEFINITE);
            tt.play();
        }
        return letters;
    }

    private void tutorialsWindow(String url, int type) throws IOException {
        int width = 1400;
        int height = 800;
        Stage window = new Stage();
        ImageView setTheme = new ImageView(new Image(url));
        setTheme.setFitWidth(width);
        setTheme.setFitHeight(height);

        Button buttons = new Button("CLOSE");
        buttons.setFont(FONT2);
        buttons.setTranslateY(300);
        buttons.setOnAction(event -> window.close());

        Label info = new Label(X.infoMenu[type]);
        info.setFont(FONT);
        info.setTranslateY(-50);

        BorderPane pane = new BorderPane();
        pane.setBottom(buttons);
        pane.setCenter(setTheme);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(setTheme, info, buttons);

        Scene newScene = new Scene(stackPane,width,height);

        window.setScene(newScene);
        window.setTitle(type == 0? "Instructions": "Credit");
        window.showAndWait();
    }

    // Chữ động
    private static class ContentFrame extends StackPane {
        public ContentFrame(Node content) {
            setAlignment(Pos.CENTER);

            Rectangle frame = new Rectangle(200, 200);
            frame.setArcWidth(25);
            frame.setArcHeight(25);
            frame.setStroke(new Color(1,0, 0,0.01));
            frame.setFill(new Color(0,1, 1,0.01));

            getChildren().addAll(frame, content);
        }
    }

    // Các lựa chọn
    private static class MenuItem extends HBox {
        private TriCircle c1 = new TriCircle(), c2 = new TriCircle();
        private Text text;
        private Runnable script;

        public MenuItem(String name) {
            super(15);
            setAlignment(Pos.CENTER);

            text = new Text(name);
            text.setFont(FONT);
            text.setEffect(new GaussianBlur(2));

            getChildren().addAll(c1, text, c2);
            setActive(false);
            setOnActivate(() -> System.out.println(name + " activated"));
        }

        public void setActive(boolean b) {
            c1.setVisible(b);
            c2.setVisible(b);
            text.setFill(b ? Color.WHITE : Color.GREY);
        }

        public void setOnActivate(Runnable r) {
            script = r;
        }

        public void activate() {
            if (script != null)
                script.run();
        }

        public Text getText() {
            return text;
        }
    }

    // Vòng tròn 2 bên 1 option.
    private static class TriCircle extends Parent {
        public TriCircle() {
            Shape shape1 = Shape.subtract(new Circle(5), new Circle(2));
            shape1.setFill(Color.WHITE);

            Shape shape2 = Shape.subtract(new Circle(5), new Circle(2));
            shape2.setFill(Color.WHITE);
            shape2.setTranslateX(5);

            Shape shape3 = Shape.subtract(new Circle(5), new Circle(2));
            shape3.setFill(Color.WHITE);
            shape3.setTranslateX(2.5);
            shape3.setTranslateY(-5);

            getChildren().addAll(shape1, shape2, shape3);

            setEffect(new GaussianBlur(2));
        }
    }

    private MenuItem getMenuItem(int index) {
        return (MenuItem) menuBox.getChildren().get(index);
    }

    public MenuBox(){
        scene = new Scene(createContent());
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                if (currentItem > 0) {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(--currentItem).setActive(true);
                }
            }
            if (event.getCode() == KeyCode.DOWN) {
                if (currentItem < menuBox.getChildren().size() - 1) {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(++currentItem).setActive(true);
                }
            }
            if (event.getCode() == KeyCode.ENTER) {
                getMenuItem(currentItem).activate();
            }
        });
    }

}
