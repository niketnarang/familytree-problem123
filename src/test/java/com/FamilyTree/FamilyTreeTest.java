package com.FamilyTree;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FamilyTreeTest {

    private FamilyTree familyTree;
    public FamilyTreeTest () {
        familyTree  = new FamilyTree();
    }

    @Test
    public void testCreateFamilyTree() {
        Person ish = familyTree.getPerson("Ish");
        assertEquals("King Shan", ish.father().getName());
    }

    @Test
    public void testAddNewChild() {
        Person saayan = familyTree.getPerson("Saayan");
        assertEquals(0, saayan.noOfDaughters());
        saayan.addChild("drini", "female");
        assertEquals(1, saayan.noOfDaughters());

        assertEquals(1, saayan.sons().size());
        saayan.addChild("chit jr", "male");
        assertEquals(2, saayan.sons().size());
    }

    @Test
    public void testMothersWithMaxDaughters() {
        assertEquals(5, familyTree.findMothersWithMaxGirlChild().size());
        Person jaya = familyTree.getPerson("Jaya");
        jaya.addChild("drini", "female");
        assertEquals(1, familyTree.findMothersWithMaxGirlChild().size());
    }
}
