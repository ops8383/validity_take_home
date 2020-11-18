package com.validity.monolithstarter.service;
import com.opencsv.CSVReader;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;


@Service
public class FindDuplicateService {

    private JSONArray deduplicates = null;
    private JSONArray duplicates = null;




    public JSONArray getDuplicates() {
        if (duplicates == null || deduplicates == null) {
            FileReader dataFile = null;
            try {
                String current = new java.io.File( "." ).getCanonicalPath();
                System.out.println("Current dir:"+current);
                dataFile = new FileReader("src/main/resources/normal.csv");
                CSVReader dataReader = new CSVReader(dataFile);
                List<String[]> dataLines = dataReader.readAll();
                removeDuplicates(dataLines);

            } catch (IOException e) {
                System.err.println("Error while getting duplicates");
                duplicates = null;
                deduplicates = null;
            }

        }
        return duplicates;
    }

    public JSONArray getDeduplicates() {
        if (duplicates == null || deduplicates == null) {
            FileReader dataFile = null;
            try {
                dataFile = new FileReader("src/main/resources/normal.csv");
                CSVReader dataReader = new CSVReader(dataFile);
                List<String[]> dataLines = dataReader.readAll();
                removeDuplicates(dataLines);

            } catch (IOException e) {
                System.err.println("Error while getting duplicates");
                duplicates = null;
                deduplicates = null;
            }

        }
        return deduplicates;
    }

    private void removeDuplicates(List<String[]> data) {
        String[] headers = data.get(0);
        JSONArray array = new JSONArray();
        HashMap<String, HashMap<String, ArrayList<Integer>>> dictionary = new HashMap<>();
        HashMap<Integer, Record> deduplicates = new HashMap<>();
        HashMap<Integer, Record> duplicates = new HashMap<>();
        for (int i = 1; i < data.size(); i++) {
            String[] tokens = data.get(i);
            boolean flag = false;
            try {
                Record record = new Record(i,tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5],
                    tokens[6], tokens[7], tokens[8], tokens[9], tokens[10], tokens[11]);
                for (int j = 1; j < headers.length; j++) {

                    if (!dictionary.containsKey(headers[j])) {
                        dictionary.put(headers[j], new HashMap<>());
                    }
                    if (!dictionary.get(headers[j]).containsKey(tokens[j])) {
                        dictionary.get(headers[j]).put(tokens[j], new ArrayList<>());
                    }
                    if(dictionary.get(headers[j]).get(tokens[j]).size() != 0){
                        for(Integer item :dictionary.get(headers[j]).get(tokens[j]) ){
                            if(record.equals(deduplicates.get(item))){
                                flag = true;
                                break;
                            }
                        }
                    }
                    if (flag) break;

                }
                if(!flag){
                    deduplicates.put(i, record);
                    for (int j = 1; j < headers.length; j++) {
                        dictionary.get(headers[j]).get(tokens[j]).add(i);
                    }
                }else{
                    duplicates.put(i, record);
                }
            } catch (Exception e) {
                System.err.println("Could't add Record: -" + Arrays.toString( data.get(i)));
            }
        }
        for(Integer item : deduplicates.keySet())
                array.put( deduplicates.get(item).toJSON());
        this.deduplicates = array;

        array =  new JSONArray();

        for(Integer item : duplicates.keySet())
            array.put( duplicates.get(item).toJSON());
        this.duplicates = array;

    }


    public class Record {
        //fields in the record
        public String id, firstName, lastName, company, email,
            address1, address2, zip, city, stateLong, state, phone;
        public int ID;
        //constructor
        public Record(int ID, String id, String firstName, String lastName, String company,
                      String email, String address1, String address2, String zip,
                      String city, String stateLong, String state, String phone) {
            this.ID = ID;
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.company = company;
            this.email = email;
            this.address1 = address1;
            this.address2 = address2;
            this.zip = zip;
            this.city = city;
            this.stateLong = stateLong;
            this.state = state;
            this.phone = phone;
        }

        //comparing two strings
        public int distance(String a, String b) {
            a = a.toLowerCase();
            b = b.toLowerCase();
            int[] costs = new int[b.length() + 1];
            for (int j = 0; j < costs.length; j++)
                costs[j] = j;
            for (int i = 1; i <= a.length(); i++) {
                costs[0] = i;
                int nw = i - 1;
                for (int j = 1; j <= b.length(); j++) {
                    int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                    nw = costs[j];
                    costs[j] = cj;
                }
            }
            return costs[b.length()];
        }

        public JSONObject toJSON(){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", this.id);
                jsonObject.put("firstName",this.firstName);
                jsonObject.put("lastName", this.lastName);
                jsonObject.put("company", this.company);
                jsonObject.put("email", this.email);
                jsonObject.put("address1", this.address1);
                jsonObject.put("address2", this.address2);
                jsonObject.put("zip", this.zip);
                jsonObject.put("city", this.city);
                jsonObject.put("stateLong", this.stateLong);
                jsonObject.put("state", this.state);
                jsonObject.put("phone", this.phone);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }


        //comparing two records to find if they are duplicates
        @Override
        public boolean equals(Object obj) {
            if (obj.getClass() != Record.class) {
                return false;
            }
            Record a = (Record) obj;
            boolean x = ( (distance(this.email, a.email) == 0) && (distance(this.phone, a.phone) == 0)) ||
                ((distance(this.firstName, a.firstName) < 3) && (distance(this.lastName, a.lastName) < 3) && (distance(this.company, a.company) < 3)
                    && (distance(this.address1 + this.address2, a.address1 + a.address2) < 5) && (distance(this.zip, a.zip) < 2)
                    && (distance(this.city, a.city) < 2) &&(distance(this.state, a.state) == 0));
            return x;
        }


    }
}

