/*
 * Copyright (c) 2020 Razeware LLC
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
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.bill_tipsy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raywenderlich.android.billtipsy.R
import kotlinx.android.synthetic.main.activity_main.*

private const val KEY_BILL_AMOUNT = "key_bill_amount"
private const val KEY_TIP_PERCENT = "key_tip_percent"

/**
 * Main Screen of the app.
 */
class MainActivity : AppCompatActivity() {

  private var _billAmount: Double = 0.0
  private var _tipPercentage: Int = 15

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putDouble(KEY_BILL_AMOUNT, _billAmount)
    outState.putInt(KEY_TIP_PERCENT, _tipPercentage)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Handle saved instance state if exists
    savedInstanceState?.let { restoreState(it) } ?: initiateValues()

    // Listen for the changes in Tip percentage input
    setTipPercentageTextWatcher()

    // Listen for the changes in Bill amount input
    setBillAmountTextWatcher()

    // Set actions to the percentage decrement/increment buttons
    setTipPercentageControlClickListeners()
  }

  private fun restoreState(savedInstanceState: Bundle) {
    // Read the values after activity recreates
    _billAmount = savedInstanceState.getDouble(KEY_BILL_AMOUNT)
    _tipPercentage = savedInstanceState.getInt(KEY_TIP_PERCENT)
  }

  private fun initiateValues() {
    // Initialize text fields with localized formatted default values
    billAmountInput.setText(_billAmount.formatToNumber())
    tipPercentageInput.setText(_tipPercentage.formatToPercentage())
    tipAmount.text = (0.0.formatToAmount())
    totalAmount.text = (0.0.formatToAmount())
  }

  private fun setBillAmountTextWatcher() {
    billAmountInput.addTextChangedListener(BillAmountTextWatcher { newBillAmount ->
      _billAmount = newBillAmount
      calculateTipAndTotalAmount()
    })
  }

  private fun setTipPercentageTextWatcher() {
    tipPercentageInput.addTextChangedListener(TipPercentageTextWatcher { newTipPercentage ->
      _tipPercentage = newTipPercentage
      calculateTipAndTotalAmount()
    })
  }

  private fun setTipPercentageControlClickListeners() {
    incrementTipPercentage.setOnClickListener {
      doOnTipPercentageControlButton(true)
    }

    decrementTipPercentage.setOnClickListener {
      doOnTipPercentageControlButton(false)
    }
  }

  private fun doOnTipPercentageControlButton(shouldIncrement: Boolean) {
    _tipPercentage = if (shouldIncrement) _tipPercentage.inc() else _tipPercentage.dec()
    tipPercentageInput.setText(_tipPercentage.toString())
    calculateTipAndTotalAmount()
  }

  private fun calculateTipAndTotalAmount() {
    // Calculate tip amount
    val _tipAmount = _billAmount * (_tipPercentage / 100.toDouble())

    // Update rest of the fields
    tipAmount.text = _tipAmount.formatToAmount()
    totalAmount.text = (_billAmount + _tipAmount).formatToAmount()
    decrementTipPercentage.isEnabled = _tipPercentage > 0
  }
}
