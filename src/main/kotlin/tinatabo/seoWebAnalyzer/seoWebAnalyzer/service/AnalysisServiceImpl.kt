package tinatabo.seoWebAnalyzer.seoWebAnalyzer.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto.GetResponseDTO
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto.PostResponseDTO
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity.Analysis
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.repository.AnalysisRepository
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.repository.KeywordsRepository
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.repository.TitleCountRepository
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.exceptions.InvalidURLException
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.exceptions.URLNotFoundException
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.isValidUrl
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.mapper.GetResponseMapper
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.mapper.PostResponseMapper
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.webAnalyzer.WebAnalyzer
import java.net.URI
import java.net.URISyntaxException
import java.net.URL
import java.util.regex.Pattern

@Service
class AnalysisServiceImpl(
    //-- @Autowired: sirve para inyectar automaticamente los objetos de las dependencias.
    @Autowired private val analysisRepository: AnalysisRepository,
    @Autowired private val keywordsRepository: KeywordsRepository,
    @Autowired private val titleCountRepository: TitleCountRepository,
    @Autowired private val postResponseMapper: PostResponseMapper,
    @Autowired private val getResponseMapper: GetResponseMapper,
    @Autowired private val webAnalyzer: WebAnalyzer
) : AnalysisService {

    override fun makeAnalysis(url: String): PostResponseDTO {
        //-- Validar la URL antes de realizar el análisis.
        if (!isValidUrl(url)){
            throw InvalidURLException("The provided URL is invalid: $url")
        }
        //-- Hacer Análisis de la url y contruir el objeto Analysis.
        val analysis: Analysis
        try {
            analysis = webAnalyzer.analyze(url)
        }catch (exception: Exception){
            throw URLNotFoundException("URL not found: $url")
        }

        //-- Guardar el análisis en la BBDD.
        val savedAnalysis = analysisRepository.save(analysis)
        //-- Convertir la entidad guardada a PostResponseDTO y devolverla al usuario.
        return postResponseMapper.toDTO(savedAnalysis)
    }


    override fun getAnalysis(url: String): PostResponseDTO? {
        //-- Buscar en la base de datos el analisis por la url.
        println(url)
        val analysisId = analysisRepository.findByUrl(url)
        if (analysisId == null){
            //-- No existe analisis de la URL --> se hace nuevo analisis.
            makeAnalysis(url)
        }
        val keywords = keywordsRepository.findByAnalysisId(analysisId)
        //val titles = titleCountRepository.findByAnalysisId(analysisId)

        println(keywords)
        //println(titles)

        return TODO()
    }

    override fun getAllAnalysis(limit: Int): List<GetResponseDTO> {
        //-- Buscar en la BBDD todos los análisis hasta el limite especificado
        val allAnalysis = analysisRepository.findAll().take(limit)
        //-- Convertir cada análisis encontrado a GetResponseDTO y devolver la lista con todos.
        return allAnalysis.map { getResponseMapper.toDTO(it) }
    }

    override fun deleteAnalysis(id: Long): Boolean {
        //-- Buscar analisis por id
        val analisisExists = analysisRepository.existsById(id)
        //-- if existe -> lo elimino -> return true
        //-- if not existe -> return false
        if (analisisExists){
            analysisRepository.deleteById(id)
        }
        return analisisExists
    }
}