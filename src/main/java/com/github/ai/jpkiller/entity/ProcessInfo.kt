package com.github.ai.jpkiller.entity

data class ProcessInfo(
    val pid: Int,
    val command: String,
    val usedMemory: ByteCount,
    val type: ProcessType
)