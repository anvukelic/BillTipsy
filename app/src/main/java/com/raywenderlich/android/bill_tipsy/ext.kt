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
import android.text.Selection
import android.text.Spannable
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

const val NUMBER_OF_FRACTION_DIGITS = 2

fun Int.formatToPercentage(): String = String.format("%d %%", this)

fun String.parsePercentage(): Int = with(this.replace("%", "").trim()) {
  if (this.isEmpty()) 0 else toInt()
}

fun Double.formatToNumber(): String {
  val numberFormat = DecimalFormat.getInstance(Locale.getDefault()).apply {
    maximumFractionDigits = NUMBER_OF_FRACTION_DIGITS
    minimumFractionDigits = NUMBER_OF_FRACTION_DIGITS
  }
  return numberFormat.format(this)
}

fun Double.formatToAmount(): String {
  val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
  numberFormat.currency = Currency.getInstance(Locale.getDefault())
  return numberFormat.format(this / 100)
}

fun String.parseAmount(): Double {
  if (this.isEmpty()) {
    return 0.0
  }
  val symbol = Currency.getInstance(Locale.getDefault()).symbol
  return this.replace("""[$symbol,.\s]""".toRegex(), "").toDouble()
}

fun Editable.moveSelection() {
  val newSelectionPosition = this.getSpanStart(Selection.SELECTION_START) + 1
  if (newSelectionPosition < this.length) {
    this.setSpan(Selection.SELECTION_START, newSelectionPosition, newSelectionPosition,
        Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
    this.setSpan(Selection.SELECTION_END, newSelectionPosition, newSelectionPosition,
        Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
  }
}
