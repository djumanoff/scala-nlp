package com.github.djumanoff.scala.nlp

/**
  * Represent result of the processing of user input
  * @param action action to perform
  * @param intent intent of the user
  * @param contexts contexts of the user input
  * @param fulfillmentText text that should be presented to user
  * @param payloads list of json payloads
  */
case class NLPResponse(action: String, intent: String, contexts: Option[List[String]] = None, fulfillmentText: Option[String] = None, payloads: Option[List[String]] = None)
