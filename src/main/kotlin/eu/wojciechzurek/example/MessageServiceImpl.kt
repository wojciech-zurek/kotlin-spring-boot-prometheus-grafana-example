package eu.wojciechzurek.example

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger


@Service
class MessageServiceImpl : MessageService {

    private lateinit var messageCounter: Counter

    private lateinit var operationCounter: Counter

    private lateinit var lastMessageLength: AtomicInteger

    private lateinit var messages: MutableList<String>

    private lateinit var timer: Timer

    private val responses = listOf(
            "Hello World",
            "Ala ma kota",
            "Hello Spring World",
            "Secret message for your eyes only"
    )

    @Autowired
    fun setCounter(meterRegistry: MeterRegistry) {
        //counters -> increment value
        messageCounter = meterRegistry.counter("service.message.counter")
        operationCounter = meterRegistry.counter("service.message.long.operation.counter")

        //gauges -> shows the current value of a meter.
        lastMessageLength = meterRegistry.gauge("service.message.last.message.length", AtomicInteger())!!
        //shows collection size (queue message, cache size etc...). In real app the collection implementation used should be thread safe.
        messages = meterRegistry.gaugeCollectionSize("service.message.message.size", emptyList(), mutableListOf())!!
        messages.addAll(responses)

        //timer -> measures the time taken for short tasks and the count of these tasks.
        timer = meterRegistry.timer("service.message.long.operation.run.timer")

        //other meters...
    }

    override fun getMessage(): String {
        messageCounter.increment()

        val message = getRandomMessage()

        lastMessageLength.set(message.length)

        return message
    }

    @Scheduled(fixedDelay = 3000)
    @Throws(InterruptedException::class)
    fun sampleLongOperation() {
        operationCounter.increment()

        val startTime = System.nanoTime()

        val randomLongOperation = Random().nextInt(7000).toLong()

        Thread.sleep(randomLongOperation)

        timer.record(System.nanoTime() - startTime, TimeUnit.NANOSECONDS)
    }

    private fun getRandomMessage(): String {
        return responses[Random().nextInt(responses.size)]
    }
}