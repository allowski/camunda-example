package com.camunda.impl.commons

fun String.camelToSnakeCase() = fold(StringBuilder(length)) { acc, c ->
    if (c in 'A'..'Z') (if (acc.isNotEmpty()) acc.append('-') else acc).append(c + ('a' - 'A'))
    else acc.append(c)
}.toString()
