package com.md.testnews.core.network

interface NetworkStateProvider {
    fun isOnline(): Boolean
}