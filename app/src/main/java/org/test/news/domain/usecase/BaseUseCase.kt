package org.test.news.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseUseCase<in Params, out Result>(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    protected abstract suspend fun execute(params: Params): Result

    suspend operator fun invoke(params: Params): Result {
        return withContext(coroutineDispatcher) {
            execute(params)
        }
    }
}