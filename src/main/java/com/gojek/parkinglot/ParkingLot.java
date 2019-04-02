package com.gojek.parkinglot;

import java.util.*;
import java.util.Map.Entry;

/**
 * Created by Rajneesh
 */
public class ParkingLot {
    int MAX_SIZE = 0;
    int MAX_FLOOR =0;
    private class Car {
        String regNo;
        String color;
        public Car(String regNo, String color) {
            this.regNo = regNo;
            this.color = color;
        }
    }
    // Available slots list
    ArrayList<Integer> availableSlotList;
    // total count of floors
    ArrayList<Integer> availablefloors;
    // Map of floor and slots
    Map<Integer, ArrayList<Integer>> map4;
    // Map of Slot, Car
    Map<Map<Integer,Integer>, Car> map1;
    // Map of RegNo, Slot
    Map<String, Map<Integer,Integer>> map2;
    // Map of Color, List of RegNo
    Map<String, ArrayList<String>> map3;


    public void createParkingLot(String lotCount, String floors) {
        try {
            this.MAX_SIZE = Integer.parseInt(lotCount);
            this.MAX_FLOOR = Integer.parseInt(floors);
        } catch (Exception e) {
            System.out.println("Invalid lot count");
            System.out.println();
        }
        this.availableSlotList = new ArrayList<Integer>() {};
        this.availablefloors = new ArrayList<Integer>() {};

        for (int i=1; i<= this.MAX_SIZE; i++) {
            availableSlotList.add(i);
        }

        for(int i=1;i<= Integer.parseInt(floors);i++){
            availablefloors.add(i);
        }
        this.map1 = new HashMap<Map<Integer,Integer>,Car>();
        this.map2 = new HashMap<String, Map<Integer,Integer>>();
        this.map3 = new HashMap<String, ArrayList<String>>();
        this.map4 = new HashMap<Integer, ArrayList<Integer>>();

        for(int i=1;i<=Integer.parseInt(floors);i++){
            map4.put(i,availableSlotList);
        }
        Integer totalSlots = Integer.parseInt(floors) * MAX_SIZE;
        System.out.println("Created parking lot with " + floors + " floors and total " + totalSlots + " slots");
        System.out.println();
    }
    public void park(String regNo, String color) {
        if (this.MAX_SIZE == 0 || this.MAX_FLOOR ==0) {
            System.out.println("Sorry, parking lot is not created");
            System.out.println();
        } else if (this.map1.size() == this.MAX_SIZE * this.MAX_FLOOR) {
            System.out.println("Sorry, parking lot is full");
            System.out.println();
        } else {
            Collections.sort(availableSlotList);
            Collections.sort(availablefloors);
//            String slot = availableSlotList.get(0).toString();
            Integer floor = availablefloors.get(0);
            Integer slot = map4.get(floor).get(0);
            Car car = new Car(regNo, color);
            Map<Integer,Integer> floorSlotMapping = new HashMap<Integer, Integer>();
            floorSlotMapping.put(floor,slot);
            this.map1.put(floorSlotMapping,car);
            this.map2.put(regNo, floorSlotMapping);
            if (this.map3.containsKey(color)) {
                ArrayList<String> regNoList = this.map3.get(color);
                this.map3.remove(color);
                regNoList.add(regNo);
                this.map3.put(color, regNoList);
            } else {
                ArrayList<String> regNoList = new ArrayList<String>();
                regNoList.add(regNo);
                this.map3.put(color, regNoList);
            }
            System.out.println("Allocated slot number: " + slot);
            System.out.println();
            availableSlotList.remove(0);
            map4.put(floor,availableSlotList);
        }
    }
    public void leave(String slotNo,String floorNo) {
        if (this.MAX_SIZE == 0 || this.MAX_FLOOR == 0) {
            System.out.println("Sorry, parking lot is not created");
            System.out.println();
        } else if (this.map1.size() > 0) {
            Map<Integer,Integer> floorSlotMapping = new HashMap<Integer, Integer>();
            floorSlotMapping.put(Integer.parseInt(floorNo),Integer.parseInt(slotNo));
            Car carToLeave = this.map1.get(floorSlotMapping);
            if (carToLeave != null) {
                this.map1.remove(floorSlotMapping);
                this.map2.remove(carToLeave.regNo);
                ArrayList<String> regNoList = this.map3.get(carToLeave.color);
                if (regNoList.contains(carToLeave.regNo)) {
                    regNoList.remove(carToLeave.regNo);
                }
                // Add the Lot No. back to available slot list.
                availableSlotList = map4.get(Integer.parseInt(floorNo));
                this.availableSlotList.add(Integer.parseInt(slotNo));
                map4.put(Integer.parseInt(floorNo),availableSlotList);
                System.out.println("Floor number " + floorNo +  " Slot number " + slotNo + " is free");
                System.out.println();
            } else {
                System.out.println("Floor number" + floorNo + "Slot number " + slotNo + " is already empty");
                System.out.println();
            }
        } else {
            System.out.println("Parking lot is empty");
            System.out.println();
        }
    }
    public void status() {
        if (this.MAX_SIZE == 0) {
            System.out.println("Sorry, parking lot is not created");
            System.out.println();
        } else if (this.map1.size() > 0) {
            // Print the current status.
            System.out.println("Slot No.\tRegistration No.\tColor");
            Car car;
            for(int i=1;i<=MAX_FLOOR;i++) {
                for (int j=1; j <= this.MAX_SIZE; j++) {
                    Map<Integer,Integer> floorSlotMapping = new HashMap<Integer, Integer>();
                    floorSlotMapping.put(i,j);
                    if (this.map1.containsKey(floorSlotMapping)) {
                        car = this.map1.get(floorSlotMapping);
                        System.out.println(i + "\t" + car.regNo + "\t" + car.color);
                    }
                }
            }
            System.out.println();
        } else {
            System.out.println("Parking lot is empty");
            System.out.println();
        }
    }
    public void getRegistrationNumbersFromColor(String color) {
        if (this.MAX_SIZE == 0 || this.MAX_FLOOR == 0) {
            System.out.println("Sorry, parking lot is not created");
            System.out.println();
        } else if (this.map3.containsKey(color)) {
            ArrayList<String> regNoList = this.map3.get(color);
            System.out.println();
            for (int i=0; i < regNoList.size(); i++) {
                if (!(i==regNoList.size() - 1)){
                    System.out.print(regNoList.get(i) + ",");
                } else {
                    System.out.print(regNoList.get(i));
                }
            }
        } else {
            System.out.println("Not found");
            System.out.println();
        }
    }
    public void getSlotNumbersFromColor(String color) {
        if (this.MAX_SIZE == 0 || this.MAX_FLOOR == 0) {
            System.out.println("Sorry, parking lot is not created");
            System.out.println();
        } else if (this.map3.containsKey(color)) {
            ArrayList<String> regNoList = this.map3.get(color);
            ArrayList<Map<Integer,Integer>> slotList = new ArrayList<Map<Integer, Integer>>();
            System.out.println();
            for (int i=0; i < regNoList.size(); i++) {
                slotList.add(this.map2.get(regNoList.get(i)));
            }
//            Collections.sort(slotList);
            for (int j=0; j < slotList.size(); j++) {
                if (!(j == slotList.size() - 1)) {
                    Map<Integer,Integer> mp = slotList.get(j);
                    for (Entry<Integer,Integer> entry : mp.entrySet()) {
                    System.out.print("Floor :" + Integer.toString(entry.getKey()) + " Slot :" + Integer.toString(entry.getValue()) + "/n");
                }
                }
            }
            System.out.println();
        } else {
            System.out.println("Not found");
            System.out.println();
        }
    }
    public void getSlotNumberFromRegNo(String regNo) {
        if (this.MAX_SIZE == 0 || this.MAX_FLOOR == 0) {
            System.out.println("Sorry, parking lot is not created");
            System.out.println();
        } else if (this.map2.containsKey(regNo)) {
            Map<Integer,Integer> mp = this.map2.get(regNo);
            for (Entry<Integer,Integer> entry : mp.entrySet()) {
                System.out.print("Floor :" + Integer.toString(entry.getKey()) + " Slot :" + Integer.toString(entry.getValue()) + "/n");
            }
        } else {
            System.out.println("Not found");
            System.out.println();
        }
    }
}
