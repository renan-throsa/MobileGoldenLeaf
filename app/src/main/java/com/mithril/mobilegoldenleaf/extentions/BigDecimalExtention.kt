package com.mithril.mobilegoldenleaf.extentions

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

fun BigDecimal.toBrazilianFormat(): String {
    val brazilianFormat = DecimalFormat.getCurrencyInstance(Locale("pt", "br"))
    return brazilianFormat.format(this).replace("R$", "R$ ")
}

fun BigDecimal.toDecimalFormat(): String {
    val brazilianFormat = DecimalFormat.getCurrencyInstance(Locale("pt", "br"))
    return brazilianFormat.format(this)
}