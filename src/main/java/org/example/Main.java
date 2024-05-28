package org.example;

import org.example.configuration.ReadProperties;
import org.example.exception.DuplicateModelNameException;
import org.example.exception.NoSuchModelNameException;
import org.example.vehicle.Bike;
import org.example.vehicle.Car;
import org.example.vehicle.Vehicle;
import org.example.vehicle.factory.BikeFactory;
import org.example.vehicle.factory.CarFactory;
import org.example.vehicle.factory.VehicleFactory;
import org.example.vehicle.utils.StringAdapter;
import org.example.vehicle.utils.VehicleUtil;
import org.example.vehicle.utils.dao.ParserDao;
import org.example.vehicle.utils.dao.VehicleByteParserFactory;
import org.example.vehicle.utils.strategy.LinearSearch;
import org.example.vehicle.utils.strategy.LinearStreamSearch;
import org.example.vehicle.utils.strategy.Search;
import org.example.vehicle.utils.visitor.PrintVisitor;
import org.example.vehicle.utils.writers.VehicleColumnWriter;
import org.example.vehicle.utils.writers.VehicleRowWriter;
import org.example.vehicle.utils.writers.VehicleWriter;

import java.io.*;
import java.util.*;

public class Main {
    public static Properties properties = ReadProperties.getProperties();

    public static void main(String[] args) throws NoSuchModelNameException, InterruptedException, IOException, DuplicateModelNameException {
        Vehicle carTest = createVehicle(new CarFactory(), "LADA", 5);
        Vehicle bikeTest = createVehicle(new BikeFactory(), "Harley", 3);

        carTest.setPriceByName("default1", 150);
        System.out.println("Среднее арифметическое :" + VehicleUtil.getAvg(carTest));

        Vehicle bikeTestClone = (Vehicle) carTest.clone();
        bikeTestClone.setPriceByName("default3", 500);
        bikeTestClone.addNewModel("Test", 100);

        VehicleUtil.printVehicle(carTest);
        VehicleUtil.printVehicle(bikeTestClone);

        printAdapterOutputStream();
        runSynchronizedVehicle();

        System.out.println("---Chain of Responsibility and Command---");
        VehicleUtil.setVehicleWriter(VehicleWriter.link(
                new VehicleRowWriter(3, "src/main/resources/testRow.txt"),
                new VehicleColumnWriter(3, "src/main/resources/testColumn.txt")
        ));
        VehicleUtil.writeToFile(bikeTest);
        VehicleUtil.writeToFile(carTest);

        System.out.println("---Command---");
        VehicleUtil.printInStream(carTest, new FileWriter("src/main/resources/commandTest.txt"));

        System.out.println("---Iterator---");
        Car car = new Car("TestIterator", 4);
        car.forEach(System.out::println);

        System.out.println("-----Memento------");
        Car.Memento memento = car.createMemento();
        try {
            car.deleteModel("default1");
        } catch (NoSuchModelNameException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Удаление модели");
        car.forEach(System.out::println);

        System.out.println("Восстановление через снимок");
        car.setMemento(memento);
        car.forEach(System.out::println);

        System.out.println("-----Strategy 1------");
        Search search1 = new LinearSearch();
        Search search2 = new LinearStreamSearch();
        int len = 10;
        Random rnd = new Random();
        int [] mas = new int[len];
        for (int i = 0; i < len; i++) {
            mas[i] = rnd.nextInt(20);
        }

        long startTime = System.nanoTime();
        Map<Integer, Long> result1 = search1.count(mas);
        printMap(result1);
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println(timeElapsed);


        System.out.println("-----Strategy 2------");
        startTime = System.nanoTime();
        Map<Integer, Long> result2 = search2.count(mas);
        printMap(result2);
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println(timeElapsed);

        System.out.println("-----Visitor------");
        PrintVisitor prw = new PrintVisitor();
        System.out.println("Car");
        carTest.accept(prw);
        System.out.println();
        System.out.println("-----Visitor------");
        System.out.println("Bike");
        bikeTest.accept(prw);

        System.out.println("-----DAO------");
        Vehicle vehicle = new Car("CarDao", 5);
        System.out.println(vehicle);

        ParserDao<Vehicle> dao = VehicleUtil.createDao();
        dao.write("src/main/resources/daoTest.txt", List.of(vehicle));
        List<Vehicle> read = dao.readAll("src/main/resources/daoTest.txt");

        System.out.println("--------------------------------");
        System.out.println(read);

        vehicle = new Bike("BikeDao", 4);
        System.out.println(vehicle);

        VehicleUtil.setDaoFactory(new VehicleByteParserFactory());
        dao = VehicleUtil.createDao();
        dao.write("src/main/resources/daoTestBin.txt", List.of(vehicle));
        List<Vehicle> read2 = dao.readAll("src/main/resources/daoTestBin.txt");

        System.out.println("--------------------------------");
        System.out.println(read2);
    }

    private static void printProperties() {
        for (Object key : properties.keySet()) {
            System.out.println(key.toString() + " = " + properties.getProperty(key.toString()));
        }
    }

    private static void printMap(Map<Integer, Long> map) {
        for (Object key : map.keySet()) {
            System.out.println(key.toString() + " = " + map.get(key));
        }
    }

    private static Vehicle createVehicle(VehicleFactory vehicleFactory, String mark, int size) {
        VehicleUtil.setFactory(vehicleFactory);
        return VehicleUtil.createInstance(mark, size);
    }

    private static void printAdapterOutputStream() {
        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                FileOutputStream fos = new FileOutputStream("src/main/resources/test.txt");
            ) {
            StringAdapter stringAdapter1 = new StringAdapter(baos);
            StringAdapter stringAdapter2 = new StringAdapter(fos);
            String[] string = new String[] {"test", "test2"};
            stringAdapter1.write(string);
            stringAdapter2.write(string);
            fos.close();
            System.out.println(baos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void runSynchronizedVehicle() throws InterruptedException, NoSuchModelNameException {
        Vehicle volvoTest = createVehicle(new BikeFactory(), "Volvo", 0);
        Vehicle synchronizedCar = VehicleUtil.synchronizedVehicle(volvoTest);
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    synchronizedCar.addNewModel("defaultThread1" + i, 100);
                } catch (DuplicateModelNameException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    synchronizedCar.addNewModel("defaultThread2" + i, 100);
                } catch (DuplicateModelNameException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        VehicleUtil.printVehicle(volvoTest);
    }

    private static void daoParser(String filename, String fileType, List<ParserDao<Vehicle>> parserDaos) {
        for (ParserDao<Vehicle> parserDao: parserDaos) {
            if (parserDao.isValidFile(fileType)) {
                List<Vehicle> res = parserDao.readAll(filename);
                res.forEach(System.out::println);
            }
        }
    }
}