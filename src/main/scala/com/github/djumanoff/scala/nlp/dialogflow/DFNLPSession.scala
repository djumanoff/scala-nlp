package com.github.djumanoff.scala.nlp.dialogflow

import com.google.cloud.dialogflow.v2._
import com.google.protobuf.util.JsonFormat
import com.github.djumanoff.scala.nlp.{HumanMessage, NLPResponse, NLPSession}

import scala.collection.JavaConverters._

/**
  * Created by idris on 7/31/18.
  */
object DFNLPSession {
  def apply(sessionId: SessionName, sessionClient: SessionsClient, ctxClient: ContextsClient): DFNLPSession = new DFNLPSession(sessionId, sessionClient, ctxClient)
}

class DFNLPSession(val sessionId: SessionName, sessionClient: SessionsClient, ctxClient: ContextsClient) extends NLPSession {

  override def processHumanMessage(msg: HumanMessage): NLPResponse = {
    try {
      val txtInput = TextInput.newBuilder()
        .setText(msg.text)
        .setLanguageCode(msg.lang)
        .build()

      val queryInput = QueryInput.newBuilder()
        .setText(txtInput)
        .build()

      val response = sessionClient.detectIntent(sessionId, queryInput)
      val action = extractAction(response)
      val intent = extractIntent(response)
      val contextList = extractContexts(response)
      val fulfillmentText = extractFulfillmentText(response)
      val payloads = extractPayload(response)

      NLPResponse(
        action,
        intent,
        if (contextList.isEmpty) None else Some(contextList),
        if (fulfillmentText == null || fulfillmentText == "") None else Some(fulfillmentText),
        if (payloads.isEmpty) None else Some(payloads)
      )
    } catch {
      case e: Exception =>
        throw new DFNLPException(e.getMessage)
    }
  }

  override def addContext(ctxName: String, lifespanCount: Integer): Unit = {
    // Create the context name with the projectId, sessionId, and contextId
    try {
      val contextName = ContextName.newBuilder
        .setProject(sessionId.getProject)
        .setSession(sessionId.getSession)
        .setContext(ctxName)
        .build

      val context = Context.newBuilder
        .setName(contextName.toString)
        .setLifespanCount(lifespanCount)
        .build

      ctxClient.createContext(sessionId, context)
    } catch {
      case e: Exception =>
        throw new DFNLPException(e.getMessage)
    }
  }

  override def removeContext(contextName: String): Unit = {
    // Remove the context name with the projectId, sessionId, and contextId
    try {
      val context = ContextName.newBuilder
        .setProject(sessionId.getProject)
        .setSession(sessionId.getSession)
        .setContext(contextName)
        .build

      ctxClient.deleteContext(context)
    } catch {
      case e: Exception =>
        throw new Exception(e.getMessage)
    }
  }

  override def removeAllContexts(): Unit = {
    // Remove all contexts of the sessionId
    try {
      ctxClient.deleteAllContexts(sessionId)
    } catch {
      case e: Exception =>
        throw new DFNLPException(e.getMessage)
    }
  }

  override def listContexts(): List[String] = {
    (for { ctx <- ctxClient.listContexts(sessionId).iterateAll().asScala } yield ctx.getName.split("/").last).toList
  }

  override def getSessionId: String = {
    sessionId.getSession
  }

  private def extractAction(response: DetectIntentResponse): String = {
    response.getQueryResult.getAction
  }

  private def extractIntent(response: DetectIntentResponse): String = {
    response.getQueryResult.getIntent.getDisplayName
  }

  private def extractContexts(response: DetectIntentResponse): List[String] = {
    response.getQueryResult.getOutputContextsList.asScala.map(ctx => ctx.getName.split("/").last).toList
  }

  private def extractFulfillmentText(response: DetectIntentResponse): String = {
    response.getQueryResult.getFulfillmentText
  }

  private def extractPayload(response: DetectIntentResponse) = {
    response.getQueryResult.getFulfillmentMessagesList.asScala.filter(_.hasPayload).map(p =>
      JsonFormat.printer().print(p.getPayload)
    ).toList
  }
}
