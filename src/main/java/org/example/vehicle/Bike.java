package org.example.vehicle;

import org.example.exception.DuplicateModelNameException;
import org.example.exception.ModelPriceOutOfBoundsException;
import org.example.exception.ModelSizeException;
import org.example.exception.NoSuchModelNameException;
import org.example.vehicle.utils.visitor.Visitor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.StringJoiner;

public class Bike implements Vehicle {
    private String mark;
    private Model head = new Model();
    private int size = 0;

    {
        head.prev = head;
        head.next = head;
    }

    public Bike(String markName, int size) {
        if (size < 0) {
            throw new ModelSizeException(Integer.toString(size));
        }
        this.mark = markName;
        createList(size);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int lengthModels() {
        return size;
    }

    @Override
    public String toString() {
        Model p = head.next;
        Model[] models = new Model[size];
        for (int i = 0; i < lengthModels(); i++) {
            models[i] = p;
            p = p.next;
        }
        StringJoiner sj = new StringJoiner(", ", Bike.class.getSimpleName() + "[", "]")
                .add("Марка = '" + mark + "'")
                .add("Линейка моделей (" + size + ") = " + Arrays.toString(models));
        sj.add(Arrays.toString(models));
        return sj.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Bike) {
            if (((Bike) obj).getMark().equals(mark) && ((Bike) obj).lengthModels() == lengthModels()) {
                for (int i = 0; i < lengthModels(); i++) {
                    if (!(((Bike) obj).getAllName()[i].equals(getAllName()[i]) && ((Bike) obj).getAllPrice()[i] == getAllPrice()[i])) {
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
        if (head != null) {
            modelHash = head.hashCode();
        }
        return hash = mark.hashCode() + modelHash;
    }

    @Override
    public Object clone() {
        Bike bike = null;
        try {
            bike = (Bike) super.clone();
            bike.head = this.head.clone();
            Model p = this.head.next;
            Model pClone = bike.head;
            while (p != this.head) {
                pClone.next.prev = pClone;
                pClone.next = p.clone();
                pClone = pClone.next;
                p = p.next;
            }
            return bike;
        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    private void createList(int size) {
        Model p;
        this.size = size;
        for (int i = 0; i < size; i++) {
            p = new Model("default" + (i + 1), 100, head.prev, head);
            head.prev.next = p;
            head.prev = p;
        }
    }

    public String[] getAllName() {
        String[] array = new String[lengthModels()];
        int index = 0;
        Model p = head.next;
        while (index < lengthModels() && p != head) {
            array[index] = p.modelName;
            index++;
            p = p.next;
        }
        return array;
    }

    public double getPriceByName(String name) throws NoSuchModelNameException {
        int count = lengthModels();
        boolean nameNoExists = true;
        double priceToName = 0;
        Model p = head.next;
        for (int i = 0; i < count; i++) {
            if (p.modelName.equals(name)) {
                priceToName = p.price;
                nameNoExists = false;
            } else {
                p = p.next;
            }
        }
        if (nameNoExists) throw new NoSuchModelNameException(name);
        return priceToName;
    }

    public void setNewNameByOldName(String newName, String oldName) throws NoSuchModelNameException, DuplicateModelNameException {
        int count = lengthModels();
        boolean nameNoExists = true;
        Model p = head.next;
        for (int i = 0; i < count; i++) {
            if (p.modelName.equals(newName)) {
                throw new DuplicateModelNameException(newName);
            } else {
                p = p.next;
            }
        }
        Model p1 = head.next;
        for (int i = 0; i < count; i++) {
            if (p1.modelName.equals(oldName)) {
                p1.modelName = newName;
                nameNoExists = false;
            } else {
                p1 = p1.next;
            }
        }
        if (nameNoExists) throw new NoSuchModelNameException(oldName);
    }

    public void setPriceByName(String name, double newPrice) throws NoSuchModelNameException {
        if (newPrice < 0) throw new ModelPriceOutOfBoundsException(Double.toString(newPrice));
        int count = lengthModels();
        boolean nameNoExists = true;
        Model p = head.next;
        for (int i = 0; i < count; i++) {
            if (p.modelName.equals(name)) {
                p.price = newPrice;
                nameNoExists = false;
            } else {
                p = p.next;
            }
        }
        if (nameNoExists) throw new NoSuchModelNameException(name);
    }

    public double[] getAllPrice() {
        double[] array = new double[lengthModels()];
        int index = 0;
        Model p = head.next;
        while (index < lengthModels() && p != head) {
            array[index] = p.price;
            index++;
            p = p.next;

        }
        return array;
    }

    public void addNewModel(String modelName, double price) throws DuplicateModelNameException {
        Model t = head.next;
        boolean modelExist = false;
        while (t != head && !modelExist) {
            if (t.modelName.equals(modelName)) {
                modelExist = true;
            }
            t = t.next;
        }
        if (modelExist) throw new DuplicateModelNameException(modelName);
        Model p = new Model(modelName, price, head, head.next);
        head.next.prev = p;
        head.next = p;
        size++;
    }


    public void deleteModel(String modelName) throws NoSuchModelNameException {
        Model p = head.next;
        while (p != head && !(p.modelName.equals(modelName))) {
            p = p.next;
        }
        if (p == head) throw new NoSuchModelNameException("Модель " + modelName + " отсутствует.");

        p.prev.next = p.next;
        p.next.prev = p.prev;
        size--;
    }

    public static class Model implements Serializable, Cloneable {
        String modelName = null;
        double price;
        Model prev = null;
        Model next = null;

        public Model() {
        }

        public Model(String modelName, double price, Model prev, Model next) {
            if (price < 0) throw new ModelPriceOutOfBoundsException("Некорректная цена");
            this.modelName = modelName;
            this.price = price;
            this.prev = prev;
            this.next = next;
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
}
