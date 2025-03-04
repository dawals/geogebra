package org.geogebra.common.kernel.interval.function;

import static org.geogebra.common.kernel.interval.function.IntervalFunction.isSupported;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.geogebra.common.BaseUnitTest;
import org.junit.Ignore;
import org.junit.Test;

public class IntervalFunctionSupportTest extends BaseUnitTest {
	@Test
	public void testSupportedOperations() {
		shouldBeSupported("x + 1");
		shouldBeSupported("x - 1");
		shouldBeSupported("x * 5");
		shouldBeSupported("x / 5");
		shouldBeSupported("x^3");
		shouldBeSupported("nroot(x, 4)");
		shouldBeSupported("sin(x)");
		shouldBeSupported("cos(x)");
		shouldBeSupported("sqrt(x)");
		shouldBeSupported("tan(x)");
		shouldBeSupported("exp(x)");
		shouldBeSupported("log(x)");
		shouldBeSupported("arccos(x)");
		shouldBeSupported("arcsin(x)");
		shouldBeSupported("arctan(x)");
		shouldBeSupported("abs(x)");
		shouldBeSupported("cosh(x)");
		shouldBeSupported("sinh(x)");
		shouldBeSupported("tanh(x)");
		shouldBeSupported("log10(x)");
		shouldBeSupported("log(x)");
		shouldBeSupported("sin(x)^3");
		shouldBeSupported("x * ((1, 1) * (1, 1))");
		shouldBeSupported("sin(x)^(2/3)");
		shouldBeSupported("sin(x)^2.141");
		shouldBeSupported("x^(-2)");
		shouldBeSupported("sin(e^x)");
		shouldBeSupported("2^sin(x)");
		shouldBeSupported("2^x");
		shouldBeSupported("2^(1/x)");
	}

	private void shouldBeSupported(String command) {
		assertTrue(command + " has not supported",
				isSupported(add(command)));
	}

	@Test
	public void testSupportOneVariableOnly() {
		shouldBeNotSupported("x + x");
		shouldBeNotSupported("x^2 + x");
		shouldBeNotSupported("abs(x)/x");
		shouldBeNotSupported("(1/x)sin(x)");
		shouldBeNotSupported("sin(x^4) + x");
		shouldBeNotSupported("tan(x)/x");
		shouldBeNotSupported("x+3x");
	}

	private void shouldBeNotSupported(String command) {
		assertFalse(command + " has supported, but it should not",
				isSupported(add(command)));
	}

	@Test
	public void powerShouldBeNumber() {
		add("v = (1, 2)");
		shouldBeNotSupported("x^v");
		shouldBeNotSupported("abs(x^v)");
		add("A = (1, 2)");
		shouldBeNotSupported("x^A");
	}

	@Test
	public void testUnsupportedOperations() {
		shouldBeNotSupported("x!");
		shouldBeNotSupported("gamma(x)");
		shouldBeNotSupported("x^2x");
		shouldBeNotSupported("(x * (1, 1)) * (1, 1)");
		shouldBeNotSupported("acosh(x)");
		shouldBeNotSupported("sin(x)^(ln(x))");
	}

	@Test
	@Ignore("APPS-4170")
	public void supportIf() {
		shouldBeSupported("If[x < 1, 0]");
		shouldBeSupported("If[x < 1, 2x]");
		shouldBeSupported("If[x < 1, x + 1]");
		shouldBeSupported("If[1 < x, x + 1]");
		shouldBeSupported("If[x != 1, x + 1]");
	}

	@Test
	@Ignore("APPS-4170")
	public void supportIfWithAndInterval() {
		shouldBeSupported("If[0 < x < 1, x + 1]");
		shouldBeSupported("If[0 < x < sin(1), x + 1]");
	}

	@Test
	public void unsupportedIfWithAndInterval() {
		shouldBeNotSupported("If[3x < x < 1, x + 1]");
		shouldBeNotSupported("If[0 < sin(x) < 2, x + 1]");
		shouldBeNotSupported("If[3x < x <= x, x + 1]");
	}

	@Test
	@Ignore("APPS-4170")
	public void supportIfWithConstantFromExpression() {
		shouldBeSupported("If[sin(2) < x, x^2]");
		shouldBeSupported("If[x < sin(2), x^2]");
	}

	@Test
	public void unsupportedIfs() {
		shouldBeNotSupported("If[sin(x) < 0, x + 1]");
		shouldBeNotSupported("If[0 < sin(x), x + 1]");
		shouldBeNotSupported("If[x < 1, 2x + x^3");
		shouldBeNotSupported("If[1 < x, 2x + x^3");
	}

	@Test
	@Ignore("APPS-4170")
	public void supportIfElse() {
		shouldBeSupported("If[x < 1, x, x + 1]");
		shouldBeSupported("If[x != 1, x, x + 1]");
		shouldBeSupported("If[-1 < x < 1, x, x + 1]");
		shouldBeSupported("If[-1 + cos(1) < x < sin(2), x, x + 1]");
	}

	@Test
	public void unsupportedIfElse() {
		shouldBeNotSupported("If[sin(x) < 0, 0, 1]");
		shouldBeNotSupported("If[x < 1, x * sin(x), x + 1]");
		shouldBeNotSupported("If[x < 1, x, x * sin(x)]");
		shouldBeNotSupported("If[x < 1, x/tan(x), x * sin(x)]");
		shouldBeNotSupported("If[x < 1, x * sin(x) + 1]");
	}

	@Test
	@Ignore("APPS-4170")
	public void supportIfList() {
		shouldBeSupported("if(x < -2, -2, x > 0, 4)");
		shouldBeSupported("if(x < -2, -2, x > 0, 4, 2)");
		shouldBeSupported("if(x < -2, x + 1, x > 0, x^4)");
		shouldBeSupported("if(-4 < x < -2, 3x, 2 < x < 4, 4x)");
	}

	@Test
	public void unsupportedIfList() {
		shouldBeNotSupported("if(x < -2, x * (ln(x)), x > 0, x^4)");
		shouldBeNotSupported("if(sin(x) < 0, 1, cos(x) > 0, x^4)");
	}
}
