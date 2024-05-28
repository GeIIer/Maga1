package org.example.vehicle.utils.dao;

import org.example.exception.DuplicateModelNameException;
import org.example.vehicle.Car;
import org.example.vehicle.Vehicle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VehicleTextParser implements ParserDao<Vehicle> {
    @Override
    public void write(String filename, List<Vehicle> objects) {
        try (FileWriter fw = new FileWriter(filename);
             PrintWriter pw = new PrintWriter(fw)) {
            for(Vehicle vehicle : objects) {
                pw.println(vehicle.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Vehicle> readAll(String filename) {
        try (FileReader fr = new FileReader(filename);
             BufferedReader br = new BufferedReader(fr)) {
            Pattern patternMark = Pattern.compile("Марка = '(.+?)'");
            Pattern patternModels = Pattern.compile("Model\\[(Название = '.*?', Цена = \\d+\\.\\d+)]");
            List<Vehicle> result = new ArrayList<>();
            while (br.ready()) {
                String input = br.readLine();
                Matcher matcher = patternMark.matcher(input);
                if (matcher.find()) {
                    Vehicle car = getVehicle(matcher, patternModels, input);
                    result.add(car);
                }

            }
            return result;
        } catch (IOException | DuplicateModelNameException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isValidFile(String fileType) {
        return "TXT".equals(fileType);
    }

    private static Vehicle getVehicle(Matcher matcher, Pattern patternModels, String input) throws DuplicateModelNameException {
        Car car = new Car(matcher.group(1));
        Matcher matcherModels = patternModels.matcher(input);
        while (matcherModels.find()) {
            String modelString = matcherModels.group(1);
            String[] parts = modelString.split(", ");
            String name = null;
            Double price = null;
            for (String part : parts) {
                if (part.startsWith("Название = '")) {
                    name = (part.substring(12, part.length() - 1));
                } else if (part.startsWith("Цена = ")) {
                    price = (Double.parseDouble(part.substring(7)));
                }
            }
            if (name != null && price != null) {
                car.addNewModel(name, price);
            } else {
                throw new RuntimeException();
            }

        }
        return car;
    }
}