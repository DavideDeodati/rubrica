package application;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;


public class HandleFile {
	
	private static ObservableList<Person> contacts = FXCollections.observableArrayList();
	
	public ObservableList<Person> loadContacts() throws FileNotFoundException  {

		File contactsPath = new File("contacts.txt");
		
		if (contactsPath.exists()) {
			Scanner scanner = new Scanner(contactsPath);
			
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] data = line.split(";");
				contacts.add(new Person( data[0], data[1], data[2], data[3], Integer.parseInt(data[4]) ));
			}
			scanner.close();
		}
		
		return contacts;
	}
	
	public void saveContacts(ObservableList<Person> contacts) throws FileNotFoundException  {
		String contactsPath = "contacts.txt";
	
		try(PrintStream ps = new PrintStream(contactsPath)){
			
			for (Person person : contacts) {
				ps.print(person.getName());
				ps.print(";");
				ps.print(person.getSurname());
				ps.print(";");
				ps.print(person.getAddress());
				ps.print(";");
				ps.print(person.getPhone());
				ps.print(";");
				ps.println(person.getAge());
				ps.flush();
			}

            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}
	
	
	public static ObservableList<Person> getContacts() {
		return contacts;
	}
}
