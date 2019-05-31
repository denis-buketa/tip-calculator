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
package com.raywenderlich.android.tipcalculator.utils.currencyutils

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Pattern

/**
 * Filters out any input that doesn't comply with the Currency format.
 *
 * Examples of valid inputs:
 * - $10
 * - $10.00
 */
class CurrencyFormatInputFilter : InputFilter {

  companion object {
    const val CURRENCY_FORMAT_INPUT_FILTER_REGEX = "\\$?(0|[1-9][0-9]*)?(\\.[0-9]{0,2})?"
    const val FIRST_CHARACTER_INDEX = 0
  }

  private val stringBuilder = StringBuilder()
  private val pattern = Pattern.compile(CURRENCY_FORMAT_INPUT_FILTER_REGEX)

  override fun filter(
      source: CharSequence,
      start: Int,
      end: Int,
      dest: Spanned,
      dstart: Int,
      dend: Int
  ): CharSequence? {

    stringBuilder.clear()
    val input = stringBuilder
        .append(dest.subSequence(FIRST_CHARACTER_INDEX, dstart))
        .append(source)
        .append(dest.subSequence(dend, dest.length))
        .toString()

    val matcher = pattern.matcher(input)

    return if (!matcher.matches()) dest.subSequence(dstart, dend) else null
  }
}