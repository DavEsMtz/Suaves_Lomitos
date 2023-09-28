package com.example.suaveslomitos.utils

import com.example.domain.models.Dog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking


fun suspendedTest(body: suspend CoroutineScope.() -> Unit) {
    runBlocking { body() }
}

/** TEST DATA **/

val rex = Dog(
    "Rex",
    description = "He is much more passive and is the first to suggest to rescue and not eat The Little Pilot",
    age = 5,
    image = "https://static.wikia.nocookie.net/isle-of-dogs/images/a/af/Rex.jpg/revision/latest/scale-to-width-down/666?cb=20180625001634"
)

val spots = Dog(
    "Spots",
    description = "Is the brother of Chief and are also a former guard dog for Mayor Kobayashi's ward",
    age = 2,
    image = "https://static.wikia.nocookie.net/isle-of-dogs/images/6/6b/Spots.jpg/revision/latest/scale-to-width-down/666?cb=20180624191101"
)

val chief = Dog(
    "Chief",
    description = "He is a leader of a pack of dogs",
    age = 8,
    image = "https://static.wikia.nocookie.net/isle-of-dogs/images/1/1d/Chief-0.jpg/revision/latest/scale-to-width-down/666?cb=20180624184431"
)
