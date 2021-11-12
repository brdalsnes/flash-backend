package com.brdalsnes.integration

import com.brdalsnes.*
import com.brdalsnes.models.NewUser
import com.brdalsnes.models.UpdateUser
import com.brdalsnes.models.User
import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserTest {
    @BeforeTest
    fun setup() {
        withTestServer {
            userTableSetup()
        }
    }

    @AfterTest
    fun tearDown() {
        withTestServer {
            userTableClear()
        }
    }

    @Test
    fun shouldGetUsers() {
        withTestServer {
            handleRequest(HttpMethod.Get, "/user").apply {
                val users = getModelFromResponse<List<User>>(response)
                assertEquals(2, users.size)
                assertEquals(HttpStatusCode.OK, response.status())
            }

            handleRequest(HttpMethod.Get, "/user/d5ca4cd1-5a54-433a-8580-bbb110031c82").apply {
                val user = getModelFromResponse<User>(response)
                assertEquals("Green", user.name)
                assertEquals(0, user.score)
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun shouldPostUser() {
        val user = NewUser(name = "Blue")
        withTestServer {
            with(handleRequest(HttpMethod.Post, "/user") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(user))
            }) {
                assertEquals(HttpStatusCode.Created, response.status())
            }
        }
    }

    @Test
    fun shouldUpdateUserNameAndScore() {
        val update = UpdateUser(name = "Blue", score = 3)
        withTestServer {
            with(handleRequest(HttpMethod.Put, "/user/d5ca4cd1-5a54-433a-8580-bbb110031c82") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(update))
            }) {
                assertEquals(HttpStatusCode.OK, response.status())
            }

            handleRequest(HttpMethod.Get, "/user/d5ca4cd1-5a54-433a-8580-bbb110031c82").apply {
                val user = getModelFromResponse<User>(response)
                assertEquals("Blue", user.name)
                assertEquals(3, user.score)
            }
        }
    }

    @Test
    fun shouldDeleteUser() {
        withTestServer {
            handleRequest(HttpMethod.Delete, "/user/d5ca4cd1-5a54-433a-8580-bbb110031c82").apply {
                assertEquals(HttpStatusCode.NoContent, response.status())
            }

            handleRequest(HttpMethod.Get, "/user").apply {
                val users = getModelFromResponse<List<User>>(response)
                assertEquals(1, users.size)
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}