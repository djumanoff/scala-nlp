package com.github.djumanoff.scala.nlp

/**
  * Represents message that will be handled by NLP Agent.
  * Amount of arguments may be increased in future
  * @param text message itself
  * @param lang language in which message was written
  */
case class HumanMessage(text: String, lang: String, sessionId: Option[String] = None)
