package com.github.ai.jpkiller.domain

import com.github.ai.jpkiller.utils.StringUtils.EMPTY

class SystemPropertyProvider {

    fun getSystemProperty(name: String): String {
        return System.getProperty(name) ?: EMPTY
    }
}