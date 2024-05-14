package org.example.vehicle;

import org.example.exception.DuplicateModelNameException;
import org.example.exception.ModelPriceOutOfBoundsException;
import org.example.exception.ModelSizeException;
import org.example.exception.NoSuchModelNameException;
import org.example.vehicle.utils.visitor.Visitor;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringJoiner;

public class Car implements Vehicle, Iterable<String> {
    private String mark;
    private Model[] models;

    public Car(String markName, int size) {
        this.mark = markName;
        if (size > 0) {
            models = new Model[size];
            for (int i = 0; i < size; i++) {
                models[i] = defaultModel(i);
            }
        } else if (size == 0) {
            models = new Model[0];
        } else {
            throw new ModelSizeException(Integer.toString(size));
        }
    }

    public Car(String markName) {
        this.mark = markName;
        this.models = new Model[0];
    }

    private Model defaultModel(int size) {
        String name = Integer.toString(size + 1);
        String defModel = "default" + name;
        return new Model(defModel, 100);
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int lengthModels() {
        return models.length;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("Марка = '" + mark + "'")
                .add("Линейка моделей (" + models.length + ") = " + Arrays.toString(models))
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Car) {
            if (((Car) obj).getMark().equals(mark) && ((Car) obj).lengthModels() == lengthModels()) {
                for (int i = 0; i < lengthModels(); i++) {
                    if (!(((Car) obj).getAllName()[i].equals(getAllName()[i]) && ((Car) obj).getAllPrice()[i] == getAllPrice()[i])) {
                        return false;
                    }
                }
                return true;
            } else return false;
        } else return false;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        int modelHash = 0;
        if (models != null) {
            modelHash = Arrays.hashCode(models);
        }
        return mark.hashCode() + modelHash;
    }

    @Override
    public Object clone() {
        Car car = null;
        try {
            car = (Car) super.clone();
            car.models = new Model[lengthModels()];
            for (int i = 0; i < car.lengthModels(); i++) {
                car.models[i] = this.models[i].clone();
            }
            return car;
        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String[] getAllName() {
        String[] array = new String[models.length];
        for (int i = 0; i < models.length; i++) {
            array[i] = models[i].getModelName();
        }
        return array;
    }

    public double getPriceByName(String name) throws NoSuchModelNameException {
        double priceToName = 0;
        boolean nameNoExists = true;
        for (Model model : models) {
            if (model.getModelName().equals(name)) {
                priceToName = model.getPrice();
                nameNoExists = false;
                break;
            }
        }
        if (nameNoExists) throw new NoSuchModelNameException(name);
        return priceToName;
    }

    public void setNewNameByOldName(String newName, String oldName) throws NoSuchModelNameException, DuplicateModelNameException {
        int index = -1;
        for (int i = 0; i < models.length; i++) {
            if (models[i].getModelName().equals(newName)) {
                throw new DuplicateModelNameException(newName);
            }
            if (models[i].getModelName().equals(oldName)) {
                index = i;
            }
        }
        if (index == -1) throw new NoSuchModelNameException(oldName);
        models[index].setModelName(newName);
    }

    public void setPriceByName(String name, double newPrice) throws NoSuchModelNameException {
        if (newPrice < 0) throw new ModelPriceOutOfBoundsException("Некорректная цена");
        boolean nameNoExists = true;
        for (Model model : models) {
            if (model.getModelName().equals(name)) {
                model.setPrice(newPrice);
                nameNoExists = false;
                break;
            }
        }
        if (nameNoExists) throw new NoSuchModelNameException(name);
    }

    public double[] getAllPrice() {
        double[] array = new double[models.length];
        for (int i = 0; i < models.length; i++) {
            array[i] = models[i].getPrice();
        }
        return array;
    }

    public void addNewModel(String modelName, double price) throws DuplicateModelNameException {
        if (price < 0) throw new ModelPriceOutOfBoundsException("Некорректная цена");
        for (Model model : models) {
            if (model.modelName.equals(modelName)) {
                throw new DuplicateModelNameException(modelName);
            }
        }
        models = Arrays.copyOf(models, models.length + 1);
        models[models.length - 1] = new Model(modelName, price);
    }

    public void deleteModel(String modelName) throws NoSuchModelNameException {
        boolean nameNoExists = true;
        for (int i = 0; i < models.length; i++) {
            if (models[i].getModelName().equals(modelName)) {
                Model[] copyModels = Arrays.copyOf(models, models.length - 1);
                if (i != models.length - 1) {
                    System.arraycopy(models, i + 1, copyModels, i, copyModels.length - i);
                }
                models = copyModels;
                nameNoExists = false;
            }
        }
        if (nameNoExists) throw new NoSuchModelNameException(modelName);
    }

    @Override
    public Iterator<String> iterator() {
        return new CarIterator(models);
    }

    public static class Model implements Serializable, Cloneable {
        private String modelName;
        private double price;

        public Model(String modelName, double price) {
            if (price < 0) throw new ModelPriceOutOfBoundsException("Некорректная цена");
            this.modelName = modelName;
            this.price = price;
        }

        public String getModelName() {
            return modelName;
        }

        public double getPrice() {
            return price;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

        public void setPrice(double price) {
            if (price < 0) throw new ModelPriceOutOfBoundsException("Некорректная цена");
            this.price = price;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Model.class.getSimpleName() + "[", "]")
                    .add("Название = '" + modelName + "'")
                    .add("Цена = " + price)
                    .toString();
        }

        @Override
        public Model clone() throws CloneNotSupportedException {
            Model modelClone = (Model) super.clone();
            modelClone.modelName = this.modelName;
            modelClone.price = this.price;
            return modelClone;
        }
    }


     static class CarIterator implements Iterator<String> {

        int cursor = 0;
        Model[] models;

        protected CarIterator(Model[] models) {
            this.models = models;
        }

        @Override
        public boolean hasNext() {
            return cursor < models.length;
        }

        @Override
        public String next() {
            return models[cursor++].toString();
        }
    }

    public Memento createMemento() {
        Memento memento = new Memento();
        memento.setCar(this);
        return memento;
    }

    public void setMemento(Memento memento)  {
        Car state = memento.getCar();
        this.mark = state.mark;
        this.models = state.models;
    }

    public static class Memento {
        private final ByteArrayOutputStream memory = new ByteArrayOutputStream();

        public void setCar(Car car) {
            this.memory.reset();
            try(ObjectOutputStream oIS = new ObjectOutputStream(memory)) {
                oIS.writeObject(car);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        public Car getCar() {
            byte[] byteArray = memory.toByteArray();
            try (ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(byteArray);
                 ObjectInputStream objectInput = new ObjectInputStream(byteArrayInput)) {
                return (Car) objectInput.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
    }
}
