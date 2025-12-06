package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(DayOfWeek.MONDAY, new TimeOfDay(13, 0), singleTrainingSession);

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, mondaySessions.size());

        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertEquals(0, tuesdaySessions.size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(DayOfWeek.THURSDAY, new TimeOfDay(20, 0), thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(DayOfWeek.MONDAY, new TimeOfDay(13, 0), mondayChildTrainingSession);
        timetable.addNewTrainingSession(DayOfWeek.THURSDAY, new TimeOfDay(13, 0), thursdayChildTrainingSession);
        timetable.addNewTrainingSession(DayOfWeek.SATURDAY, new TimeOfDay(10, 0), saturdayChildTrainingSession);


        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, mondaySessions.size());

        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(new TimeOfDay(13, 0), thursdaySessions.getFirst().getTimeOfDay());
        Assertions.assertEquals(new TimeOfDay(20, 0), thursdaySessions.getLast().getTimeOfDay());

        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertEquals(0, tuesdaySessions.size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(DayOfWeek.MONDAY, new TimeOfDay(13, 0), singleTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)).size());

        Assertions.assertEquals(0, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0)).size());
    }

    @Test
    void shouldBe4TrainingsForCoach() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(DayOfWeek.THURSDAY, new TimeOfDay(20, 0),thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(DayOfWeek.MONDAY, new TimeOfDay(13, 0), mondayChildTrainingSession);
        timetable.addNewTrainingSession(DayOfWeek.THURSDAY, new TimeOfDay(13, 0), thursdayChildTrainingSession);
        timetable.addNewTrainingSession(DayOfWeek.SATURDAY, new TimeOfDay(10, 0), saturdayChildTrainingSession);

        HashMap<Coach, Integer> coachesAndTrainings = timetable.getCountByCoaches();

        Assertions.assertEquals(4, coachesAndTrainings.get(coach));

    }

    @Test
    void shouldBe0TrainingsForCoach() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Афанасьев", "Георгий", "Дмитриевич");
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(DayOfWeek.THURSDAY, new TimeOfDay(20, 0), thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(DayOfWeek.MONDAY, new TimeOfDay(13, 0), mondayChildTrainingSession);
        timetable.addNewTrainingSession(DayOfWeek.THURSDAY, new TimeOfDay(13, 0), thursdayChildTrainingSession);
        timetable.addNewTrainingSession(DayOfWeek.SATURDAY, new TimeOfDay(10, 0), saturdayChildTrainingSession);

        HashMap<Coach, Integer> coachesAndTrainings = timetable.getCountByCoaches();

        Assertions.assertNull(coachesAndTrainings.get(coach2));
    }

    @Test
    void shouldGetCorrectOrderByCountOfTrainings() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Афанасьев", "Георгий", "Дмитриевич");
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(DayOfWeek.THURSDAY, new TimeOfDay(20, 0), thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(DayOfWeek.MONDAY, new TimeOfDay(13, 0), mondayChildTrainingSession);
        timetable.addNewTrainingSession(DayOfWeek.THURSDAY, new TimeOfDay(13, 0), thursdayChildTrainingSession);
        timetable.addNewTrainingSession(DayOfWeek.SATURDAY, new TimeOfDay(10, 0), saturdayChildTrainingSession);

        mondayChildTrainingSession = new TrainingSession(groupChild, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(15, 0));
        thursdayChildTrainingSession = new TrainingSession(groupChild, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(DayOfWeek.MONDAY,  new TimeOfDay(15, 0), mondayChildTrainingSession);
        timetable.addNewTrainingSession(DayOfWeek.THURSDAY,  new TimeOfDay(15, 0), thursdayChildTrainingSession);

        Iterator<Map.Entry<Coach, Integer>> iterator = timetable.getCountByCoaches().entrySet().iterator();
        Map.Entry<Coach, Integer> firstEntry = iterator.next(); // Получаем первый элемент
        Map.Entry<Coach, Integer> secondEntry = iterator.next(); // Получаем второй элемент
        Integer firstCount = firstEntry.getValue();
        Integer secondCount = secondEntry.getValue();
        Assertions.assertTrue(firstCount>secondCount);
    }

    @Test
    void shouldBe2TrainingsAtTheSameTime() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Афанасьев", "Георгий", "Дмитриевич");
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(DayOfWeek.THURSDAY, new TimeOfDay(20, 0), thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(DayOfWeek.THURSDAY, new TimeOfDay(20, 0), thursdayChildTrainingSession);

        Assertions.assertEquals(2, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.THURSDAY,
                new TimeOfDay(20, 0)).size());
    }
}
