package com.example.aplikacjajson;

import android.os.Bundle;// wszystkie importy są normalnie w android studio nie trzeba nic dodawać do dependecies
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextSurname = findViewById(R.id.editTextSurname);
        TextView textViewDisplay = findViewById(R.id.textViewDisplay);

        // Przycisk do zapisywania do pliku
        findViewById(R.id.buttonSave).setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String surname = editTextSurname.getText().toString();
            saveToJson(name, surname); //funkcja zapisu do json
        });

        // Przycisk do odczytu z tego pliku
        findViewById(R.id.buttonLoad).setOnClickListener(v -> {
            String result = loadFromJson(); // wywołanie odczytywania pliku
            textViewDisplay.setText(result); // Wyświetlenie odczytanych danych z pliku na ekranie
        });
    }

    // Funkcja do zapisu w json
    private void saveToJson(String name, String surname) {
        JSONObject jsonObject = new JSONObject(); // Tu tworzę obiek JSON
        try {
            jsonObject.put("name", name); // Do tego obiektu dodaję imię
            jsonObject.put("surname", surname); // i nazwisko

            // Zapis
            try (FileOutputStream fos = openFileOutput("data.json", MODE_PRIVATE)) {
                fos.write(jsonObject.toString().getBytes()); // Ten obiekt trzeba przekonwertować do zapisu w pliku
            } catch (IOException e) {
                e.printStackTrace(); // errory wejścia i wyjścia
            }
        } catch (JSONException e) {
            e.printStackTrace(); // Tu obsługuję błędy związane z konwersją tego obiektu json
        }
    }

    // Odczyt
    private String loadFromJson() {
        StringBuilder jsonString = new StringBuilder(); // Trzeba stworzyć obiekt do odczytu danych
        try (FileInputStream fis = openFileInput("data.json")) {
            int character;
            // Odczyt tylko znak po znaku jak coś
            while ((character = fis.read()) != -1) {
                jsonString.append((char) character); // Dodaje do tego stringBuildera
            }

            // i konwertujemy w drugą stronę na obiekt JSON
            JSONObject jsonObject = new JSONObject(jsonString.toString());
            String name = jsonObject.getString("name"); // pobranie imienia z json
            String surname = jsonObject.getString("surname"); // i nazwiska

            return "Imię: " + name + ", Nazwisko: " + surname;
        } catch (IOException | JSONException e) {
            e.printStackTrace(); // obsługa błędów związanych z odczytem i parsowaniem
            return "Błąd podczas wczytywania danych."; // zwrotka komunikatu błedów
        }
    }
}
