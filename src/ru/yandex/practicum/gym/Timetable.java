package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {
    private TrainingSession trainingSession;
    private DayOfWeek dayOfWeek;

    public Timetable(DayOfWeek dayOfWeek, TrainingSession trainingSession) {
        this.dayOfWeek = dayOfWeek;
        this.trainingSession = trainingSession;
    }

    public Timetable() {
    }

    Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> timetable = new TreeMap<>();

    public void addNewTrainingSession(DayOfWeek dayOfWeek, TimeOfDay timeOfDay, TrainingSession trainingSession) {
        if (!timetable.containsKey(dayOfWeek)) {
            timetable.put(dayOfWeek, new TreeMap<>());
        }

        Map<TimeOfDay, List<TrainingSession>> dayTrainings = timetable.get(dayOfWeek);

        if (!dayTrainings.containsKey(timeOfDay)) {
            dayTrainings.put(timeOfDay, new ArrayList<>());
        }

        dayTrainings.get(timeOfDay).add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        Map<TimeOfDay, List<TrainingSession>> dayTrainings = timetable.get(dayOfWeek);
        if (dayTrainings == null) {
            return new ArrayList<>();
        }

        List<TrainingSession> sessions = new ArrayList<>();
        for (List<TrainingSession> trainingSessions : dayTrainings.values()) {
            sessions.addAll(trainingSessions);
        }
        return sessions;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        Map<TimeOfDay, List<TrainingSession>> sessions = timetable.get(dayOfWeek);

        List<TrainingSession> filteredSessions = sessions.getOrDefault(timeOfDay, new ArrayList<>());
        return filteredSessions;
    }

    public TreeSet<CounterOfTrainings> getCountByCoaches() {
        HashMap<Coach, Integer> coachesAndTrainings = new HashMap<>();

        for (Map<TimeOfDay, List<TrainingSession>> dayTrainings : timetable.values()) {
            for (List<TrainingSession> trainingSessions : dayTrainings.values()) {
                for (TrainingSession trainingSession : trainingSessions) {
                    Coach coach = trainingSession.getCoach();
                    coachesAndTrainings.compute(coach, (key, oldValue) -> (oldValue == null) ? 1 : oldValue + 1);
                }
            }
        }

        TreeSet<CounterOfTrainings> sortedCoaches = new TreeSet<>();
        for (Map.Entry<Coach, Integer> entry : coachesAndTrainings.entrySet()) {
           sortedCoaches.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }
        return sortedCoaches;
    }
}
