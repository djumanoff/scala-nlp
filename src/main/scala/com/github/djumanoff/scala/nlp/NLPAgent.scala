package com.github.djumanoff.scala.nlp

trait NLPAgent {

  def getSession(sessionId: Option[String] = None): NLPSession

  def closeSession(sessionId: String): Unit

  def close(): Unit
}
