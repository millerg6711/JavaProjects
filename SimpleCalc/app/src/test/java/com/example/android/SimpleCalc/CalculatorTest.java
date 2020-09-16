/*
 * Copyright 2018, Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.SimpleCalc;

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

/**
 * JUnit4 unit tests for the calculator logic. These are local unit tests; no device needed
 */
@RunWith(JUnit4.class)
@SmallTest
public class CalculatorTest {

    private Calculator mCalculator;

    /**
     * Set up the environment for testing
     */
    @Before
    public void setUp() {
        mCalculator = new Calculator();
    }

    /**
     * Test for simple addition
     */
    @Test
    public void addTwoNumbers() {
        double resultAdd = mCalculator.add(1d, 1d);
        assertThat(resultAdd, is(equalTo(2d)));
    }

    @Test
    public void addPositiveAndNegative() {
        double result = mCalculator.add(1d, -1d);
        assertThat(result, is(equalTo(0d)));
    }

    @Test
    public void addFloatingPoint() {
        double result = mCalculator.add(.1, .2);
        assertThat(result, is(closeTo(.3, .0005)));
    }

    @Test
    public void addTwoNumbersNegative() {
        double resultAdd = mCalculator.add(-1d, 2d);
        assertThat(resultAdd, is(equalTo(1d)));
    }
    @Test
    public void addTwoNumbersFloats() {
        double resultAdd = mCalculator.add(1.111f, 1.111d);
        assertThat(resultAdd, is(closeTo(2.222, 0.01)));
    }
    @Test
    public void subTwoNumbers() {
        double resultSub = mCalculator.sub(1d, 1d);
        assertThat(resultSub, is(equalTo(0d)));
    }
    @Test
    public void subWorksWithNegativeResult() {
        double resultSub = mCalculator.sub(1d, 17d);
        assertThat(resultSub, is(equalTo(-16d)));
    }
    @Test
    public void mulTwoNumbers() {
        double resultMul = mCalculator.mul(32d, 2d);
        assertThat(resultMul, is(equalTo(64d)));
    }
    @Test
    public void divTwoNumbers() {
        double resultDiv = mCalculator.div(32d,2d);
        assertThat(resultDiv, is(equalTo(16d)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void divByZeroThrows() {
        mCalculator.div(5d, 0);
    }

    @Test
    public void powPositiveIntOperands() {
        double pow = mCalculator.pow(5d, 5d);
        assertThat(pow, is(equalTo(3125d)));
    }

    @Test
    public void powNegativeIntAsFirstOperand() {
        assertThat(mCalculator.pow(-5d, 2d), is(equalTo(25d)));
    }

    @Test
    public void powNegativeIntAsSecondOperand() {
        assertThat(mCalculator.pow(5d, -2d), is(equalTo(0.04d)));
    }

    @Test
    public void powZeroAsFirstOperandAndPositiveIntAsSecondOperand() {
        assertThat(mCalculator.pow(0d, 5d), is(equalTo(0d)));
    }

    @Test
    public void powZeroAsSecondOperand() {
        assertThat(mCalculator.pow(5d, 0d), is(equalTo(1d)));
    }

    @Test
    public void powZeroAsFirstOperandAndNegativeOneAsSecondOperand() {
        assertThat(mCalculator.pow(0d, -1d), is(equalTo(Double.POSITIVE_INFINITY)));
    }

    @Test
    public void powNegativeZeroAsFirstOperandAndAnyNegativeNumberAsSecondOperand() {
        assertThat(mCalculator.pow(-0d, -2d), is(equalTo(Double.POSITIVE_INFINITY)));
    }
}