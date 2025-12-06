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
            timetable.put(dayOfWeek, new HashMap<>());
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
        if (sessions == null) {
            return new ArrayList<>();
        }

        List<TrainingSession> filteredSessions = new ArrayList<>();
        for (List<TrainingSession> trainingSessions : sessions.values()) {
            for (TrainingSession session : trainingSessions) {
                if (session.getTimeOfDay().equals(timeOfDay)) {
                    filteredSessions.add(session);
                }
            }
        }
        return filteredSessions;
    }

    public HashMap<Coach, Integer> getCountByCoaches() {
        HashMap<Coach, Integer> coachesAndTrainings = new HashMap<>();

        for (Map<TimeOfDay, List<TrainingSession>> dayTrainings : timetable.values()) {
            for (List<TrainingSession> trainingSessions : dayTrainings.values()) {
                for (TrainingSession trainingSession : trainingSessions) {
                    Coach coach = trainingSession.getCoach();
                    coachesAndTrainings.compute(coach, (key, oldValue) -> (oldValue == null) ? 1 : oldValue + 1);
                }
            }
        }
        List<Map.Entry<Coach, Integer>> list = new ArrayList<>(coachesAndTrainings.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        HashMap<Coach, Integer> sortedCoachesAndTrainings = new LinkedHashMap<>();
        for (Map.Entry<Coach, Integer> entry : list) {
            sortedCoachesAndTrainings.put(entry.getKey(), entry.getValue());
        }
        return sortedCoachesAndTrainings;
    }
}
