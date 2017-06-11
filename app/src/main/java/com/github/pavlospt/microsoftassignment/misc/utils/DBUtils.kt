package com.github.pavlospt.microsoftassignment.misc.utils


object DBUtils {
  fun generateWhereStatement(columns: List<String>, termsSize: Int): String {
    val columnsSize = columns.size
    var finalWhereStatement = ""

    for (i in 0 until columnsSize) {
      finalWhereStatement += generateLikePerColumn(columns[i], termsSize)
      if (isNotLastStep(i, columnsSize)) finalWhereStatement += " OR "
    }

    return finalWhereStatement
  }

  fun generateLikePerColumn(columnName: String, termsSize: Int): String {
    var finalLikePerColumn = "("

    for (i in 0 until termsSize) {
      finalLikePerColumn += "$columnName LIKE ?" + (if (isNotLastStep(i, termsSize)) " " else "")
      if (isNotLastStep(i, termsSize)) finalLikePerColumn += "OR "
    }

    finalLikePerColumn += ")"

    return finalLikePerColumn
  }

  fun generateWrappedTermsList(terms: List<String>, columnsSize: Int): List<String> {
    val generatedTermsList = mutableListOf<String>()

    for (i in 0 until columnsSize) {
      generatedTermsList.addAll(terms.map { wrapTerm(it) })
    }

    return generatedTermsList.toList()
  }

  fun wrapTerm(term: String) = "%$term%"

  private fun isNotLastStep(currentPos: Int, size: Int) = currentPos < size - 1
}