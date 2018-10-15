package com.github.djumanoff.scala.nlp

/**
  * This trait is responsible for life management of
  * NLP agent. Depending on service that was used it may create
  * or close agent.
  */
trait NLPProvider {

  def createAgent(): NLPAgent

  def closeAgent(agent: NLPAgent): Unit = {
    agent.close()
  }
}
