package com.github.djumanoff.scala.nlp

/**
  * Created by idris on 7/26/18.
  */

/**
  * Trait that describes objects that implement NLP session.
  * It should be able to process human message and return NLPResponse or ErrorInfo
  * Add or remove context and contexts
  */
trait NLPSession {
  /**
    * Processes human message and return NLPResponse
    * @param msg human text message
    * @return NLPResponse if everything was ok or ErrorInfo
    */
  def processHumanMessage(msg: HumanMessage): NLPResponse

  /**
    * Add contexts to NLP session
    * @param contextName context to be added to NLP session
    * @return true or ErrorInfo
    */
  def addContext(contextName: String, lifespanCount: Integer): Unit

  /**
    * Remove context from NLP session
    * @param contextName context to be removed from NLP session
    * @return true or ErrorInfo
    */
  def removeContext(contextName: String): Unit

  /**
    * Remove all contexts from NLP session, if contextNames are None than it will remove all contexts
    * @return true or ErrorInfo
    */
  def removeAllContexts(): Unit

  /**
    * List all contexts of current session
    * @return list of strings representing current contexts
    */
  def listContexts(): List[String]

  /**
    * Get string representation of session id
    * @return session id
    */
  def getSessionId: String
}
