package com.knoldus.javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class JavaFXLoader extends Application {
    public final static String ID = "loader";
    public static final String LOADER_CONTAINER_ID = "loader-container-id";
    public final static String LOADER_BACKGROUND_ID = "loader-background-id";
    public final static String LOADER_CONTENT_ID = "loader-content-id";
    public final static String LOADER_CONTROLS_ID = "loader-controls-id";
    public final static String LOADER_LOGO_ID = "loader-logo-id";
    public final static String LOADER_STATUS_ID = "loader-status-id";
    public final static String LOADER_CLOSE_BUTTON_ID = "loader-close-button-id";

    public final static String IMAGES = "file:src/main/resources/img";
    public final static String LOADER_BACKGROUND = IMAGES + "/loader-background.jpg";
    public final static String LOADER_LOGO = IMAGES + "/loader-logo.gif";
    public final static String LOADER_ICON = IMAGES + "/loader-icon.jpg";
    public final static String LOADER_CLOSE_BUTTON = IMAGES + "/loader-close-button.png";
    public final static String LOADER_CLOSE_BUTTON_HOVERED = IMAGES + "/loader-close-button-hovered.png";
    public final static String LOADER_CLOSE_BUTTON_PRESSED = IMAGES + "/loader-close-button-pressed.png";

    public final static String LOADER_STAGE_TITLE = "Loader";
    public final static String LOADER_STATUS = "Loading";

    public final static double LOADER_CLOSE_BUTTON_SIZE = 24;
    private static final double LOADER_WIDTH = 768;
    private static final double LOADER_HEIGHT = 464;
    private static final double LOADER_LOGO_SIZE = 200;

    private static double xOffset = 0;
    private static double yOffset = 0;

    private final double LOADER_STATUS_FONT_SIZE = 24;
    private final String LOADER_STATUS_FONT_FAMILY = "Roboto Medium";
    private final Color LOADER_STATUS_COLOR = Color.rgb(25, 25, 25, 1);
    private final Font LOADER_STATUS_FONT = Font.font(LOADER_STATUS_FONT_FAMILY, FontWeight.BOLD, LOADER_STATUS_FONT_SIZE);

    private final StackPane m_loaderContainer = new StackPane();
    private final VBox m_loaderContent = new VBox();
    private final HBox m_loaderControls = new HBox();
    private final ImageView m_loaderBackground = new ImageView();
    private final ImageView m_loaderLogo = new ImageView();
    private final ImageView m_loaderCloseButtonImage = new ImageView();
    private final Text m_loaderStatus = new Text();
    private final Button m_loaderCloseButton = new Button();
    private final Group m_root = new Group();
    private final Stage m_stage = new Stage();
    private Image m_image;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        initialize();
        showLoaderWindow();
    }

    private void initialize() {
        Scene scene = new Scene(m_root, LOADER_WIDTH, LOADER_HEIGHT);
        m_stage.setScene(scene);
        m_stage.setTitle(JavaFXLoader.LOADER_STAGE_TITLE);
        m_stage.setHeight(LOADER_HEIGHT);
        m_stage.setWidth(LOADER_WIDTH);
        m_stage.initStyle(StageStyle.TRANSPARENT);
        m_stage.getIcons().addAll(new Image(LOADER_ICON));
        m_stage.setOnCloseRequest(windowEvent ->
        {
            windowEvent.consume();
            handleCloseEvent();
        });

        m_root.setId(ID);

        StackPane.setAlignment(m_loaderContainer, Pos.CENTER);

        m_loaderContainer.setId(LOADER_CONTAINER_ID);
        m_loaderContainer.setMaxSize(LOADER_WIDTH, LOADER_HEIGHT);

        initializeLoaderBackground();

        initializeLoaderContent();

        initializeLoaderControls();

        m_loaderContainer.getChildren().addAll(m_loaderBackground, m_loaderContent, m_loaderControls);
        m_root.getChildren().add(m_loaderContainer);

        dragLoader();
    }

    public void initializeLoaderBackground() {
        m_image = new Image(LOADER_BACKGROUND);

        m_loaderBackground.setId(LOADER_BACKGROUND_ID);
        m_loaderBackground.setImage(m_image);
        m_loaderBackground.setFitWidth(LOADER_WIDTH);
        m_loaderBackground.setFitHeight(LOADER_HEIGHT);
    }

    public void initializeLoaderContent() {
        initializeLoaderLogo();
        initializeLoaderStatus();

        m_loaderContent.getChildren().addAll(m_loaderLogo, m_loaderStatus);

        m_loaderContent.setId(LOADER_CONTENT_ID);
        m_loaderContent.setSpacing(10);
        m_loaderContent.setAlignment(Pos.CENTER);
    }

    public void initializeLoaderLogo() {
        m_image = new Image(LOADER_LOGO);

        m_loaderLogo.setId(LOADER_LOGO_ID);
        m_loaderLogo.setImage(m_image);
        m_loaderLogo.setFitWidth(LOADER_LOGO_SIZE);
        m_loaderLogo.setFitHeight(LOADER_LOGO_SIZE);
    }

    public void initializeLoaderStatus() {
        m_loaderStatus.setText(LOADER_STATUS);
        m_loaderStatus.setId(LOADER_STATUS_ID);
        m_loaderStatus.setFill(LOADER_STATUS_COLOR);
        m_loaderStatus.setFont(LOADER_STATUS_FONT);
        m_loaderStatus.setTextAlignment(TextAlignment.CENTER);
    }

    public void initializeLoaderControls() {
        initializeLoaderCloseButton();

        m_loaderControls.getChildren().addAll(m_loaderCloseButton);
        m_loaderControls.setId(LOADER_CONTROLS_ID);
        m_loaderControls.setAlignment(Pos.TOP_RIGHT);
    }

    public void initializeLoaderCloseButton() {
        m_image = new Image(LOADER_CLOSE_BUTTON);
        m_loaderCloseButtonImage.setImage(m_image);
        m_loaderCloseButtonImage.setFitWidth(LOADER_CLOSE_BUTTON_SIZE);
        m_loaderCloseButtonImage.setFitHeight(LOADER_CLOSE_BUTTON_SIZE);

        m_loaderCloseButton.setId(LOADER_CLOSE_BUTTON_ID);
        m_loaderCloseButton.setGraphic(m_loaderCloseButtonImage);
        m_loaderCloseButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        m_loaderCloseButton.setOnAction(handleCloseEvent());
        m_loaderCloseButton.addEventHandler(MouseEvent.ANY, loaderCloseButtonMouseEvent());
    }

    private EventHandler<? super MouseEvent> loaderCloseButtonMouseEvent() {
        return (EventHandler<MouseEvent>) event ->
        {
            if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
                m_image = new Image(LOADER_CLOSE_BUTTON_HOVERED);
                m_loaderCloseButtonImage.setImage(m_image);
            } else if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
                m_image = new Image(LOADER_CLOSE_BUTTON);
                m_loaderCloseButtonImage.setImage(m_image);
            } else if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                m_image = new Image(LOADER_CLOSE_BUTTON_PRESSED);
                m_loaderCloseButtonImage.setImage(m_image);
            }
        };
    }

    public void showLoaderWindow() {
        m_stage.show();
        m_stage.toFront();
        m_stage.setAlwaysOnTop(true);
    }

    private EventHandler<ActionEvent> handleCloseEvent() {
        return event -> System.exit(0);
    }

    private void dragLoader() {
        m_loaderContainer.setOnMousePressed(event ->
        {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        m_loaderContainer.setOnMouseDragged(event ->
        {
            m_stage.setX(event.getScreenX() - xOffset);
            m_stage.setY(event.getScreenY() - yOffset);
        });
    }
}
