package it.univr.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StackTests {
	private Stack stack;

	@Before
	public void setup() {
		stack = new Stack();
	}

	@Test
	public void testStackEmptiness() {
		assertTrue("Stack is empty after being created", stack.isEmpty());
	}

	@Test
	public  void testPushPop() {
		stack.push(4);

		assertEquals("After a push() of an integer A, A is returned by pop()", 4, stack.pop());
	}
}
