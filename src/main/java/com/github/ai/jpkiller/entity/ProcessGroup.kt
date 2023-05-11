package com.github.ai.jpkiller.entity

data class ProcessGroup(
    val type: ProcessGroupType,
    val types: Set<ProcessType>,
    val processes: List<ProcessInfo>,
    val usedMemory: ByteCount
)