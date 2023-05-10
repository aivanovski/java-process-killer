package com.github.ai.jpkiller.utils

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class StringExtensionsKtTest {

    @Test
    fun `isNumber should return true only for digits`() {
        "".isNumber() shouldBe false
        "-".isNumber() shouldBe false
        "1.0".isNumber() shouldBe false
        "1234567890".isNumber() shouldBe true
    }
}