package com.brdalsnes.integration

import com.brdalsnes.fullClear
import com.brdalsnes.fullSetup
import com.brdalsnes.getModelFromResponse
import com.brdalsnes.models.Card
import com.brdalsnes.models.Deck
import com.brdalsnes.models.NewCard
import com.brdalsnes.models.UpdateCard
import com.brdalsnes.withTestServer
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CardTest {
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
    fun shouldGetCard() {
        withTestServer {
            handleRequest(HttpMethod.Get, "/card/72ec667a-ef97-47b1-afcd-da00b27f9966").apply {
                val card = getModelFromResponse<Card>(response)
                assertEquals("Hola", card.back)
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun shouldPostCard() {
        val card = NewCard(deckId = "a18e337d-4593-4239-9554-281794d58f43", front = "Dog", back = "Perro")
        withTestServer {
            with(handleRequest(HttpMethod.Post, "/card") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(card))
            }) {
                assertEquals(HttpStatusCode.Created, response.status())
            }
        }
    }

    @Test
    fun shouldUpdateCard() {
        val update = UpdateCard(front = "Dog", back = "Perro")
        withTestServer {
            with(handleRequest(HttpMethod.Patch, "/card/72ec667a-ef97-47b1-afcd-da00b27f9966") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(update))
            }) {
                assertEquals(HttpStatusCode.OK, response.status())
            }

            handleRequest(HttpMethod.Get, "/card/72ec667a-ef97-47b1-afcd-da00b27f9966").apply {
                val card = getModelFromResponse<Card>(response)
                assertEquals("Dog", card.front)
                assertEquals("Perro", card.back)
            }
        }
    }

    @Test
    fun shouldDeleteCard() {
        withTestServer {
            handleRequest(HttpMethod.Delete, "/card/72ec667a-ef97-47b1-afcd-da00b27f9966").apply {
                assertEquals(HttpStatusCode.NoContent, response.status())
            }
        }
    }

    @Test
    fun shouldIncreaseCardNumber() {
        withTestServer {
            handleRequest(HttpMethod.Get, "/deck/a18e337d-4593-4239-9554-281794d58f43").apply {
                val deck = getModelFromResponse<Deck>(response)
                assertEquals(2, deck.numCards)
            }
            shouldPostCard()
            handleRequest(HttpMethod.Get, "/deck/a18e337d-4593-4239-9554-281794d58f43").apply {
                val deck = getModelFromResponse<Deck>(response)
                assertEquals(3, deck.numCards)
            }
        }
    }
}