package com.FamilyTree;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Main class
 */
class FamilyTree {
    private final static String FAMILY_TREE_FILE_PATH = "src/main/java/json/ShanFamilyTree.json";
    private static Map<String, Person> personMap = new HashMap<>();

    /**
     * Constructor
     */
    public FamilyTree () {
        try {
            createFamilyTree();
        } catch (IOException ex) {
            System.out.println("Not able to read the family tree JSON file.");
            System.exit(0);
        }
    }

    /**
     * Utility method to find list of mothers with maximum girl child
     * @return
     */
    public List<String> findMothersWithMaxGirlChild() {
        Map<String, Integer> motherDaughterCountMap = personMap.values()
                .stream()
                .filter(c -> c.hasParent() && c.hasDaughters() && c.isFemale())
                .collect(Collectors.toMap(Person::getName, Person::noOfDaughters));

        long maxDaughters = motherDaughterCountMap.values().stream().max(Comparator.naturalOrder()).get();
        return motherDaughterCountMap.entrySet()
                .stream()
                .filter(e -> e.getValue() == maxDaughters)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Person getPerson (String person) {
        return FamilyTree.personMap.get(person);
    }

    public boolean hasPerson (String person) {
        return FamilyTree.personMap.containsKey(person);
    }

    /**
     * Method to create Family Tree
     * @throws IOException IO exception
     */
    private void createFamilyTree() throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonRootNode = mapper.readTree(new File(FAMILY_TREE_FILE_PATH));
        populateTree(null, jsonRootNode);
    }

    /**
     * Recursive method to read JSON and create Person tree.
     * @param parent parent
     * @param jsonNode current node
     * @return person
     */
    private Person populateTree(Person parent, JsonNode jsonNode) {
        Person person = new Person(jsonNode.path("name").asText(), jsonNode.path("gender").asText());
        personMap.putIfAbsent(jsonNode.path("name").asText(), person);
        JsonNode partnerJsonNode = jsonNode.path("partner");
        if (!partnerJsonNode.isMissingNode()) {
            Person partner = new Person(partnerJsonNode.path("name").asText(), partnerJsonNode.path("gender").asText());
            person.setPartner(partner);
            partner.setPartner(person);
            personMap.putIfAbsent(partnerJsonNode.path("name").asText(), partner);
        }
        if (jsonNode.path("children").size() > 0) {
            for (JsonNode childNode : jsonNode.path("children")) {
                populateTree(person, childNode);
            }
        }
        person.setParent(parent);
        if (parent != null) {
            parent.addChild(person);
        }
        return person;
    }

    public static void addPersonToMap (Person person) {
        personMap.putIfAbsent(person.getName(), person);
    }
}