package com.example.sw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private boolean buttonClicked = false; // переменная-флаг для проверки была ли нажата кнопка, сделано для избежания возможности двух быстрых нажатий кнопок

    private Button gridSizeButton;
    private int gridSize = 6; // Начальное значение размера сетки (4x4)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridSizeButton = findViewById(R.id.difficulty); // Найти кнопку по идентификатору
    }

    public void onGameScreen(View view) {
        if (!buttonClicked) {
            buttonClicked = true;
            Intent gameScreen = new Intent(this, BasicGame.class);
            gameScreen.putExtra("GRID_SIZE", gridSize); // Передаем размер сетки в активность игры
            startActivity(gameScreen);
        }
    }

    // Метод для изменения размера сетки и текста кнопки
    public void changeGridSize(View view) {
        if (gridSize == 4) {
            gridSize = 5;
            gridSizeButton.setText("5x4");
        } else if (gridSize == 5) {
            gridSize = 6;
            gridSizeButton.setText("6x4");
        } else {
            gridSize = 4;
            gridSizeButton.setText("4x4");
        }
    }
    public void exitGame(View view) {
       finish();
    }


    @Override
    protected void onResume() {//повторный запуск мелодии, после возвращения в игру
        super.onResume();

        buttonClicked = false; // Сбрасываем флаг при возвращении к активности
    }
}
