1. This project assumes the maven is installed in the system where the project is being tested
2. Go to the main folder and run `mvn clean package`. This will run the test and compile the code.
3. Run mvn `mvn exec:java` to run the main file of the application.
4. You can type exit to exit the application.
5. Messages are expected to follow the format: Person = <PERSON>; Relation = <Relation>
6. Currently the program does not handle validation for invalid inputs
7. Any relation with spaces (like grand daughter) should be separated by hyphen (-).
8. It accepts the family tree in JSON format.
9. For problem 2, it supports only MOTHER to add a child and not FATHER. Also, the application supports both adding a DAUGHTER and SON.
The relation given in the sample input was grand-children but this application has implemented grand-daughter as mentioned in the relationships table.
10. Spelling for Lavanya is given as it is in sample input but as Lavnya in family tree diagram. In the JSON, I have used the name Lavnya.
11. For problem 3, give input as 'max girl child' and the application will print the list of mothers.
