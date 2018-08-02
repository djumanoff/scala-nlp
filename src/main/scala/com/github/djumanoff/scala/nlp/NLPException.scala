package com.github.djumanoff.scala.nlp

class NLPException(message: String, cause: Option[Throwable] = None) extends Exception(message, cause.orNull) {

}
