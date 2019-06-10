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

import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.raywenderlich.android.tipcalculator.filter.UserInputFilter
import com.raywenderlich.android.tipcalculator.utils.NumberUtils
import com.raywenderlich.android.tipcalculator.utils.StringUtils
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main Screen contains input fields and button to calculate the result. It also shows the result.
 */
class MainActivity : AppCompatActivity() {

  companion object {
    const val EMPTY_RESULT = "-"

    const val DEFAULT_TIP_PERCENT = 20.00f
    const val DEFAULT_PARTY_COUNT = 4

    // [$0.00 - $9999.99]
    const val BILL_INPUT_FILTER_REGEX = "\\$?(0|[1-9][0-9]{0,3})?(\\.[0-9]{0,2})?"
    // [0.00% - 99.99%]
    const val TIP_INPUT_FILTER_REGEX = "(0|[1-9][0-9]?)?(\\.[0-9]{0,2})?%?"
    // [1 - 100]
    const val PARTY_COUNT_FILTER_REGEX = "([1-9]|[1-8][0-9]|9[0-9]|100)?"

    const val BILL_FORMAT = "$%.2f"
    const val TIP_FORMAT = "%.2f%%"
  }

  private val stringUtils = StringUtils()
  private val numberUtils = NumberUtils()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }
}
