package br.com.mauriciobenigno.groovy_tdd.details

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException
import javax.inject.Inject

class PlaylistDetailsService @Inject constructor(
    private val api: PlaylistDetailsAPI
){
    fun fetchPlaylistDetais(id: String) : Flow<Result<PlaylistDetails>> {
        return flow {
            emit(Result.success(api.fetchPlaylistDetail(id)))
        }.catch {
            emit(Result.failure(RuntimeException("Algo errado nao esta certo")))
        }
    }

}
