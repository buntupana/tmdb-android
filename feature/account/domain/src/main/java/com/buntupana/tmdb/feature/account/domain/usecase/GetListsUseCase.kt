package com.buntupana.tmdb.feature.account.domain.usecase

import com.buntupana.tmdb.feature.account.domain.model.ListItem
import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import javax.inject.Inject

class GetListsUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(): Result<List<ListItem>, NetworkError> {
        return accountRepository.getLists()
    }
}