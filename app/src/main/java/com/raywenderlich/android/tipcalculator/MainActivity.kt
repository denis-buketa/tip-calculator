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

    // Initialize bill input field
    initInputField(billEditText, BILL_INPUT_FILTER_REGEX, BILL_FORMAT)

    // Initialize tip input field
    initInputField(tipEditText, TIP_INPUT_FILTER_REGEX, TIP_FORMAT)

    // Initialize party count input field
    partyEditText.filters = arrayOf(
        UserInputFilter(
            PARTY_COUNT_FILTER_REGEX
        )
    )

    // Set default tip value
    val defaultTipValue = numberUtils.roundUpToTwoDecimalPlaces(DEFAULT_TIP_PERCENT)
    val defaultTip = String.format(TIP_FORMAT, defaultTipValue)
    tipEditText.setText(defaultTip)

    // Set default party count value
    partyEditText.setText(DEFAULT_PARTY_COUNT.toString())

    // Set calculate button's onClick listener
    calculateButton.setOnClickListener {

      // Clear input fields' focuses
      billEditText.clearFocus()
      tipEditText.clearFocus()
      partyEditText.clearFocus()

      calculateResult()
    }
  }

  /**
   * Initializes the input field with its [InputFilter] and value format.
   */
  private fun initInputField(inputEditText: EditText, filter: String, format: String) {

    // Set inputFilter
    inputEditText.filters = arrayOf(UserInputFilter(filter))

    // Set specific actions depending on the input field's focus
    inputEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->

      if (inputEditText.text.isNotEmpty()) {

        // Remove any non-numeric values ([0-9]) and the decimal dot (.)
        val inputAmount = stringUtils.formatToNumericDecimalValue(inputEditText.text.toString())

        if (hasFocus.not()) {

          // Format the input amount to user-friendly value when the input field loses its focus
          val readableValue = String.format(
              format,
              numberUtils.roundUpToTwoDecimalPlaces(inputAmount.toFloat())
          )
          inputEditText.setText(readableValue)

        } else if (hasFocus) {

          // Set numeric value when the input field regains its focus
          inputEditText.setText(inputAmount)
        }
      }
    }
  }

  private fun calculateResult() {
    if (inputValuesAreValid()) {

      // Read input values
      val billValue = stringUtils
          .formatToNumericDecimalValue(billEditText.text.toString())
          .toFloat()
      val tipValue = stringUtils.formatToNumericDecimalValue(tipEditText.text.toString()).toFloat()
      val partyCountValue = partyEditText.text.toString().toInt()

      // Calculate results
      val totalValue = billValue * (1 + tipValue / 100)
      val perPersonValue = numberUtils.roundUpToTwoDecimalPlaces(totalValue / partyCountValue)

      // Show result to the User
      totalValueTextView.text = String.format(BILL_FORMAT, totalValue)
      perPersonValueTextView.text = String.format(BILL_FORMAT, perPersonValue)
    } else {

      // Show empty result
      totalValueTextView.text = EMPTY_RESULT
      perPersonValueTextView.text = EMPTY_RESULT
    }
  }

  private fun inputValuesAreValid() = billEditText.text.isNotEmpty() &&
      tipEditText.text.isNotEmpty() &&
      partyEditText.text.isNotEmpty() &&
      partyEditText.text.toString().toInt() > 0
}
