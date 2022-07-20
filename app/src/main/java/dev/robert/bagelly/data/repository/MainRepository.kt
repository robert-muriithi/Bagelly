package dev.robert.bagelly.data.repository

import dev.robert.bagelly.model.Sell
import dev.robert.bagelly.utils.Resource

interface MainRepository {
    suspend fun sell(sell: Sell, result: (Resource<List<Sell>>) -> Unit)
}