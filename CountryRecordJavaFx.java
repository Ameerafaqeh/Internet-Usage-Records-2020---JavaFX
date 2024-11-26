import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CountryRecordJavaFx extends Application {

	private CountryRecordArray countryArray = new CountryRecordArray();
	private ListView<String> recordListView = new ListView<>();
	private TextField nameTextField = new TextField();
	private TextField percentageTextField = new TextField();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Country Records");

		Button chooseFileButton = new Button("File Chooser");
		chooseFileButton.setStyle("-fx-background-color:#008000; " + "-fx-text-fill: black; "
				+ "-fx-font-weight: bold; " + "-fx-font-size: 14px;");
		chooseFileButton.setOnAction(e -> chooseFile(primaryStage));

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		Label countryName = new Label("Country Name");
		countryName.setStyle("-fx-text-fill: #008000; -fx-font-weight: bold;");
		grid.add(countryName, 0, 0);
		grid.add(nameTextField, 1, 0);
		Label perc = new Label("Percentage of Internet users");
		perc.setStyle("-fx-text-fill: #008000; -fx-font-weight: bold;");

		grid.add(perc, 0, 1);
		grid.add(percentageTextField, 1, 1);

		HBox buttonsBox = new HBox(10);
		Button addButton = new Button("Add");

		addButton.setStyle("-fx-background-color:#008000; " + "-fx-text-fill: black; " + "-fx-font-weight: bold; "
				+ "-fx-font-size: 14px;");
		addButton.setOnAction(e -> addRecord());
		Button deleteButton = new Button("Delete");
		deleteButton.setStyle("-fx-background-color:#008000; " + "-fx-text-fill: black; " + "-fx-font-weight: bold; "
				+ "-fx-font-size: 14px;");
		deleteButton.setOnAction(e -> deleteRecord());
		Button searchButton = new Button("search");
		searchButton.setStyle("-fx-background-color:#008000; " + "-fx-text-fill: black; " + "-fx-font-weight: bold; "
				+ "-fx-font-size: 14px;");
		searchButton.setOnAction(e -> searchByNameOrPercentage());
		buttonsBox.getChildren().addAll(addButton, deleteButton, searchButton);

		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(chooseFileButton, recordListView, grid, buttonsBox);

		Scene scene = new Scene(vbox, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.getScene().getRoot().setStyle("-fx-background-color: #111111;");

	}

	private void chooseFile(Stage primaryStage) {
		//Method: Select a file from the device and
		//display it on the screen
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
		File file = fileChooser.showOpenDialog(primaryStage);

		if (file != null) {
			countryArray = new CountryRecordArray();

			countryArray.readDataFromFile(file.getAbsolutePath());
			updateListView();
			recordListView.setStyle("-fx-control-inner-background: #008000;");

		}
	}

	private void addRecord() {//Method for adding new information to the record
		String name = nameTextField.getText();
		String percentageStr = percentageTextField.getText();
		if (!name.isEmpty() && !percentageStr.isEmpty()) {
			double percentage = Double.parseDouble(percentageStr);
			CountryRecord record = new CountryRecord(name, percentage);
			countryArray.addRecord(record);
			updateListView();
			nameTextField.clear();
			percentageTextField.clear();
		}
	}

	private void deleteRecord() {//Deletion method through the country name
		String name = nameTextField.getText();
		if (!name.isEmpty()) {
			countryArray.deleteRecordByName(name);
			updateListView();
			nameTextField.clear();
		}
	}

	private void searchByNameOrPercentage() {
		//The search method is by using either the country 
		//name or the relative name
		String searchText = nameTextField.getText();
		String percentageText = percentageTextField.getText();
		if (!searchText.isEmpty() || !percentageText.isEmpty()) {
			if (!searchText.isEmpty() && !percentageText.isEmpty()) {

				double percentage = Double.parseDouble(percentageText);
				CountryRecord[] result = countryArray.searchByNameAndPercentage(searchText, percentage);
				recordListView.getItems().clear();
				for (CountryRecord record : result) {
					recordListView.getItems().add(record.toString());
				}
			} else if (!searchText.isEmpty()) {

				CountryRecord[] result = countryArray.searchByName(searchText);
				recordListView.getItems().clear();
				for (CountryRecord record : result) {
					recordListView.getItems().add(record.toString());
				}
			} else if (!percentageText.isEmpty()) {

				double percentage = Double.parseDouble(percentageText);
				CountryRecord[] result = countryArray.searchByPercentage(percentage);
				recordListView.getItems().clear();
				for (CountryRecord record : result) {
					recordListView.getItems().add(record.toString());
				}
			}
		}
	}

	private void updateListView() {
		//A method for updating the registry when performing
		//any of the previous operations
		recordListView.getItems().clear();
		for (CountryRecord record : countryArray.getRecords()) {
			recordListView.getItems().add(record.toString());
		}
	}

}
