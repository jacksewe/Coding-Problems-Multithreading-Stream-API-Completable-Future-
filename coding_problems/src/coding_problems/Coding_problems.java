package coding_problems;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * List of employees(id, name, age) 1. write down all questions write a function
 * which takes list of employees as input remove duplicate 2. create a function
 * use stream api, get employees whose age larger than 40 3. create a function
 * use one stream api to split list into 2 groups based on age
 * List<List<Employee>> , [[employees' age < 40], [employees' age >= 40]] 4.
 * create a class + function input 3 list of Integer, use multi-threading to sum
 * all values from 3 list and return int result use CompletableFuture 5. create
 * a class + function create two threads and print even + odd number 1 : Thread1
 * 2 : Thread2 3 : Thread1 4 : Thread2 ....
 *
 * deadline: tomorrow morning 10:00 am cdt upload to github
 */
public class Coding_problems {
    public static void main(String args[]) {
        Employee john = new Employee(1, "John", 12);
        Employee heather = new Employee(2, "Heather", 42);
        Employee anna = new Employee(3, "Anna", 41);

        List<Employee> employees = new ArrayList<>();
        employees.add(john);
        employees.add(john);
        employees.add(heather);
        employees.add(anna);

        //No duplicates
        employees = Employee.removeDuplicate(employees);
        for (Employee e : employees) {
            System.out.print(e.id + " ");
        }
        System.out.println();

        //More than 40 years old
        List<Employee> moreThanForty = Employee.moreThanForty(employees);
        for (Employee e : moreThanForty) {
            System.out.print(e.age + " ");
        }
        System.out.println();

        //Split into group
        List<List<Employee>> employeeGroups = Employee
                .splitIntoGroups(employees);
        for (List<Employee> group : employeeGroups) {
            for (Employee e : group) {
                System.out.print(e.age + " ");
            }
            System.out.print("    ");
        }
        System.out.println();

        //Sum of three list of integers
        List<Integer> listOne = new ArrayList<>();
        listOne.add(1);
        listOne.add(1);
        List<Integer> listTwo = new ArrayList<>();
        listTwo.add(1);
        listTwo.add(1);
        List<Integer> listThree = new ArrayList<>();
        listThree.add(1);
        listThree.add(1);
        System.out
                .println(AdditionSolver.sumLists(listOne, listTwo, listThree));

        //Print numbers using two threads
        NumberPrinter np = new NumberPrinter();
        np.printNumbers();
    }
}

class Employee {
    int id;
    String name;
    int age;

    Employee(int theirId, String theirName, int theirAge) {
        this.id = theirId;
        this.name = theirName;
        this.age = theirAge;
    }

    static List<Employee> removeDuplicate(List<Employee> employees) {
        List<Integer> seen = new ArrayList<>();
        List<Employee> answer = new ArrayList<>();
        for (Employee e : employees) {
            if (!seen.contains(e.id)) {
                seen.add(e.id);
                answer.add(e);
            }
        }
        return answer;
    }

    static List<Employee> moreThanForty(List<Employee> employees) {
        return employees.stream().filter(i -> i.age > 40)
                .collect(Collectors.toList());
    }

    static List<List<Employee>> splitIntoGroups(List<Employee> employees) {
        List<Employee> moreThanForty = moreThanForty(employees);
        List<Employee> fortyOrLess = employees.stream().filter(i -> i.age <= 40)
                .collect(Collectors.toList());
        List<List<Employee>> employeeGroups = new ArrayList<>();
        employeeGroups.add(moreThanForty);
        employeeGroups.add(fortyOrLess);
        return employeeGroups;
    }
}

class AdditionSolver {
    static int sumLists(List<Integer> listOne, List<Integer> listTwo,
            List<Integer> listThree) {
        CompletableFuture<Integer> listOneFuture = CompletableFuture
                .supplyAsync(() -> listOne.stream().mapToInt(Integer::intValue)
                        .sum());
        CompletableFuture<Integer> listTwoFuture = CompletableFuture
                .supplyAsync(() -> listTwo.stream().mapToInt(Integer::intValue)
                        .sum());
        CompletableFuture<Integer> listThreeFuture = CompletableFuture
                .supplyAsync(() -> listThree.stream()
                        .mapToInt(Integer::intValue).sum());
        return Stream.of(listOneFuture, listTwoFuture, listThreeFuture)
                .map(CompletableFuture::join)
                .collect(Collectors.summingInt(Integer::intValue));
    }
}

class NumberPrinter {
    int x = 1;
    int max = 1;

    void printNumbers() {
        Thread threadOne = new Thread(() -> {
            while (this.x <= 5) {
                if (this.x % 2 == 0) {
                    System.out.println(this.x + " : ThreadOne");
                    this.x++;
                }
            }
        });
        Thread threadTwo = new Thread(() -> {
            while (this.x <= 5) {
                if (this.x % 2 != 0) {
                    System.out.println(this.x + " : ThreadTwo");
                    this.x++;
                }
            }
        });
        threadOne.start();
        threadTwo.start();
    }
}