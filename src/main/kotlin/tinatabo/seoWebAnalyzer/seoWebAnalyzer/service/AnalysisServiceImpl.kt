package tinatabo.seoWebAnalyzer.seoWebAnalyzer.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto.GetResponseDTO
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto.PostResponseDTO
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity.Analysis
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity.Keywords
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity.TitleCount
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.repository.AnalysisRepository
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.exceptions.InvalidURLException
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.exceptions.URLNotFoundException
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.isValidUrl
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.mapper.GetResponseMapper
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.mapper.PostResponseMapper
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.webAnalyzer.WebAnalyzer

@Service
class AnalysisServiceImpl(
    //-- @Autowired: sirve para inyectar automaticamente los objetos de las dependencias.
    @Autowired private val analysisRepository: AnalysisRepository,
    @Autowired private val postResponseMapper: PostResponseMapper,
    @Autowired private val getResponseMapper: GetResponseMapper,
    @Autowired private val webAnalyzer: WebAnalyzer,
    @Autowired private val jdbcTemplate: JdbcTemplate
) : AnalysisService {

    //-- Sistema de Logging
    companion object {
        private val logger = LoggerFactory.getLogger(AnalysisServiceImpl::class.java)
    }

    override fun makeAnalysis(url: String): PostResponseDTO {
        //-- Validar la URL antes de realizar el análisis.
        if (!isValidUrl(url)){
            throw InvalidURLException("The provided URL is invalid: $url")
        }

        //-- Comprobar en la BBDD si existe el analisis de la url.
        //-- Se utiliza la clase jdbcTemplate que simplica la interacción con la BBDD.
        val analysis: Analysis? = jdbcTemplate.query(
            "SELECT * FROM analysis WHERE url = ?",  //-- Consulta que se le hace a la BBDD.
            BeanPropertyRowMapper(Analysis::class.java), //-- Mappea una fila de resultados de la consulta a una instancia Analysis
            url //-- Este campo reemplaza ?              //-- Es importante que los nombre de las columnas en los resultados de la consulta
        ).firstOrNull()                                  //-- sean iguales a los nombres de los campos de la clase Analysis.

        if (analysis != null){
            logger.info("Existing analysis found in the database for url: $url")
            //-- Obtener los datos de las tablas keywords y title_counts
            val keywords: List<Keywords>? = analysis.id_analysis?.let {
                jdbcTemplate.query(
                    "SELECT * FROM keywords WHERE id_analysis_key = ?",
                    BeanPropertyRowMapper(Keywords::class.java),
                    it
                )
            }

            val titles: List<TitleCount>? = analysis.id_analysis?.let {
                jdbcTemplate.query(
                    "SELECT * FROM title_counts WHERE id_analysis_title = ?",
                    BeanPropertyRowMapper(TitleCount::class.java),
                    it
                )
            }

            //-- Crear nueva instacia de PostResponseDTO con los datos obtenidos.
            val postResponse = analysis.let{ it ->
                PostResponseDTO(
                    id = it.id_analysis,
                    url = it.url,
                    title = it.title,
                    description = it.description,
                    keywords = keywords?.map { keyword -> keyword.keyword } ?: listOf(),
                    titles = titles?.associateBy({ it.titleType }, { it.count }) ?: mapOf(),
                    html5 = it.html5,
                    images = it.images,
                    createdAt = it.createdAt
                )
            }
            return postResponse
        }else{
            logger.info("No existing analysis found. Performing new analysis for url: $url")
            //-- Hacer Análisis de la url y contruir el objeto Analysis.
            val newAnalysis: Analysis
            try {
                newAnalysis = webAnalyzer.analyze(url)
            }catch (exception: Exception){
                throw URLNotFoundException("URL not found: $url")
            }
            //-- Guardar el análisis en la BBDD.
            val savedAnalysis = analysisRepository.save(newAnalysis)
            //-- Convertir la entidad guardada a PostResponseDTO y devolverla al usuario.
            return postResponseMapper.toDTO(savedAnalysis)
        }
    }

    override fun getAllAnalysis(limit: Int): List<GetResponseDTO> {
        //-- Buscar en la BBDD todos los análisis hasta el limite especificado
        val allAnalysis = analysisRepository.findAll().take(limit)
        //-- Convertir cada análisis encontrado a GetResponseDTO y devolver la lista con todos.
        return allAnalysis.map { getResponseMapper.toDTO(it) }
    }

    override fun deleteAnalysis(id: Int): Boolean {
        //-- Buscar analisis por id_title_counts
        val analysis: Analysis? = jdbcTemplate.query(
            "SELECT * FROM analysis WHERE id_analysis = ?",
            BeanPropertyRowMapper(Analysis::class.java),
            id
        ).firstOrNull()

        //-- if existe -> lo elimino -> return true
        //-- if not existe -> return false
        if (analysis != null){
            jdbcTemplate.update(
                "DELETE FROM analysis WHERE id_analysis = ?",
                id
            )
            return true
        }
        return false
    }
}