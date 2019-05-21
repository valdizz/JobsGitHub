package com.valdizz.jobsgithub.model

/**
 * Singleton object that provides the repository instance.
 *
 * @author Vlad Kornev
 */
object SearchRepositoryProvider {
    fun provideSearchRepository(): SearchRepository {
        return SearchRepository(GithubApiService.create())
    }
}