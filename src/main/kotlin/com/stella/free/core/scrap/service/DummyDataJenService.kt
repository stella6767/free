package com.stella.free.core.scrap.service

import jakarta.validation.constraints.Max
import kotlinx.coroutines.*
import net.datafaker.Faker
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.collections.ArrayList


@Service
class DummyDataJenService(

) {

    enum class AsyncType {
        COROUTINE, TASK, SINGLE
    }




    fun createDummyPersonsByExecutorService(
        size: Int,
        faker: Faker,
        dummyPeople: ArrayList<DummyPerson>
    ): List<DummyPerson> {

        val executorService =
            Executors.newFixedThreadPool(10)

        val tasks =
            arrayListOf<Callable<DummyPerson>>()

        for (i in 1..size) {
            val task = Callable {
                createDummyPerson(faker)
            }
            tasks.add(task)
        }

        val futures =
            executorService.invokeAll(tasks)

        val result =
            futures.map { it.get() }

        executorService.shutdown()

        return result
    }


    fun createDummyPersonsBySingleThread(
        size: Int,
        faker: Faker,
        dummyPeople: ArrayList<DummyPerson>
    ): List<DummyPerson> {

        for (i in 1..size) {
            dummyPeople.add(createDummyPerson(faker))
        }

        return dummyPeople
    }

    suspend fun createDummyPersonsByCoroutine(
        size: Int,
        faker: Faker,
        dummyPeople: ArrayList<DummyPerson>
    ): List<DummyPerson> {


        return coroutineScope {
            val tasks = List(size) {
                async(Dispatchers.Default) { createDummyPersonByCoroutine(faker) }
            }
            tasks.awaitAll()
        }

    }


    fun createDummyPersons(size: Int, type: AsyncType): List<DummyPerson> {

        val faker = Faker(Locale("ko"))

        val dummyPeople =
            ArrayList<DummyPerson>()

        return when (type) {
            AsyncType.SINGLE -> createDummyPersonsBySingleThread(size, faker, dummyPeople)
            AsyncType.COROUTINE -> runBlocking { createDummyPersonsByCoroutine(size, faker, dummyPeople) }
            AsyncType.TASK -> createDummyPersonsByExecutorService(size, faker, dummyPeople)
        }
    }


    suspend fun createDummyPersonByCoroutine(faker: Faker): DummyPerson {
        return createDummyPerson(faker)
    }




    private fun createDummyPerson(faker: Faker): DummyPerson {
        //Thread.sleep(2000)
        val name = faker.name().fullName()
        val email = faker.internet().emailAddress()
        val domain = faker.internet().domainName()
        val ipaddr = faker.internet().ipV4Address()
        val companyName = faker.company().name()
        val catchPhrase = faker.company().catchPhrase()
        val streetAddress = faker.address().streetAddress()
        val address = faker.address().fullAddress()

        return DummyPerson(
            name, email, domain, ipaddr, companyName, catchPhrase, streetAddress, address
        )
    }


    data class DummyPerson(
        val name: String,
        val email: String,
        val domain: String,
        val ipaddr: String,
        val companyName: String,
        val catchPhrase: String,
        val streetAddress: String,
        val address: String,
    )


    data class DummyGenDto(
        val type: AsyncType,

        @field:Max(1000000)
        val size: Int
    )


}