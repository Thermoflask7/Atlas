package zaragoza.mateo.atlas.red

import retrofit2.http.GET
import retrofit2.http.Query
import zaragoza.mateo.atlas.modelos.Pais

interface PaisesApi {

    @GET("countries")
    suspend fun obtenerPaises(
        @Query("fields") campos: String): List<Pais>
}