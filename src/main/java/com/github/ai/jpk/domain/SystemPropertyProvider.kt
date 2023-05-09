package com.github.ai.jpk.domain

import com.github.ai.jpk.utils.StringUtils.EMPTY

class SystemPropertyProvider {

    fun getSystemProperty(name: String): String {
        return System.getProperty(name) ?: EMPTY
    }
}