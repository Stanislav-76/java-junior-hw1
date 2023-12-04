package ru.gb.lesson1.hw;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Homework {

    /**
     * 0.1. Посмотреть разные статьи на Хабр.ру про Stream API
     * 0.2. Посмотреть видеоролики на YouTube.com Тагира Валеева про Stream API
     * <p>
     * 1. Создать список из 1_000 рандомных чисел от 1 до 1_000_000
     * 1.1 Найти максимальное
     * 2.2 Все числа, большие, чем 500_000, умножить на 5, отнять от них 150 и просуммировать
     * 2.3 Найти количество чисел, квадрат которых меньше, чем 100_000
     * <p>
     * 2. Создать класс Employee (Сотрудник) с полями: String name, int age, double salary, String department
     * 2.1 Создать список из 10-20 сотрудников
     * 2.2 Вывести список всех различных отделов (department) по списку сотрудников
     * 2.3 Всем сотрудникам, чья зарплата меньше 10_000, повысить зарплату на 20%
     * 2.4 * Из списка сотрудников с помощью стрима создать Map<String, List<Employee>> с отделами и сотрудниками внутри отдела
     * 2.5 * Из списока сорудников с помощью стрима создать Map<String, Double> с отделами и средней зарплатой внутри отдела
     */

    public static void main(String[] args) {
        List<Integer> list = Stream.generate(() -> ThreadLocalRandom.current().nextInt(1_000_000))
                .limit(1000)
                .toList();
        System.out.println(list.stream().max((i1, i2) -> i1 - i2));
        System.out.println(list.stream().reduce(Integer::max));

        System.out.println(list.stream().filter(it -> it > 500_000).map(it -> it * 5 - 150).reduce(0, (it1, it2) -> it1 + it2));
        System.out.println(list.stream().filter(it -> it > 500_000).map(it -> it * 5 - 150).reduce(0, Integer::sum));
        System.out.println(list.stream().filter(it -> it > 500_000).map(it -> it * 5 - 150).collect(Collectors.summingInt(Integer::intValue)));
        System.out.println(list.stream().filter(it -> it > 500_000).map(it -> it * 5 - 150).mapToInt(it -> it.intValue()).sum());

        System.out.println(list.stream().filter(it -> Math.pow(it, 2) < 100_000).count());

        List<Employee> employees = List.of(
                new Employee("Алексей", 30, 50000, "Отдел frontend разаработки"),
                new Employee("Сергей", 25, 55000, "Отдел frontend разаработки"),
                new Employee("Александр", 34, 40000, "Отдел frontend разаработки"),
                new Employee("Виктор", 40, 50000, "Отдел backend разаработки"),
                new Employee("Константин", 24, 8000, "Отдел backend разаработки"),
                new Employee("Алексей", 41, 100000, "Отдел backend разаработки"),
                new Employee("Анна", 32, 9000, "Отдел аналитики"),
                new Employee("Мария", 23, 70000, "Отдел аналитики"),
                new Employee("Олег", 30, 50000, "Отдел аналитики")
        );

        System.out.println(employees.stream().collect(Collectors.groupingBy(Employee::getDepartment)).keySet());
        employees.stream().filter(it -> it.getSalary() < 10_000).forEach(it -> it.setSalary(it.getSalary() * 1.2));
        System.out.println(employees);
        Map<String, List<Employee>> map1 = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment));
        System.out.println(map1);
        Map<String, Double> map2 = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
        System.out.println(map2);


    }

    static class Employee {
        String name;
        int age;

        double salary;

        public String getDepartment() {
            return department;
        }

        String department;

        public Employee(String name, int age, double salary, String department) {
            this.name = name;
            this.age = age;
            this.salary = salary;
            this.department = department;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    ", department='" + department + '\'' +
                    "}\n";
        }
    }

}
