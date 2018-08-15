package com.github.djumanoff.scala.nlp.dialogflow

import com.google.cloud.dialogflow.v2.{ContextsClient, SessionName, SessionsClient}
import com.github.djumanoff.scala.nlp.{NLPAgent, NLPSession}
import java.util.UUID.randomUUID

object DFNLPAgent {
  def apply(projectId: String, sessClient: SessionsClient, ctxClient: ContextsClient): DFNLPAgent = new DFNLPAgent(projectId, sessClient, ctxClient)
}

class DFNLPAgent(projectId: String, sessClient: SessionsClient, ctxClient: ContextsClient) extends NLPAgent {
  override def getSession(sessionId: Option[String] = None): NLPSession = {
    try {
      val sessionName = SessionName.of(projectId, sessionId.getOrElse(generateSessionId())) // TODO: generate unique session id
      DFNLPSession(sessionName, sessClient, ctxClient)
    } catch {
      case e: Exception =>
        throw new DFNLPException(e.getMessage)
    }
  }

  override def closeSession(sessionId: String): Unit = {
    throw new DFNLPException("Not Implemented in DialogFlow API v2")
  }

  override def close(): Unit = {
    try {
      sessClient.close()
      ctxClient.close()
    } catch {
      case e: Exception =>
        throw new DFNLPException(e.getMessage)
    }
  }

  /**
    * private helper method to generate IDs of the sessions TODO: in future replace with good one
    * @return session id of type string
    */
  private def generateSessionId(): String = {
    randomUUID().toString
  }
}
