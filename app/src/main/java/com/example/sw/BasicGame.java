package com.example.sw;


import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.InputType;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BasicGame extends AppCompatActivity {
    private List<ImageView> tilesList = new ArrayList<>();
    private List<Integer> imagesList = new ArrayList<>();
    private int clickNumber = 1;
    private int click1Value = 0;  // переменная для первого клика
    private int click2Value = 0;  // переменная для второго клика

    private int gridSize;

    private Chronometer chronometer;  // хронометр
    private boolean isFirstIconOpened = false;  // проверка на открытие первого тайла

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int gridSize = getIntent().getIntExtra("GRID_SIZE", 4);  // Значение по умолчанию 4x4
        int layoutResourceId;
        switch (gridSize) {
            case 4:
                layoutResourceId = R.layout.activity_basic_game_4;
                break;
            case 5:
                layoutResourceId = R.layout.activity_basic_game_5;
                break;
            case 6:
                layoutResourceId = R.layout.activity_basic_game;
                break;
            default:
                // По умолчанию используйте макет для 4x4
                layoutResourceId = R.layout.activity_basic_game;
                break;
        }
        setContentView(layoutResourceId);
        chronometer = findViewById(R.id.chronometer);
        // Получите значение размера сетки из Intent

        // Сохраните значение gridSize в переменной для дальнейшего использования
        this.gridSize = gridSize;

        // Инициализация ImageView
        initializeImageViews(gridSize);

        // Инициализация обработчиков событий для кнопок
        initializeClickListeners();

        // Инициализация изображений и начало игры
        initializeImagesAndStartGame(gridSize);
    }

    private void initializeImageViews(int gridSize) {
        int[] imageIds;

        switch (gridSize) {
            case 4:
                imageIds = new int[]{
                        R.id.sw1, R.id.sw2, R.id.sw3, R.id.sw4,
                        R.id.sw5, R.id.sw6, R.id.sw7, R.id.sw8,
                        R.id.sw9, R.id.sw10, R.id.sw11, R.id.sw12,
                        R.id.sw13, R.id.sw14, R.id.sw15, R.id.sw16
                };
                break;

            case 5:
                imageIds = new int[]{
                        R.id.sw1, R.id.sw2, R.id.sw3, R.id.sw4,
                        R.id.sw5, R.id.sw6, R.id.sw7, R.id.sw8,
                        R.id.sw9, R.id.sw10, R.id.sw11, R.id.sw12,
                        R.id.sw13, R.id.sw14, R.id.sw15, R.id.sw16,
                        R.id.sw17, R.id.sw18, R.id.sw19, R.id.sw20
                };
                break;

            case 6:
                imageIds = new int[]{
                        R.id.sw1, R.id.sw2, R.id.sw3, R.id.sw4,
                        R.id.sw5, R.id.sw6, R.id.sw7, R.id.sw8,
                        R.id.sw9, R.id.sw10, R.id.sw11, R.id.sw12,
                        R.id.sw13, R.id.sw14, R.id.sw15, R.id.sw16,
                        R.id.sw17, R.id.sw18, R.id.sw19, R.id.sw20,
                        R.id.sw21, R.id.sw22, R.id.sw23, R.id.sw24
                };
                break;

            default:
                imageIds = new int[0];
                break;
        }

        for (int id : imageIds) {
            ImageView imageView = findViewById(id);
            tilesList.add(imageView);
        }
    }




    private void initializeClickListeners() {
        for (int i = 0; i < tilesList.size(); i++) {
            final int finalI = i;
            tilesList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClick(tilesList.get(finalI), finalI);
                }
            });
        }
    }


    private void initializeImagesAndStartGame(int gridSize) {
        int[] imageResources;

        switch (gridSize) {
            case 4:
                imageResources = new int[]{
                        R.drawable.sw1, R.drawable.sw2, R.drawable.sw3, R.drawable.sw4,
                        R.drawable.sw5, R.drawable.sw6, R.drawable.sw7, R.drawable.sw8
                };
                break;

            case 5:
                imageResources = new int[]{
                        R.drawable.sw1, R.drawable.sw2, R.drawable.sw3, R.drawable.sw4,
                        R.drawable.sw5, R.drawable.sw6, R.drawable.sw7, R.drawable.sw8,
                        R.drawable.sw9, R.drawable.sw10
                };
                break;

            case 6:
                imageResources = new int[]{
                        R.drawable.sw1, R.drawable.sw2, R.drawable.sw3, R.drawable.sw4,
                        R.drawable.sw5, R.drawable.sw6, R.drawable.sw7, R.drawable.sw8,
                        R.drawable.sw9, R.drawable.sw10, R.drawable.sw11, R.drawable.sw12
                };
                break;

            default:
                imageResources = new int[0];
                break;
        }

        // Дублирование изображений
        imagesList.clear();
        for (int i = 0; i < 2; i++) {
            for (int resId : imageResources) {
                imagesList.add(resId);
            }
        }

        // Перемешивание изображений
        Collections.shuffle(imagesList);

        // Включение всех тайлов
        for (ImageView imageView : tilesList) {
            imageView.setEnabled(true);
        }
    }

    private void buttonClick(ImageView image, int number) {
        if (clickNumber == 1) {
            image.setImageResource(imagesList.get(number));
            clickNumber = 2;
            click1Value = number + 1;

            if (!isFirstIconOpened) {
                // Если это первая открытая иконка, то запустить Chronometer
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                chronometer.setVisibility(View.VISIBLE);
                isFirstIconOpened = true;
            }

        } else if (clickNumber == 2 && click1Value != number + 1) {  // Добавьте это условие
            image.setImageResource(imagesList.get(number));
            clickNumber = 1;
            click2Value = number + 1;
            compareTiles();
        }
    }

    // уведомление по завершению игры
    private void showResult() {
        // конвертация времени
        long elapsedTimeMillis = SystemClock.elapsedRealtime() - chronometer.getBase();

        int hours = (int) (elapsedTimeMillis / 3600000);
        int minutes = (int) ((elapsedTimeMillis % 3600000) / 60000);
        int seconds = (int) ((elapsedTimeMillis % 60000) / 1000);

        String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);  // строка времени

        // Создаем AlertDialog с EditText для ввода имени пользователя
        final EditText inputName = new EditText(this);
        inputName.setInputType(InputType.TYPE_CLASS_TEXT);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Поздравляем!\nВы справились за: " + formattedTime + "\nХотите сыграть ещё раз?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        resetGame();
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        // Закрыть текущую активность и вернуться к предыдущей (если нужно)
                        finish();  // Закрыть текущую активность
                        Intent menuIntent = new Intent(BasicGame.this, MainActivity.class);
                        startActivity(menuIntent);  // Запустить активность меню
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void resetGame() {  // сброс всех нужных переменных и начало игры заново
        // Сброс всех переменных и состояний игры
        clickNumber = 1;
        click1Value = 0;
        click2Value = 0;
        isFirstIconOpened = false;

        // Отключение всех плиток
        for (ImageView imageView : tilesList) {
            imageView.setEnabled(false);
            imageView.setVisibility(View.VISIBLE);  // Сделать все плитки видимыми, если они были скрыты
            imageView.setImageResource(R.drawable.close);  // Сбросить изображения на закрытые
        }

        // Сброс хронометра
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());

        // Инициализация изображений и начало игры
        initializeImagesAndStartGame(gridSize);
    }

    private void compareTiles() {
        // Отключение всех тайл
        for (ImageView imageView : tilesList) {
            imageView.setEnabled(false);
        }

        if (imagesList.get(click1Value - 1).equals(imagesList.get(click2Value - 1))) {
            // Одинаковые изображения
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Удаление тайл
                    tilesList.get(click1Value - 1).setVisibility(View.INVISIBLE);
                    tilesList.get(click2Value - 1).setVisibility(View.INVISIBLE);

                    // Включение всех тайлов
                    for (ImageView imageView : tilesList) {
                        imageView.setEnabled(true);
                    }

                    // Проверка, остались ли еще видимые тайлы
                    boolean anyTilesVisible = false;
                    for (ImageView imageView : tilesList) {
                        if (imageView.getVisibility() == View.VISIBLE) {
                            anyTilesVisible = true;
                            break;
                        }
                    }

                    // Если не осталось видимых плиток, останавливаем хронометр
                    if (!anyTilesVisible) {
                        chronometer.stop();
                        showResult();
                    }
                }
            }, 500);
        } else {
            // Разные изображения
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Переворот тайлов
                    tilesList.get(click1Value - 1).setImageResource(R.drawable.close);
                    tilesList.get(click2Value - 1).setImageResource(R.drawable.close);

                    // Включение всех тайлов
                    for (ImageView imageView : tilesList) {
                        imageView.setEnabled(true);
                    }
                }
            }, 500);
        }
    }

}