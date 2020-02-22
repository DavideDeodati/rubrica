package application;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.io.FileNotFoundException;

import application.HandleFile;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class FXMLEditorContactController {

	@FXML Button createContactButton;
	@FXML Button cancelContactButton;

	@FXML TextField nameField;
	@FXML TextField surnameField;
	@FXML TextField addressField;
	@FXML TextField phoneField;
	@FXML TextField ageField;

	ObservableList<Person> contacts = FXMLMainController.getContacts();

	Person person;

	public FXMLEditorContactController() {
	}

	public FXMLEditorContactController(Person person) {
		this.person = person;
	}

	/**
	 * @Button cancelContactButton
	 * Clear text fields and reset combo box
	 */
	@FXML
	private void cancelContact() {
		Stage stage = (Stage) cancelContactButton.getScene().getWindow();
		stage.close();
	}


	/**
	 * Clear text fields 
	 */
	private void cleanFields() {
		nameField.setText("");
		surnameField.setText("");
		addressField.setText("");
		phoneField.setText("");
		ageField.setText("");
	}

	@FXML
	private void initialize()  
	{	
		if (person != null) {
			populateFields();
		}
	}

	/**
	 * Fill text fields with person's field
	 */
	@FXML
	private void populateFields()
	{	
		nameField.setText(person.getName());
		surnameField.setText(person.getSurname());
		addressField.setText(person.getAddress());
		phoneField.setText(person.getPhone());
		ageField.setText(String.valueOf(person.getAge()));
	}



	@FXML
	private void saveContact() throws FileNotFoundException {
		HandleFile hf = new HandleFile();

		if (nameField.getText().isEmpty() ||
				surnameField.getText().isEmpty() ||
				addressField.getText().isEmpty() ||
				phoneField.getText().isEmpty() ||
				ageField.getText().isEmpty()) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setContentText("Missing informations contacts!");
			alert.showAndWait();
			return;
		}

		try {
			//Make Person from Fields
			Person p = new Person(
					nameField.getText(), 
					surnameField.getText(), 
					addressField.getText(), 
					phoneField.getText(), 
					Integer.parseInt((ageField.getText()))
					);

			//If person exist update it else add new person to contacts 
			int indexPerson = contacts.indexOf(person);
			if (indexPerson != -1) {
				contacts.set(indexPerson, p);
			} else {
				contacts.add(p);
			}

			cleanFields();

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setContentText("Age field have to be an integer!");
			alert.showAndWait();
			return;
		}	

		hf.saveContacts(contacts);
		Stage stage = (Stage) cancelContactButton.getScene().getWindow();
		stage.close();
	}

}