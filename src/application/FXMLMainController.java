package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLMainController {

	private static ObservableList<Person> contacts;
	HandleFile lf;

	@FXML private TableView<Person> contactsTable;

	@FXML private Button deleteContact;
	@FXML private Button newContact;
	@FXML private Button editContact;

	@FXML private TableColumn<Person, String> name;
	@FXML private TableColumn<Person, String> surname;
	@FXML private TableColumn<Person, String> phone;

	@FXML
	private void initialize() throws FileNotFoundException 
	{	
		lf = new HandleFile();
		contacts = lf.loadContacts();

		if ( contacts.size() > 0 ) {
			deleteContact.setDisable(false);
			editContact.setDisable(false);
		}

		name.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		surname.setCellValueFactory(new PropertyValueFactory<Person, String>("surname"));
		phone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));

		contactsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		contactsTable.setItems(contacts);

	}


	@FXML
	private void addContact() throws IOException {
		loadEditorContacts();
		if ( contacts.size() > 0 ) {
			deleteContact.setDisable(false);
			editContact.setDisable(false);
		}
	}

	@FXML
	private void deleteContact() throws IOException {
		Person selectedPerson = contactsTable.getSelectionModel().getSelectedItem();

		if ( selectedPerson == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setContentText("Select a contact to remove it.");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText("Confirmation Dialog");
			alert.setContentText("Are you sure to delete " + selectedPerson.getName() + " " + selectedPerson.getSurname() + " from contacts?");
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			    contacts.remove(selectedPerson);
			    lf.saveContacts(contacts);
			    if ( contacts.size() == 0 ) {
					deleteContact.setDisable(true);
					editContact.setDisable(true);
				}
			}
		}
	}

	@FXML
	private void editContact() throws IOException {
		Person selectedPerson = contactsTable.getSelectionModel().getSelectedItem();

		if ( selectedPerson == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setContentText("Select a contact to edit it.");
			alert.showAndWait();
		} else {
			loadEditorContacts(selectedPerson);
		}
	}



	private void loadEditorContacts() throws IOException {

		FXMLLoader editorContact = new FXMLLoader(getClass().getResource("FXMLEditorContact.fxml"));

		editorContact.setController(new FXMLEditorContactController());

		Pane pane = editorContact.load();

		Scene scene = new Scene(pane);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Editor Contact");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();

	}

	private void loadEditorContacts(Person person) throws IOException {

		FXMLLoader editorContact = new FXMLLoader(getClass().getResource("FXMLEditorContact.fxml"));

		editorContact.setController(new FXMLEditorContactController(person));

		Pane pane = editorContact.load();

		Scene scene = new Scene(pane);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Editor Contact");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();

	}
	
	
	public static ObservableList<Person> getContacts() {
		return contacts;
	}

}