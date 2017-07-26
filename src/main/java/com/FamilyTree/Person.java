package com.FamilyTree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Person class that represents each entity in the tree
 */
class Person {
    private Person partner;
    private Person parent;
    private List<Person> children;
    private final String name;
    private final String sex;

    /**
     * Constructor
     * @param name name
     * @param sex sex
     */
    public Person (String name, String sex) {
        this.name = name;
        this.sex = sex;
        this.children = new ArrayList<>();
    }

    /**
     * setter for partner
     * @param partner
     */
    public void setPartner (Person partner) {
        this.partner = partner;
    }

    /**
     * setter for parent
     * @param parent
     */
    public void setParent (Person parent) {
        this.parent = parent;
    }

    /**
     * getter for name
     * @return
     */
    public String getName () {
        return name;
    }

    /**
     * Finds if the given person has parent defined
     * @return true, if has parent
     */
    public boolean hasParent() {
        if (this.parent != null) {
            return true;
        } else if (this.partner != null && this.partner.parent != null) {
            return true;
        }
        return false;
    }

    /**
     * Method to find if the person has daughters
     * @return true, if person has daughters
     */
    public boolean hasDaughters() {
        return noOfDaughters() > 0;
    }

    /**
     * Method to find if person is female
     * @return true, if person is female
     */
    public boolean isFemale() {
        return this.sex.equalsIgnoreCase("female");
    }

    /**
     * Method to find number of daughters for the Person
     * @return number of daughters
     */
    public int noOfDaughters () {
        return this.daughters().size();
    }

    /**
     * Add child to the given person
     * @param child child object
     */
    public void addChild(Person child) {
        this.children.add(child);
    }

    /**
     * Add child to the given person
     * The logic is add children to the first person. This is to be consistent how JSON input file is initialized.
     * @param childName child name
     * @param childSex child sex
     */
    public void addChild(String childName, String childSex) {
        Person child = new Person(childName, childSex);
        if (!this.children.isEmpty()) {
            this.addChild(child);
            child.setParent(this);
        } else if (this.partner != null
                && !this.partner.children.isEmpty()) {
            this.partner.addChild(child);
            child.setParent(this.partner);
        } else if (this.parent != null) {
            this.addChild(child);
            child.setParent(this);
        } else if (this.partner != null && this.partner.parent != null) {
            this.partner.addChild(child);
            child.setParent(this.partner);
        } else {
            this.addChild(child);
            child.setParent(this);
        }
        FamilyTree.addPersonToMap(child);

    }

    /**
     *
     * @return List of Brothers
     */
    public List<Person> brothers() {
        if (this.parent != null) {
            List<Person> children = this.parent.children;
            return children.stream()
                    .filter( c -> c.name != this.name && (c.sex).equalsIgnoreCase("male"))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     *
     * @return List of sisters
     */
    public List<Person> sisters() {
        if (this.parent != null) {
            List<Person> children = this.parent.children;
            return children.stream()
                           .filter( c -> c.name != this.name && (c.sex).equalsIgnoreCase("female"))
                           .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public Person father() {
        Person father = this.parent;
        if (father == null) {
            father = this.partner.parent;
        }

        if (father.sex.equalsIgnoreCase("male")) {
            return father;
        } else {
            return father.partner;
        }
    }

    public Person mother() {
        Person mother = this.parent;
        if (mother == null) {
            mother = this.partner.parent;
        }
        if (mother != null) {
            if (mother.sex.equalsIgnoreCase("female")) {
                return mother;
            } else if (mother.partner != null){
                return mother.partner;
            }
        }
        return null;
    }

    public List<Person> children() {
        if (!this.children.isEmpty()) {
            return this.children;
        } else if (this.partner != null && !this.partner.children.isEmpty()) {
            return this.partner.children;
        }
        return new ArrayList<>();
    }

    public List<Person> sons() {
        return this.children().stream()
                .filter(c -> c.sex.equalsIgnoreCase("male"))
                .collect(Collectors.toList());
    }

    public List<Person> daughters() {
        return this.children().stream()
                .filter(c -> c.sex.equalsIgnoreCase("female"))
                .collect(Collectors.toList());
    }

    public List<Person> grandDaughters() {
        List<Person> daughters = new ArrayList<>();
        for (Person children : this.children()) {
            daughters.addAll(
                    children.children().stream()
                            .filter(c -> c.sex.equalsIgnoreCase("female"))
                            .collect(Collectors.toList())
            );
        }
        return daughters;
    }

    public List<Person> cousins() {
        List<Person> cousins = new ArrayList<>();
        for (Person brother : this.parent.brothers()) {
            cousins.addAll(brother.children);
        }

        for (Person sister : this.parent.sisters()) {
            cousins.addAll(sister.children);
        }
        return cousins;
    }

    public List<Person> brotherInLaws() {
        List<Person> brotherInLaws = new ArrayList<>();
        //Spouse’s brothers
        if (this.partner != null) {
            brotherInLaws.addAll(this.partner.brothers());
        }

        //Husbands of siblings
        for (Person sibling : this.sisters()) {
            if (sibling.partner != null) {
                brotherInLaws.add(sibling.partner);
            }
        }
        return brotherInLaws;
    }

    public List<Person> sisterInLaws() {
        List<Person> sisterInLaws = new ArrayList<>();
        //Spouse’s sisters,
        if (this.partner != null) {
            sisterInLaws.addAll(this.partner.sisters());
        }

        //Wives of siblings
        for (Person sibling : this.brothers()) {
            if (sibling.partner != null) {
                sisterInLaws.add(sibling.partner);
            }
        }
        return sisterInLaws;
    }
    
    public List<Person> maternalAunts() {
        List<Person> maternalAunts = new ArrayList<>();
        //Mother’s sister
        maternalAunts.addAll(this.mother().sisters());

        //Mother’s sister-in-laws
        maternalAunts.addAll(this.mother().sisterInLaws());
        return maternalAunts;
    }

    public List<Person> paternalAunts() {
        List<Person> paternalAunts = new ArrayList<>();
        Person father = this.father();
        if (father != null) {
            //Father’ sister
            paternalAunts.addAll(father.sisters());

            //Father’s sister-in-laws
            paternalAunts.addAll(father.sisterInLaws());
        }
        return paternalAunts;
    }

    public List<Person> maternalUncles() {
        List<Person> maternalUncles = new ArrayList<>();
        Person mother = this.mother();
        if (mother != null) {
            //Mother’s brother
            maternalUncles.addAll(mother.brothers());

            //Mother’s brother-in-laws
            maternalUncles.addAll(mother.brotherInLaws());
        }
        return maternalUncles;
    }

    public List<Person> paternalUncles() {
        List<Person> paternalUncles = new ArrayList<>();
        Person father = this.father();
        if (father != null) {
            //Father’s brothers
            paternalUncles.addAll(father.brothers());

            //Father’s brother-inlaws
            paternalUncles.addAll(father.brotherInLaws());
        }
        return paternalUncles;
    }
}