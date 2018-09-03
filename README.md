# Rabobank Customer Transaction Processing
This is a standalone application that reads input files that are in the below formats:<br>
* .csv<br>
* .xml<br>
The inputs files are processed and failed transactions are stored in final output file that will be in .csv format.

-------------
# Building from source
Get the source code from git repository using the URL:<br>
```bash
git clone https://github.com/Nandini30/Assignment.git
```
-------------
# STS Tool
Import the project into STS as existing project and build the project using Maven.

-------------
# Input Files
Provide the input files in the below path:<br>
`/StatementProcessor/src/main/resources/input` <br>
The input files must be either in .csv or .xml format

-------------
# Execution Steps
Open the Boot Application File located in the below path:<br>
`/StatementProcessor/src/main/java/com/rabo/transaction/TransactionProcessorApplication.java`<br>
Run the above application as Spring boot appplication. <br>

Once the server is started and JVM is running, hit the below URL to start the Batch Job.<br>
`http://localhost:8081/launchJob`<br>
The job will be executed the corresponding status will be reflected in the browser.

-------------
# Output file
A report will be generated in the below path based on the inputs that are processed from the Input files.<br>
`/StatementProcessor/target/classes/output/records_output.csv`<br>
The report contains the consolidated failed transactions from all input files.
