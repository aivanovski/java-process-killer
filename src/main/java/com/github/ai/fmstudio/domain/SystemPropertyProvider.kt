package com.github.ai.fmstudio.domain

import com.github.ai.fmstudio.utils.StringUtils.EMPTY

class SystemPropertyProvider {

    fun getSystemProperty(name: String): String {
        return System.getProperty(name) ?: EMPTY
    }
}