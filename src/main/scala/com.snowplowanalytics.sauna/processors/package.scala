/*
 * Copyright (c) 2015 Snowplow Analytics Ltd. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package com.snowplowanalytics.sauna

// akka
import akka.actor._

import scala.language.implicitConversions

/**
 * Some convenient implicit methods to reduce boilerplate code.
 */
package object processors {
  implicit def processor2processorActorWrapper(processor: Processor)(implicit system: ActorSystem): ProcessorActorWrapper =
    new ProcessorActorWrapper(system.actorOf(Props(processor)))

  implicit class processorActorWrapper2actor(processorActorWrapper: ProcessorActorWrapper) {
    def !(message: Any)(implicit sender: ActorRef = Actor.noSender) =
      processorActorWrapper.processingActor.!(message)(sender)
  }
}