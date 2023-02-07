# Currency Conversion

Example

Convert CAD to HKD
CAD -> USD -> HKD

There are no cycles
CAD -> GBP -> EUR -> GBP

How to run

* Download the project and import the code in IDE.
* Start the Springboot application from IDE. Default port is 8080. 
* We can change the port from application property file.
* Use URL to download the CSV file. http://localhost:8080/api/v1/currency/get-conversion-rate-csv
* Sample currency rate file has been attached. (https://github.com/sarthiMerchant7/currency-conversion/files/10674275/Currency-coversion-rate.3.csv)

Notes:

* OpenJDK 17 and Springboot version 2.7.8 has been used to develop this application.
