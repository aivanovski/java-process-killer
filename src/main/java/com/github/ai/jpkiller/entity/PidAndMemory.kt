package com.github.ai.jpkiller.entity

data class PidAndMemory(
    val pid: Int,
    val allocatedMemory: ByteCount
)