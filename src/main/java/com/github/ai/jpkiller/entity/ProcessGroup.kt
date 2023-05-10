package com.github.ai.jpkiller.entity

data class ProcessGroup(
    val name: String,
    val types: Set<ProcessType>,
    val processes: List<ProcessInfo>,
    val usedMemory: ByteCount
)