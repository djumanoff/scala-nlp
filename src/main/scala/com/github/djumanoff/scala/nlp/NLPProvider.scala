package com.github.djumanoff.scala.nlp

trait NLPProvider {

  def createAgent(): NLPAgent

  def closeAgent(agent: NLPAgent): Unit = {
    agent.close()
  }
}
