/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ciaomultisegmentapi.controllers

import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.FakeRequest
import uk.gov.hmrc.ciaomultisegmentapi.models.WelcomeMessage
import uk.gov.hmrc.ciaomultisegmentapi.models.JsonFormatters.formatWelcomeMessage
import uk.gov.hmrc.play.test.{UnitSpec, WithFakeApplication}

class WelcomeControllerSpec extends UnitSpec with WithFakeApplication {

  private implicit val materializer = fakeApplication.materializer

  private val controller = new WelcomeController

  "WelcomeController" should {

    "respond with the expected message when calling GET / " in {

      val request = FakeRequest(method = "GET", path = "/")

      val result = controller.welcome()(request)

      status(result) shouldBe Status.OK

      val expectedAnswer = WelcomeMessage("Ciao!")

      await(jsonBodyOf(result)) shouldBe Json.toJson(expectedAnswer)
    }

    "respond with the expected message when calling GET /:friend " in {

      val friend = "Valentino"

      val request = FakeRequest(method = "GET", path = s"/$friend")

      val result = controller.welcomeFriend(friend)(request)

      status(result) shouldBe Status.OK

      val expectedAnswer = WelcomeMessage(s"Ciao $friend!")

      await(jsonBodyOf(result)) shouldBe Json.toJson(expectedAnswer)
    }

    "respond with the expected message when calling GET /:friend/:city " in {

      val friend = "Alvise"
      val city = "Paris"

      val request = FakeRequest(method = "GET", path = s"/$friend/$city")

      val result = controller.welcomeFriendCity(friend, city)(request)

      status(result) shouldBe Status.OK

      val expectedAnswer = WelcomeMessage(s"Ciao $friend! Welcome to $city!")

      await(jsonBodyOf(result)) shouldBe Json.toJson(expectedAnswer)
    }

  }

}
