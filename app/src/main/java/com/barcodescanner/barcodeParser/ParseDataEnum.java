/**
 * ParseDataEnum
 * @author Kyujin Cho
 * @version 1.0
 * @see com.hanyang.bpreader.ParseData
 */

package com.barcodescanner.barcodeParser;
public class ParseDataEnum {
      // Descriptions about parsed data
    static final String[] DESC = {
            "Format Code", // index :0
            "PASS",// index :1
            "Name",// index :2
            "PASS",// index :3
            "Ticket PNR Code",// index :4
            "Departure Airport", // // index :5
            "Arrival Airport", // // index :6
            "Operating Carrier", // // index :7
            "Flight Number", // // index :8
            "Flight Date",// index :9
            "Seat Class",// index :10
            "Seat No.",// index :11
            "Check-In Seq. Number",// index :12
            "Passenger Status",// index :13
            "PASS",// index :14
            "PASS",// index :15
            "PASS",// index :16
            "PASS",// index :17
            "Passenger Desc.",// index :18
            "Source of Check-In",// index :19
            "Source of BP Insurance",// index :20
            "Date of Issue of BP",// index :21
            "Document Type",// index :22
            "Airline Designator of BP Issuer",// index :23
            "Baggage Tag License Plate Number(s)",// index :24
            "PASS",// index :25
            "Airline Numeric Code",// index :26
            "Document Form/Serial Number",// index :27
            "Selectee Indicator",// index :28
            "International Documentation Verification",// index :29
            "Marketing Carrier",// index :30
            "Frequent Flier Carrier",// index :31
            "FF Number",// index :32
            "ID/AD Indicator",// index :33
            "Free Baggage Allowance"// index :34
    };
    // Locations of each datas defined above
    static final int[] CURSOR = {1,1,20,1,7,3,3,3,5,3,1,4,5,1,2,1,1,2,1,1,1,4,1,3,13,2,3,10,1,1,3,3,16,1,3};
}
