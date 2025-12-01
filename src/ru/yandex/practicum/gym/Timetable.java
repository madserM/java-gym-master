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

    Map<DayOfWeek, List<TrainingSession>> timetable = new TreeMap<>();

    public void addNewTrainingSession(DayOfWeek dayOfWeek, TrainingSession trainingSession) {
        if (!timetable.containsKey(dayOfWeek)) {
            List<TrainingSession> sessions = new ArrayList<>();
            sessions.add(trainingSession); // Добавляем тренировку в список
            timetable.put(dayOfWeek, sessions); // Помещаем список в карту
            sessions.sort(trainingSessionComparator);
        } else {
            timetable.get(dayOfWeek).add(trainingSession);
            timetable.get(dayOfWeek).sort(trainingSessionComparator);
        }
    }

    Comparator<TrainingSession> trainingSessionComparator = new Comparator<TrainingSession>() {
        @Override
        public int compare(TrainingSession o1, TrainingSession o2) {
            // Сравниваем время начала тренировок
            return o1.getTimeOfDay().compareTo(o2.getTimeOfDay());
        }
    };

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        List<TrainingSession> sessions = timetable.get(dayOfWeek);
        if (sessions == null) {
            return new ArrayList<>();
        }
        return sessions;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        List<TrainingSession> sessions = timetable.get(dayOfWeek);
        if (sessions == null) {
            return new ArrayList<>();
        }

        List<TrainingSession> filteredSessions = new ArrayList<>();
        for (TrainingSession session : sessions) {
            if (session.getTimeOfDay().equals(timeOfDay)) {
                filteredSessions.add(session);
            }
        }
        return filteredSessions;
    }

    public HashMap<Coach, Integer> getCountByCoaches() {
        HashMap<Coach, Integer> coachesAndTrainings = new HashMap<>();
        for (List<TrainingSession> sessions : timetable.values()) {
            for (TrainingSession trainingSession : sessions) {
                Coach coach = trainingSession.getCoach();
                coachesAndTrainings.put(coach, coachesAndTrainings.getOrDefault(coach, 0) + 1);
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
