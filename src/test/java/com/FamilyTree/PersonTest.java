package com.FamilyTree;

import org.junit.Test;
import static org.junit.Assert.*;

public class PersonTest {
    private FamilyTree familyTree;

    public PersonTest () {
        familyTree  = new FamilyTree();
    }

    @Test
    public void testBrothers() {
        Person ish = familyTree.getPerson("Ish");
        assertEquals(2, ish.brothers().size());
    }

    @Test
    public void testSisters() {
        Person ish = familyTree.getPerson("Ish");
        assertEquals(1, ish.sisters().size());
    }

    @Test
    public void testFather() {
        Person ish = familyTree.getPerson("Ish");
        assertEquals("King Shan", ish.father().getName());
    }

    @Test
    public void testMother() {
        Person ish = familyTree.getPerson("Ish");
        assertEquals("Queen Anga", ish.mother().getName());
    }

    @Test
    public void testChildren() {
        Person kingShan = familyTree.getPerson("King Shan");
        assertEquals(4, kingShan.children().size());
    }

    @Test
    public void testSon() {
        Person kingShan = familyTree.getPerson("King Shan");
        assertEquals(3, kingShan.sons().size());
    }

    @Test
    public void testDaughter() {
        Person kingShan = familyTree.getPerson("King Shan");
        assertEquals(1, kingShan.daughters().size());
    }

    @Test
    public void testGrandDaughter() {
        Person kingShan = familyTree.getPerson("King Shan");
        assertEquals(2, kingShan.grandDaughters().size());
    }

    @Test
    public void testCousins() {
        Person drita = familyTree.getPerson("Drita");
        assertEquals(5, drita.cousins().size());
    }

    @Test
    public void testBrotherInLaw() {
        Person ambi = familyTree.getPerson("Ambi");
        assertEquals(2, ambi.brotherInLaws().size());

        Person jaya = familyTree.getPerson("Jaya");
        assertEquals(1, jaya.brotherInLaws().size());
    }

    @Test
    public void testSisterInLaw() {
        Person ambi = familyTree.getPerson("Ambi");
        assertEquals(1, ambi.sisterInLaws().size());

        Person jaya = familyTree.getPerson("Jaya");
        assertEquals(0, jaya.sisterInLaws().size());
    }

    @Test
    public void testMaternalAunt() {
        Person lavnya = familyTree.getPerson("Lavnya");
        assertEquals(1, lavnya.maternalAunts().size());

        Person jaya = familyTree.getPerson("Jaya");
        assertEquals(0, jaya.sisterInLaws().size());
    }

    @Test
    public void testPaternalAunt() {
        Person drita = familyTree.getPerson("Drita");
        assertEquals(2, drita.paternalAunts().size());

        Person jaya = familyTree.getPerson("Jaya");
        assertEquals(2, jaya.paternalAunts().size());
    }

    @Test
    public void testMaternalUncle() {
        Person satvy = familyTree.getPerson("Satvy");
        assertEquals(3, satvy.maternalUncles().size());

        Person jaya = familyTree.getPerson("Jaya");
        assertEquals(2, jaya.maternalUncles().size());
    }

    @Test
    public void testPaternalUncle() {
        Person satvy = familyTree.getPerson("Satvy");
        assertEquals(3, satvy.paternalUncles().size());

        Person jaya = familyTree.getPerson("Jaya");
        assertEquals(3, jaya.paternalUncles().size());
    }

}
