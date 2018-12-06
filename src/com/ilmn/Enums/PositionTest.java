package com.ilmn.Enums;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class PositionTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void ctor_parses_A1() {
        Position pos = new Position("A1");
        assertEquals(0, pos.x);
        assertEquals(0, pos.y);
    }

    @Test
    public void ctor_parses_D1() {
        Position pos = new Position("D1");
        assertEquals(0, pos.x);
        assertEquals(3, pos.y);
    }

    @Test
    public void ctor_parses_A4() {
        Position pos = new Position("A4");
        assertEquals(3, pos.x);
        assertEquals(0, pos.y);
    }

    @Test
    public void ctor_throws_F1() {
        exception.expect(IndexOutOfBoundsException.class);
        Position pos = new Position("F1");
    }

    @Test
    public void ctor_throws_A6() {
        exception.expect(IndexOutOfBoundsException.class);
        Position pos = new Position("A6");
    }

    @Test
    public void ctor_throws_1D() {
        exception.expect(IndexOutOfBoundsException.class);
        Position pos = new Position("1D");
    }

    @Test
    public void ctor_throws_11() {
        exception.expect(IndexOutOfBoundsException.class);
        Position pos = new Position("11");
    }
}