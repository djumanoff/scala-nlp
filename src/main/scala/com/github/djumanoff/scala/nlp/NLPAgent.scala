package com.github.djumanoff.scala.nlp

/**
  * This trait is responsible for life management of session inside NLP agent.
  * Depending on which NLP service is used, realization implements
  * it's own ways of creating and closing session.
  */
trait NLPAgent {

  def createSession(sessionId: Option[String] = None): NLPSession

  def closeSession(sessionId: String): Unit

  def close(): Unit
}
