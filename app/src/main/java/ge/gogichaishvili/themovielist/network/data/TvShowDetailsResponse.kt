package ge.gogichaishvili.themovielist.network.data

import ge.gogichaishvili.themovielist.domain.model.DetailsUiModel

data class TvShowDetailsResponse(
    val adult: Boolean,
    val backdrop_path: String,
    val created_by: List<Any>,
    val episode_run_time: List<Int>,
    val first_air_date: String,
    val genres: List<GenreX>,
    val homepage: String,
    val id: Int,
    val in_production: Boolean,
    val languages: List<String>,
    val last_air_date: String,
    val last_episode_to_air: LastEpisodeToAir,
    val name: String,
    val networks: List<Network>,
    val next_episode_to_air: Any,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<Any>,
    val production_countries: List<ProductionCountryX>,
    val seasons: List<Season>,
    val spoken_languages: List<SpokenLanguageX>,
    val status: String,
    val tagline: String,
    val type: String,
    val vote_average: Double,
    val vote_count: Int
) {
    fun toDomainModel(): DetailsUiModel {
        return DetailsUiModel(
            id = id,
            backdropPath = backdrop_path,
            originalTitle = original_name,
            overview = overview,
            popularity = popularity
        )
    }
}
