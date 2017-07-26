package com.FamilyTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Input Listener class
 */
class InputListener {
    private static final String EXIT = "Exit";
    private static final String PERSON = "PERSON";
    private static final String RELATION = "RELATION";
    private static final String INVALID_INPUT = "Invalid Input. Please enter again.";
    private static final String MAX_GIRL_CHILD = "Max girl child";
    //Relations
    private static final String BROTHERS = "BROTHERS";
    private static final String SISTERS = "SISTERS";
    private static final String GRAND_DAUGHTER = "GRAND-DAUGHTER";
    private static final String SON = "SON";
    private static final String DAUGHTER = "DAUGHTER";
    private static final String CHILDREN = "CHILDREN";
    private static final String FATHER = "FATHER";
    private static final String MOTHER = "MOTHER";
    private static final String COUSINS = "COUSINS";
    private static final String BROTHER_IN_LAW = "BROTHER-IN-LAW";
    private static final String SISTER_IN_LAW = "SISTER-IN-LAW";
    private static final String MATERNAL_AUNT = "MATERNAL-AUNT";
    private static final String PATERNAL_AUNT =  "PATERNAL-AUNT";
    private static final String MATERNAL_UNCLE = "MATERNAL-UNCLE";
    private static final String PATERNAL_UNCLE = "PATERNAL-UNCLE";

    private Scanner scanner;
    private FamilyTree familyTree;

    public InputListener (Scanner scanner, FamilyTree familyTree) {
        this.scanner = scanner;
        this.familyTree = familyTree;
    }

    /**
     * Method to parse input message and do action accordingly
     */
    public void parseInput() {
        printWelcomeMessage();
        String input = scanner.nextLine();
        while (!(input).equalsIgnoreCase(EXIT)) {
            if (isNewBorn(input)) {
                processNewBorn(input);
            } else if (isFindRelation(input)) {
                processFindRelation(input);
            } else if (input.equalsIgnoreCase(MAX_GIRL_CHILD)) {
                System.out.println(
                    familyTree.findMothersWithMaxGirlChild()
                            .stream()
                            .collect(Collectors.joining(", "))
                );
            } else {
                System.out.println(INVALID_INPUT);
            }
            System.out.print("Input: ");
            input = scanner.nextLine();
        }
    }

    /**
     * Process input to find relations for given person
     * @param input input string that contains person and relation
     */
    private void processFindRelation(String input) {
        String[] inputArray = input.split(";");
        String person = parseEntity(inputArray[0], 1);
        String relation = parseEntity(inputArray[1], 1);
        findRelations(familyTree.getPerson(person), relation.toLowerCase());
    }

    /**
     * Process input to add new member to the family
     * @param input input string that contains mother and daughter/son name
     */
    private void processNewBorn(String input) {
        String[] inputArray = input.split(";");
        String mother = parseEntity(inputArray[0], 1);
        String childName = parseEntity(inputArray[1], 1);
        String childSex = childSex(parseEntity(inputArray[1], 0));
        if (familyTree.hasPerson(mother)) {
            familyTree.getPerson(mother).addChild(childName, childSex);
        } else {
            System.out.println(INVALID_INPUT);
        }
    }

    /**
     * Retrieve child sex from the input
     * @param input input string
     * @return String - male or female
     */
    private String childSex(String input) {
        if (input.equalsIgnoreCase(DAUGHTER)) {
            return "female";
        } else {
            return "male";
        }
    }

    /**
     * Utility method to find if input is to add a new member
     * @param input
     * @return
     */
    private Boolean isNewBorn(String input) {
        input = input.toLowerCase();
        boolean startsWithMother = input.startsWith(MOTHER.toLowerCase());
        boolean hasSonOrDaughter =
                (input.indexOf(DAUGHTER.toLowerCase()) > 0) || (input.indexOf(SON.toLowerCase()) > 0);
        return startsWithMother && hasSonOrDaughter;
    }

    /**
     * Utility method to find if input is to find relatios for given person
     * @param input
     * @return
     */
    private Boolean isFindRelation(String input) {
        input = input.toLowerCase();
        boolean startsWithPerson = input.startsWith(PERSON.toLowerCase());
        boolean hasRelation = input.indexOf(RELATION.toLowerCase()) > 0;
        return startsWithPerson && hasRelation;
    }

    /**
     * Utility method to parse the given input
     * @param input
     * @param index
     * @return
     */
    private String parseEntity(String input, int index) {
        return (input.split("=")[index]).trim();
    }

    /**
     * Based on the parsed input, this class retrieves the relations for the given input.
     * @param person person
     * @param relation relation
     */
    private void findRelations(Person person, String relation) {
        switch (relation.toUpperCase()) {
            case CHILDREN:
                prettyPrint(person.children());
                break;
            case BROTHERS:
                prettyPrint(person.brothers());
                break;
            case SISTERS:
                prettyPrint(person.sisters());
                break;
            case FATHER:
                prettyPrint(new ArrayList<Person>() {{
                    add(person.father());
                }});
                break;
            case MOTHER:
                prettyPrint(new ArrayList<Person>() {{
                    add(person.mother());
                }});
                break;
            case SON:
                prettyPrint(person.sons());
                break;
            case DAUGHTER:
                prettyPrint(person.daughters());
                break;
            case GRAND_DAUGHTER:
                prettyPrint(person.grandDaughters());
                break;
            case COUSINS:
                prettyPrint(person.cousins());
                break;
            case BROTHER_IN_LAW:
                prettyPrint(person.brotherInLaws());
                break;
            case SISTER_IN_LAW:
                prettyPrint(person.sisterInLaws());
                break;
            case MATERNAL_AUNT:
                prettyPrint(person.maternalAunts());
                break;
            case PATERNAL_AUNT:
                prettyPrint(person.paternalAunts());
                break;
            case MATERNAL_UNCLE:
                prettyPrint(person.maternalUncles());
                break;
            case PATERNAL_UNCLE:
                prettyPrint(person.paternalUncles());
                break;
            default:
                System.out.println("Not a valid Input");
        }
    }

    private void prettyPrint(List<Person> people) {
        System.out.println(
                people.stream()
                     .map(Person::getName)
                     .collect(Collectors.joining(", "))
        );
    }

    /**
     * Utility method to print welcome message.
     */
    private void printWelcomeMessage() {
        System.out.println("Welcome to King Shan Family Tree !!!");
        System.out.println("(Type 'exit' to end the program)");
        System.out.println("Input: ");
    }
}