package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.DatabaseConnection;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class UserController {

	
	@FXML
	Button HomeButton;
	@FXML
	Button ResourcesButton;
	@FXML
	Button userLogOut;
	@FXML
	Pane UserAppointmentPane;
	@FXML
	TableView<ModelTable> UserAppointmentTable;
	@FXML
	TableColumn<ModelTable, String> UserPatientColumn;
	@FXML
	TableColumn<ModelTable, String> UserModalityColumn;
	@FXML
	TableColumn<ModelTable, String> UserDateTimeColumn;
	@FXML
	TableColumn<ModelTable, String> UserRadiologistColumn;
	@FXML
	TableColumn<ModelTable, String> UserTechnicianColumn;
	ObservableList<ModelTable> appointments = FXCollections.observableArrayList();
	
	
	
	public void userLogOut(ActionEvent event) throws IOException {
		
		Main m = new Main();
		
		m.changeScene("../Views/Login.fxml");
	}
	
	public void HomeButton(ActionEvent event) throws IOException{
		
		Main m = new Main();
		m.changeScene("../Views/User.fxml");
	}
	
	public void ResourcesButton(ActionEvent event) throws IOException{
		
		Main m = new Main();
		m.changeScene("../Views/UserResources.fxml");
	}
	

	public void populateAppointments() {
		appointments.clear();
		int patient = 0;
		int modality = 0;
		int tech = 0;
		int radio = 0;
		String patientName = null;
		String modalityName = null;
		String techName = null;
		String radioName = null;
		
		
		try {
			Connection con = DatabaseConnection.getConnection();
			ResultSet rs = con.createStatement().executeQuery("select * from appointments");

			while (rs.next()) {
				patient = rs.getInt("patient");
				modality = rs.getInt("modality");
				tech = rs.getInt("technician");
				radio = rs.getInt("radiologist");
				ResultSet rs2 = con.createStatement().executeQuery("select * from patients where patient_id=" + patient);
				while(rs2.next()) {
					patientName = rs2.getString("first_name") + " " + rs2.getString("last_name");
				}
				rs2 = con.createStatement().executeQuery("select * from modalities where modality_id=" + modality);
				while(rs2.next()) {
					modalityName = rs2.getString("name");
				}
				rs2 = con.createStatement().executeQuery("select * from users where user_id=" + tech);
				while(rs2.next()) {
					techName = rs2.getString("full_name");
				}
				rs2 = con.createStatement().executeQuery("select * from users where user_id=" + radio);
				while(rs2.next()) {
					radioName = rs2.getString("full_name");
				}				
				
				appointments.add(new ModelTable(patient, 0, 0, patientName,
						modalityName, rs.getString("date_time"), techName, 
						radioName, null));
			}
		} 
		catch (SQLException e) {
			System.out.println("Error: Could not get appointment data.");
		}
		

	}
}

			





