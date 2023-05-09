package com.github.ai.jpk.entity

data class ProcessInfo(
    val pid: Int,
    val command: String,
    val usedMemory: Long,
    val type: ProcessType
)