import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class HotelReservationApp extends Application {
    private ReservationService service = new ReservationService();
    private ListView<String> reservationListView = new ListView<>();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hotel Reservation System");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));

        VBox form = createForm();
        VBox listView = createListView();

        root.setLeft(form);
        root.setCenter(listView);

        Scene scene = new Scene(root, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createForm() {
        VBox vbox = new VBox(10);

        TextField guestNameField = new TextField();
        guestNameField.setPromptText("Guest Name");

        ComboBox<String> roomTypeComboBox = new ComboBox<>();
        roomTypeComboBox.getItems().addAll("Single", "Double", "Suite");
        roomTypeComboBox.setPromptText("Room Type");

        DatePicker checkInDatePicker = new DatePicker();
        checkInDatePicker.setPromptText("Check-In Date");

        ComboBox<LocalTime> checkInTimeComboBox = createTimeComboBox();
        checkInTimeComboBox.setPromptText("Check-In Time");

        DatePicker checkOutDatePicker = new DatePicker();
        checkOutDatePicker.setPromptText("Check-Out Date");

        ComboBox<LocalTime> checkOutTimeComboBox = createTimeComboBox();
        checkOutTimeComboBox.setPromptText("Check-Out Time");

        Button createButton = new Button("Create Reservation");
        createButton.setOnAction(e -> {
            String guestName = guestNameField.getText();
            String roomType = roomTypeComboBox.getValue();
            LocalDate checkInDate = checkInDatePicker.getValue();
            LocalTime checkInTime = checkInTimeComboBox.getValue();
            LocalDate checkOutDate = checkOutDatePicker.getValue();
            LocalTime checkOutTime = checkOutTimeComboBox.getValue();

            if (guestName.isEmpty() || roomType == null || checkInDate == null || checkInTime == null
                    || checkOutDate == null || checkOutTime == null) {
                showAlert("Error", "All fields must be filled.");
            } else {
                String checkInDateTime = checkInDate.format(dateFormatter) + " " + checkInTime.format(timeFormatter);
                String checkOutDateTime = checkOutDate.format(dateFormatter) + " " + checkOutTime.format(timeFormatter);
                service.createReservation(guestName, roomType, checkInDateTime, checkOutDateTime);
                updateReservationList();
            }
        });

        vbox.getChildren().addAll(new Label("Create Reservation"), guestNameField, roomTypeComboBox, checkInDatePicker,
                checkInTimeComboBox, checkOutDatePicker, checkOutTimeComboBox, createButton);
        vbox.setStyle("-fx-font-weight: bold;");
        return vbox;
    }

    private ComboBox<LocalTime> createTimeComboBox() {
        ComboBox<LocalTime> timeComboBox = new ComboBox<>();
        timeComboBox.setConverter(new StringConverter<LocalTime>() {
            @Override
            public String toString(LocalTime time) {
                return time != null ? time.format(timeFormatter) : "";
            }

            @Override
            public LocalTime fromString(String string) {
                return LocalTime.parse(string, timeFormatter);
            }
        });

        for (int i = 0; i < 24; i++) {
            timeComboBox.getItems().add(LocalTime.of(i, 0));
            timeComboBox.getItems().add(LocalTime.of(i, 30));
        }

        return timeComboBox;
    }

    private VBox createListView() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        Button refreshButton = new Button("Refresh List");
        refreshButton.setOnAction(e -> updateReservationList());

        Button deleteButton = new Button("Delete Selected Reservation");
        deleteButton.setOnAction(e -> {
            String selectedItem = reservationListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                int id = Integer.parseInt(selectedItem.split(",")[0]);
                service.deleteReservation(id);
                updateReservationList();
            } else {
                showAlert("Error", "No reservation selected.");
            }
        });

        Label header = new Label("ID-Guest Name-Room Type-Check-InDateTime-Check-OutDateTime");
        header.setStyle("-fx-font-weight: bold;");

        reservationListView.setPrefHeight(300);
        updateReservationList();

        vbox.getChildren().addAll(header, refreshButton, deleteButton, reservationListView);

        return vbox;
    }

    private void updateReservationList() {
        reservationListView.getItems().clear();
        for (Reservation reservation : service.getAllReservations()) {
            reservationListView.getItems().add(reservation.toString());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
