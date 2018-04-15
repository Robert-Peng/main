package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
//import com.calendarfx.view.CalendarView;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private CalendarWindow calendarWindow;
    //private BrowserPanel browserPanel;
    private PersonListPanel personListPanel;
    private PetPatientListPanel petPatientListPanel;
    private Config config;
    private UserPrefs prefs;

    @FXML
    private StackPane calendarPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane petPatientListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setWindowDefaultSize(prefs);
        setWindowDefaultTheme(prefs);

        setAccelerators();
        registerAsAnEventHandler(this);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        //browserPanel = new BrowserPanel();
        //@@author Robert-Peng
        calendarWindow = new CalendarWindow(logic.getFilteredAppointmentList());
        this.calendarPlaceholder.getChildren().add(calendarWindow.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        petPatientListPanel = new PetPatientListPanel(logic.getFilteredPetPatientList());
        petPatientListPanelPlaceholder.getChildren().add(petPatientListPanel.getRoot());
        //@@author
        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
        getRoot().getScene().getStylesheets().add(prefs.getGuiSettings().getCurrentTheme());
    }

    //@@author aquarinte
    /**
     * Sets the default theme based on user preferences.
     */
    private void setWindowDefaultTheme(UserPrefs prefs) {
        getRoot().getScene().getStylesheets().add(prefs.getGuiSettings().getCurrentTheme());
    }

    /**
     * Returns the current size, position, and theme of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        ObservableList<String> cssFiles = getRoot().getScene().getStylesheets();
        assert cssFiles.size() == 2 : "There should only be 2 stylesheets used in main Window.";

        String theme = cssFiles.stream().filter(c -> !c.contains("/view/Extensions.css")).findFirst().get();
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY(), theme);
    }

    //@@author
    /**
     * Opens the help window.
     */
    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }


    /*void releaseResources() {
        browserPanel.freeResources();
    }*/

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    //@@author aquarinte
    /**
     * Changes the theme of Medeina.
     */
    @Subscribe
    public void handleChangeThemeEvent(ChangeThemeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        String userSelectedTheme = event.theme.getThemePath();
        String userSelectedStyleSheet = this.getClass().getResource(userSelectedTheme).toExternalForm();
        if (!hasStyleSheet(userSelectedStyleSheet)) {
            changeStyleSheet(userSelectedStyleSheet);
        }
    }

    /**
     * Checks whether {@code theme} is already in use by the application.
     */
    public Boolean hasStyleSheet(String theme) {
        List<String> styleSheetsInUsed = getRoot().getScene().getStylesheets();
        if (styleSheetsInUsed.contains(theme)) {
            return true;
        }
        return false;
    }

    /**
     * Removes all existing stylesheets and add the given {@code theme} to style sheets.
     * Re-adds Extensions.css to style sheets.
     */
    public void changeStyleSheet(String theme) {
        String extensions = this.getClass().getResource("/view/Extensions.css").toExternalForm();
        getRoot().getScene().getStylesheets().clear();
        getRoot().getScene().getStylesheets().add(extensions); //re-add Extensions.css
        boolean isChanged = getRoot().getScene().getStylesheets().add(theme);
        assert isChanged == true : "Medeina's theme is not successfully changed.";
    }
}
