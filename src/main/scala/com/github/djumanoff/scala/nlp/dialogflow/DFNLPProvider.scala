package com.github.djumanoff.scala.nlp.dialogflow

import com.google.cloud.dialogflow.v2.{ContextsClient, SessionsClient}
import com.github.djumanoff.scala.nlp.{NLPAgent, NLPProvider}

object DFNLPProvider {
  def apply(projectId: String): DFNLPProvider = new DFNLPProvider(projectId)
}

class DFNLPProvider(projectId: String) extends NLPProvider {

  override def createAgent(): NLPAgent = {
    try {
      validateGoogleCredentials()

      val sessionClient = SessionsClient.create()
      val contextClient = ContextsClient.create()

      DFNLPAgent(projectId, sessionClient, contextClient)
    } catch {
      case e: Exception =>
        throw new DFNLPException(e.getMessage)
    }
  }

  private def validateGoogleCredentials(): Unit = {
    // TODO: validate that GOOGLE_APPLICATION_CREDENTIALS env var is set, if not throw Exception
  }
}
