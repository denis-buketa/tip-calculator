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

import android.graphics.PorterDuff
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.raywenderlich.android.tipcalculator.utils.NumberUtils
import com.raywenderlich.android.tipcalculator.utils.StringUtils
import com.raywenderlich.android.tipcalculator.utils.UserInputFilter
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main Screen contains input fields and button to calculate the result. It also shows the result.
 */
class MainActivity : AppCompatActivity() {

  companion object {
    const val EMPTY_RESULT = "-"
    const val DEFAULT_TIP_PERCENT = 20.00f
    const val DEFAULT_PARTY_COUNT = 4

    const val BILL_INPUT_FILTER_REGEX = "\\$?(0|[1-9][0-9]{0,4})?(\\.[0-9]{0,2})?"
    const val TIP_INPUT_FILTER_REGEX = "(0|[1-9][0-9]?)?(\\.[0-9]{0,2})?%?"
    const val PARTY_COUNT_FILTER_REGEX = "([1-9]|[1-8][0-9]|9[0-9]|100)?"

    const val BILL_FORMAT = "$%.2f"
    const val TIP_FORMAT = "%.2f%%"
  }

  private val stringUtils = StringUtils()
  private val numberUtils = NumberUtils()

  private val calculateButtonColor by lazy { ContextCompat.getColor(this, R.color.colorPrimary) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    initCalculateButton()
    initInputFields()
    initDefaultValues()
  }

  private fun initCalculateButton() {

    // Set Button's color
    calculateButton.background.setColorFilter(calculateButtonColor, PorterDuff.Mode.MULTIPLY)

    // Set Button's OnClick listener
    calculateButton.setOnClickListener {
      clearInputFieldsFocus()
      calculateResult()
    }
  }

  private fun initInputFields() {

    // Initialize Bill Input field
    initInputField(billEditText, BILL_INPUT_FILTER_REGEX, BILL_FORMAT)

    // Initialize Tip Input field
    initInputField(tipEditText, TIP_INPUT_FILTER_REGEX, TIP_FORMAT)

    // Initialize Party Count Input field
    partyEditText.filters = arrayOf(UserInputFilter(PARTY_COUNT_FILTER_REGEX))
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

        val inputValue = inputEditText.text.toString()

        if (hasFocus.not()) {
          // If Input Field lost focus, format the value

          // Set User friendly value
          val value = stringUtils.formatToNumericDecimalValue(inputValue).toFloat()
          val readableValue = String.format(format, numberUtils.roundUpToTwoDecimalPlaces(value))
          inputEditText.setText(readableValue)

        } else if (hasFocus) {
          // If Input Field regained focus, remove any unnecessary characters

          // Remove every character but numeric ones ([0-9]) and the decimal dot (.)
          inputEditText.setText(stringUtils.formatToNumericDecimalValue(inputValue))
        }
      }
    }
  }

  private fun initDefaultValues() {

    // Set Default Tip value
    val defaultTipValue = numberUtils.roundUpToTwoDecimalPlaces(DEFAULT_TIP_PERCENT)
    val defaultTip = String.format(TIP_FORMAT, defaultTipValue)
    tipEditText.setText(defaultTip)

    // Set Default Party Count value
    partyEditText.setText(DEFAULT_PARTY_COUNT.toString())
  }

  private fun clearInputFieldsFocus() {
    billEditText.clearFocus()
    tipEditText.clearFocus()
    partyEditText.clearFocus()
  }

  private fun calculateResult() {
    if (inputValuesAreValid()) {

      // Read input values
      val billValue = stringUtils
        .formatToNumericDecimalValue(billEditText.text.toString())
        .toFloat()
      val tipValue = stringUtils.formatToNumericDecimalValue(tipEditText.text.toString()).toFloat()
      val partyCount = partyEditText.text.toString().toInt()

      // Calculate results
      val total = billValue * (1 + tipValue / 100)
      val perPerson = numberUtils.roundUpToTwoDecimalPlaces(total / partyCount)

      // Show result to the User
      totalValueTextView.text = String.format(BILL_FORMAT, total)
      perPersonValueTextView.text = String.format(BILL_FORMAT, perPerson)
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
