package com.brdalsnes.integration

import com.brdalsnes.fullClear
import com.brdalsnes.fullSetup
import com.brdalsnes.getModelFromResponse
import com.brdalsnes.models.Deck
import com.brdalsnes.models.NewSubscription
import com.brdalsnes.withTestServer
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SubscriptionTest {
    @BeforeTest
    fun setup() {
        withTestServer {
            fullSetup()
        }
    }

    @AfterTest
    fun tearDown() {
        withTestServer {
            fullClear()
        }
    }

    @Test
    fun shouldPostSubscription() {
        withTestServer {
            val subscription = NewSubscription(
                userId = "d5ca4cd1-5a54-433a-8580-bbb110031c83",
                deckId = "a18e337d-4593-4239-9554-281794d58f44")
            with(handleRequest(HttpMethod.Post, "/subscription") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(subscription))
            }) {
                assertEquals(HttpStatusCode.Created, response.status())
            }
        }
    }

    @Test
    fun shouldDeleteSubscription() {
        withTestServer {
            handleRequest(HttpMethod.Delete, "/subscription/3d2330af-d153-44e8-aa60-bc0051ffcc6e").apply {
                assertEquals(HttpStatusCode.NoContent, response.status())
            }
        }
    }

    @Test
    fun shouldIncreaseDeckSubscription() {
        withTestServer {
            handleRequest(HttpMethod.Get, "/deck/a18e337d-4593-4239-9554-281794d58f44").apply {
                val deck = getModelFromResponse<Deck>(response)
                assertEquals(0, deck.numSubscribers)
            }
            shouldPostSubscription()
            handleRequest(HttpMethod.Get, "/deck/a18e337d-4593-4239-9554-281794d58f44").apply {
                val deck = getModelFromResponse<Deck>(response)
                assertEquals(1, deck.numSubscribers)
            }
        }
    }
}