package com.vaultwise.frontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.format.DateTimeFormatter;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private TableView<Expense> expenseTable;
    @FXML private TableColumn<Expense, String> categoryCol;
    @FXML private TableColumn<Expense, Double> amountCol;
    @FXML private TableColumn<Expense, String> dateCol;
    @FXML private TextField categoryField;
    @FXML private TextField amountField;
    @FXML private DatePicker datePicker;

    private final ObservableList<Expense> expenses = FXCollections.observableArrayList();
    private long userId;

    public void setUsername(String username) {
        welcomeLabel.setText("Welcome, " + username + "!");
    }

    public void setUserId(long userId) {
        this.userId = userId;
        fetchExpenses();
    }

    public void fetchExpenses() {
        try {
            URL url = new URL("http://localhost:8080/api/" + userId + "/expenses");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + LoginController.getJwtToken());

            if (conn.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) sb.append(line);
                System.out.println("== RAW RESPONSE START ==");
                System.out.println(sb.toString()); // 👈 This prints what backend sent
                System.out.println("== RAW RESPONSE END ==");

                JSONArray arr = new JSONArray(sb.toString());
                expenses.clear();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    expenses.add(new Expense(
                        obj.getString("category"),
                        obj.getDouble("amount"),
                        obj.getString("date")
                    ));
                }

                categoryCol.setCellValueFactory(data -> data.getValue().categoryProperty());
                amountCol.setCellValueFactory(data -> data.getValue().amountProperty().asObject());
                dateCol.setCellValueFactory(data -> data.getValue().dateProperty());

                expenseTable.setItems(expenses);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onAddExpense() {
        try {
            String category = categoryField.getText();
            String amountStr = amountField.getText();
            String date = datePicker.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE);

            JSONObject payload = new JSONObject();
            payload.put("category", category);
            payload.put("amount", Double.parseDouble(amountStr));
            payload.put("date", date);

            URL url = new URL("http://localhost:8080/api/" + userId + "/expense");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + LoginController.getJwtToken());
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.toString().getBytes());
            }

            if (conn.getResponseCode() == 200 || conn.getResponseCode() == 201) {
                fetchExpenses();
                categoryField.clear();
                amountField.clear();
                datePicker.setValue(null);
            } else {
                showError("Failed to add expense. Status: " + conn.getResponseCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Error: " + e.getMessage());
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("VaultWise");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
