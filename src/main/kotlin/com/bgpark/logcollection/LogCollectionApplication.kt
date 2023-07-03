package com.bgpark.logcollection

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LogCollectionApplication

fun main(args: Array<String>) {
	runApplication<LogCollectionApplication>(*args)
}
