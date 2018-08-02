package com.github.djumanoff.scala.nlp.dialogflow

import com.github.djumanoff.scala.nlp.NLPException

final class DFNLPException(message: String, cause: Option[Throwable] = None) extends NLPException(message, cause) {

}
