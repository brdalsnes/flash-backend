package com.brdalsnes.integration

import com.brdalsnes.*
import com.brdalsnes.models.Deck
import com.brdalsnes.models.NewDeck
import com.brdalsnes.models.UpdateDeck
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DeckTest {
    @BeforeTest
    fun setup() {
        withTestServer {
            userTableSetup()
            deckTableSetup()
        }
    }

    @AfterTest
    fun tearDown() {
        withTestServer {
            deckTableClear()
            userTableClear()
        }
    }

    @Test
    fun shouldGetDecks() {
        withTestServer {
            handleRequest(HttpMethod.Get, "/deck").apply {
                val decks = getModelFromResponse<List<Deck>>(response)
                assertEquals(2, decks.size)
                assertEquals(HttpStatusCode.OK, response.status())
            }

            handleRequest(HttpMethod.Get, "/deck/a18e337d-4593-4239-9554-281794d58f43").apply {
                val deck = getModelFromResponse<Deck>(response)
                assertEquals("Spanish", deck.name)
                assertEquals(0, deck.numCards)
                assertEquals(0, deck.numSubscribers)
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun shouldPostDeck() {
        val deck = NewDeck(creator = "d5ca4cd1-5a54-433a-8580-bbb110031c82", name = "Russian")
        withTestServer {
            with(handleRequest(HttpMethod.Post, "/deck") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(deck))
            }) {
                assertEquals(HttpStatusCode.Created, response.status())
            }
        }
    }

    @Test
    fun shouldUpdateDeckName() {
        val update = UpdateDeck(name = "Spanish 2")
        withTestServer {
            with(handleRequest(HttpMethod.Patch, "/deck/a18e337d-4593-4239-9554-281794d58f43") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(update))
            }) {
                assertEquals(HttpStatusCode.OK, response.status())
            }

            handleRequest(HttpMethod.Get, "/deck/a18e337d-4593-4239-9554-281794d58f43").apply {
                val deck = getModelFromResponse<Deck>(response)
                assertEquals("Spanish 2", deck.name)
            }
        }
    }

    @Test
    fun shouldDeleteDeck() {
        withTestServer {
            handleRequest(HttpMethod.Delete, "/deck/a18e337d-4593-4239-9554-281794d58f43").apply {
                assertEquals(HttpStatusCode.NoContent, response.status())
            }

            handleRequest(HttpMethod.Get, "/deck").apply {
                val decks = getModelFromResponse<List<Deck>>(response)
                assertEquals(1, decks.size)
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}