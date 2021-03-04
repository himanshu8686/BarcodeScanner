
package com.barcodescanner.barcodeParser;

import android.content.Context;
import android.util.Log;


import com.barcodescanner.models.BoardingPassModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ParseData {

    public static BoardingPassModel parse(String data, Context mContext) {
        ArrayList<String> values = new ArrayList<>(); // The cut string will be saved in this ArrayList

        BoardingPassModel boardingPassModel = new BoardingPassModel();
        int cursor = 0; // Indicates the location currently pointing to
        if (data.length() < 131) //Is less than 131 characters in length?
            while (data.length() != 131)
                data += " "; // Forced to increase to 131 characters

        for (int c : ParseDataEnum.CURSOR) { // ParseDataEnum ForEach statement to cut to the position of
            String p = "";
            System.out.println("==>>>" + c);
            //Name do not repelace spaces
            if (cursor == 2) {
                p = data.substring(cursor, cursor + c);
            } else {
                p = data.substring(cursor, cursor + c).replace(" ", ""); // Cut strings to fit
            }

            values.add(p); // Put the truncated string into values
            cursor += c; // at last cusrsor will be 131
        }

        Log.e("values=>", cursor + values.toString());

        /* ============ name formating ========*/
        int length = values.get(2).length();
        String name = values.get(2);
        if (name.substring(length - 2).equals("MR"))
            name = "Mr. " + values.get(2).substring(0, length - 2);
        else if (name.substring(length - 1).equals("MS"))
            name = "Ms. " + values.get(2).substring(0, length - 2);
        String[] words = name.split("/");
        name = "";
        for (String word : words) {
            try {
                if (word.startsWith("Mr. ") || word.startsWith("Ms. ")) {
                    // name += word.substring(0, 4) + word.substring(4, 5) + word.substring(5).toLowerCase() + " ";
                    name += word.substring(0, 4) + word.substring(4, 5) + word.substring(5) + " ";
                } else {
                    // name += word.substring(0, 1) + word.substring(1).toLowerCase() + " ";
                    name += word.substring(0, 1) + word.substring(1) + " ";
                }
            } catch (Exception e) {

            }

        }
        /* ============ name formating ========*/

        values.set(2, name); // setting name in array list
        if (name.length() <= 0) {
            boardingPassModel.setValid(false);
        }
        boardingPassModel.setName(name);
        // If the name ends in MR/MS, add Mr./Ms. in front of it.

        ///values.set(5, manager.getName(values.get(5)));
        ////
        //Searches and saves the name using the IATA code in the database where the airport name is stored.
        //values.set(6, manager.getName(values.get(6)));
        //
        //Searches and saves the name using the IATA code in the database where the airport name is stored.
        // values.set(7, airline_manager.getName(values.get(7)));

        /************** from city ****************/
        values.set(5, values.get(5)); // setting from city to arraylist
        if (values.get(5).length() <= 0)
        {
            boardingPassModel.setValid(false);
        }
        boardingPassModel.setFromCity(values.get(5));
        /************** from city ****************/

        /************** to city ****************/

        values.set(6, values.get(6));  // setting to city to arraylist
        if (values.get(6).length() <= 0)
            boardingPassModel.setValid(false);

        boardingPassModel.setToCity(values.get(6));


        // Search and save the name by IATA code in the database where airline names are stored
        if (values.get(8).startsWith("0")) {
            String flight_code = values.get(8);
            while (flight_code.startsWith("0"))
                flight_code = flight_code.substring(1);
            values.set(8, flight_code); // setting flight number to list
        }

        /************** flight number ****************/

        if (values.get(8).length() <= 0)
            boardingPassModel.setValid(false);
        boardingPassModel.setFlightNumber(values.get(8));

        /************** flight number ****************/


        // Make the flight number look pretty
        try {
            // int nextYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
            int boardingPassJulianDay = Integer.parseInt(values.get(9));
            int currentJulianDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
            currentJulianDay = currentJulianDay - 30;
            String input = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)) + values.get(9);
            if (currentJulianDay > boardingPassJulianDay) {
                input = Integer.toString(Calendar.getInstance().get(Calendar.YEAR) + 1) + values.get(9);
            }

            DateFormat fmt1 = new SimpleDateFormat("yyyyDDD");
            Date date = fmt1.parse(input);
            //DateFormat fmt2 = new SimpleDateFormat("MM-dd-yyyy"); // 율리우스 날짜를 입력 받아 그레고리언 날짜로 저장
            DateFormat fmt2 = new SimpleDateFormat("yyyy-MM-dd"); // Enter Julian date and save it as Gregorian date
            values.set(9, fmt2.format(date));

            /************** flight date ****************/
            boardingPassModel.setFlightDate(fmt2.format(date));
            /************** flight date ****************/


        } catch (ParseException e) {
            boardingPassModel.setValid(false);
            e.printStackTrace();
        }
        catch (Exception e){
            boardingPassModel.setValid(false);
            e.printStackTrace();
        }

        if (values.get(10).length() <= 0)
            boardingPassModel.setValid(false);
        String seat = "Economy";
//        switch (values.get(10)) { // Switch statement that takes the ticket class and transforms it into a full name
//            case "E":
//                seat = "Economy Class";
//                break;
//            case "J" :
//                seat = "Business Class";
//                break;
//            case "F" :
//                seat = "First Class";
//                break;
//            case "W" :
//                seat = "Premium Economy";
//                break;
//            default :
//                seat = "Economy Class";
//                break;
//        }

        // String[] economyClassCode = {"B", "E", "G", "H", "K", "L", "M", "N ", "O", "Q", "S", "T", "V", "W", "X", "Y", "U"};
        String[] businessClassCode = {"D", "J", "Z", "A", "P", "R", "F", "C"};
        boolean test = false;
        for (String element : businessClassCode) {
            if (element.equals(values.get(10))) {
                seat = "Business";
                break;
            }
        }
        boardingPassModel.setSeatingClass(seat); // setting seating class

        boardingPassModel.setCompartmentCode(values.get(10));// setting compartment code
        values.set(10, seat);// setting compartment code in arraylist

        if (!values.get(11).equals("")) {
            if (values.get(11).charAt(0) == '0')
                values.set(11, values.get(11).substring(1));
        }


        if (!values.get(7).equals("")) {
            boardingPassModel.setCarrierCode(values.get(7)); // setting carrier code
        }


        if (values.get(11).length() <= 0) // setting seat number
            boardingPassModel.setValid(false);
        boardingPassModel.setSeatNumber(values.get(11));

        // setting sequence no
        boardingPassModel.setSequenceNumber(values.get(12));

        return boardingPassModel;
    }
}
