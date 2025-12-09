package ru.yandex.practicum.gym;

import java.util.*;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {
    private Coach coach;
    private int count;

    public CounterOfTrainings(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getTrainingCount() {
        return count;
    }

    @Override
    public int compareTo(CounterOfTrainings other) {
        // Сортировка по убыванию количества тренировок
        return Integer.compare(other.count, this.count);
    }
}
