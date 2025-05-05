package com.vaultwise.frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    private static String jwtToken;
    private static long userId;

    public static String getJwtToken() {
        return jwtToken;
    }

    public static long getUserId() {
        return userId;
    }

    public void onLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            URL url = new URL("http://localhost:8080/api/authenticate");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInput = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password);
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes("utf-8"));
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                JSONObject json = new JSONObject(response.toString());
                jwtToken = json.getString("token");
                userId = json.getLong("userId");

                showAlert(Alert.AlertType.INFORMATION, "Login successful!");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
                Scene dashboardScene = new Scene(loader.load());

                DashboardController controller = loader.getController();
                controller.setUsername(username);
                controller.setUserId(userId);

                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(dashboardScene);
                stage.setTitle("VaultWise Dashboard");

            } else {
                showAlert(Alert.AlertType.ERROR, "Invalid login!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Login failed: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("VaultWise Login");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
