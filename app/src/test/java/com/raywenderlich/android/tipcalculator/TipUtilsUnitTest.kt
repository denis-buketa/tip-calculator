/*
 * Copyright (c) 2019 Razeware LLC
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish, 
 * distribute, sublicense, create a derivative work, and/or sell copies of the 
 * Software in any work that is designed, intended, or marketed for pedagogical or 
 * instructional purposes related to programming, coding, application development, 
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works, 
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.raywenderlich.android.tipcalculator

import com.raywenderlich.android.tipcalculator.utils.tiputils.TipUtils
import com.raywenderlich.android.tipcalculator.utils.tiputils.TipUtilsImpl
import org.junit.Assert
import org.junit.Test

/**
 * Test for the [TipUtils]
 */
class TipUtilsUnitTest {

  private val tipUtils = TipUtilsImpl()

  @Test
  fun `Number is rounded to two decimal places`() {

    Assert.assertEquals(0.00f, tipUtils.roundUpToTwoDecimalPlaces(0f))
    Assert.assertEquals(0.00f, tipUtils.roundUpToTwoDecimalPlaces(0.00f))
    Assert.assertEquals(0.12f, tipUtils.roundUpToTwoDecimalPlaces(0.121f))
    Assert.assertEquals(0.12f, tipUtils.roundUpToTwoDecimalPlaces(0.123f))
    Assert.assertEquals(0.13f, tipUtils.roundUpToTwoDecimalPlaces(0.125f))
    Assert.assertEquals(0.13f, tipUtils.roundUpToTwoDecimalPlaces(0.127f))
    Assert.assertEquals(0.13f, tipUtils.roundUpToTwoDecimalPlaces(0.129f))

    Assert.assertEquals(5.00f, tipUtils.roundUpToTwoDecimalPlaces(5f))
    Assert.assertEquals(5.00f, tipUtils.roundUpToTwoDecimalPlaces(5.00f))
    Assert.assertEquals(5.12f, tipUtils.roundUpToTwoDecimalPlaces(5.121f))
    Assert.assertEquals(5.12f, tipUtils.roundUpToTwoDecimalPlaces(5.123f))
    Assert.assertEquals(5.13f, tipUtils.roundUpToTwoDecimalPlaces(5.125f))
    Assert.assertEquals(5.13f, tipUtils.roundUpToTwoDecimalPlaces(5.127f))
    Assert.assertEquals(5.13f, tipUtils.roundUpToTwoDecimalPlaces(5.129f))

    Assert.assertEquals(10.00f, tipUtils.roundUpToTwoDecimalPlaces(10f))
    Assert.assertEquals(10.00f, tipUtils.roundUpToTwoDecimalPlaces(10.00f))
    Assert.assertEquals(10.12f, tipUtils.roundUpToTwoDecimalPlaces(10.121f))
    Assert.assertEquals(10.12f, tipUtils.roundUpToTwoDecimalPlaces(10.123f))
    Assert.assertEquals(10.13f, tipUtils.roundUpToTwoDecimalPlaces(10.125f))
    Assert.assertEquals(10.13f, tipUtils.roundUpToTwoDecimalPlaces(10.127f))
    Assert.assertEquals(10.13f, tipUtils.roundUpToTwoDecimalPlaces(10.129f))
  }

  @Test
  fun `Number is mapped to readable tip format`() {

    Assert.assertEquals("0.00%", tipUtils.mapToReadableFormat(0f))
    Assert.assertEquals("0.00%", tipUtils.mapToReadableFormat(0.00f))
    Assert.assertEquals("0.12%", tipUtils.mapToReadableFormat(0.121f))
    Assert.assertEquals("0.12%", tipUtils.mapToReadableFormat(0.123f))
    Assert.assertEquals("0.13%", tipUtils.mapToReadableFormat(0.125f))
    Assert.assertEquals("0.13%", tipUtils.mapToReadableFormat(0.127f))
    Assert.assertEquals("0.13%", tipUtils.mapToReadableFormat(0.129f))

    Assert.assertEquals("5.00%", tipUtils.mapToReadableFormat(5f))
    Assert.assertEquals("5.00%", tipUtils.mapToReadableFormat(5.00f))
    Assert.assertEquals("5.12%", tipUtils.mapToReadableFormat(5.121f))
    Assert.assertEquals("5.12%", tipUtils.mapToReadableFormat(5.123f))
    Assert.assertEquals("5.13%", tipUtils.mapToReadableFormat(5.125f))
    Assert.assertEquals("5.13%", tipUtils.mapToReadableFormat(5.127f))
    Assert.assertEquals("5.13%", tipUtils.mapToReadableFormat(5.129f))

    Assert.assertEquals("10.00%", tipUtils.mapToReadableFormat(10f))
    Assert.assertEquals("10.00%", tipUtils.mapToReadableFormat(10.00f))
    Assert.assertEquals("10.12%", tipUtils.mapToReadableFormat(10.121f))
    Assert.assertEquals("10.12%", tipUtils.mapToReadableFormat(10.123f))
    Assert.assertEquals("10.13%", tipUtils.mapToReadableFormat(10.125f))
    Assert.assertEquals("10.13%", tipUtils.mapToReadableFormat(10.127f))
    Assert.assertEquals("10.13%", tipUtils.mapToReadableFormat(10.129f))
  }
}