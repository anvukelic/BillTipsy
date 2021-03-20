/*
 * Copyright (c) 2021 Razeware LLC
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

import android.text.Editable
import android.text.TextWatcher

private const val MAXIMUM_VALUE = 1000000.00

/**
 * This class is part of the starter project.
 * Student will apply it to the Bill Amount EditText.
 */
class BillAmountTextWatcher(
    private val onNewAmount: (Double) -> Unit
) : TextWatcher {

  var isEditing: Boolean = false

  override fun beforeTextChanged(s: CharSequence, p0: Int, p1: Int, p2: Int) {}

  override fun onTextChanged(s: CharSequence, p0: Int, p1: Int, p2: Int) {}

  override fun afterTextChanged(editable: Editable) {
    if (isEditing.not()) {
      isEditing = true
      val billAmount = editable.toString().parseAmount() / 100

      val isValidAmount = billAmount <= MAXIMUM_VALUE

      val formattedAmount = if (isValidAmount) billAmount.formatToNumber() else MAXIMUM_VALUE.formatToNumber()
      val shouldMoveSelection = formattedAmount.length > editable.length
      editable.replace(0, editable.length, formattedAmount)

      if (shouldMoveSelection) {
        editable.moveSelection()
      }

      onNewAmount.invoke(formattedAmount.parseAmount())
      isEditing = false
    }
  }
}