package tinatabo.seoWebAnalyzer.seoWebAnalyzer.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto.GetResponseDTO
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto.PostResponseDTO
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.repository.AnalysisRepository
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.mapper.GetResponseMapper
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.mapper.PostResponseMapper
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.webAnalyzer.WebAnalyzer

@Service
class AnalysisServiceImpl(
    //-- @Autowired: sirve para inyectar automaticamente los objetos de las dependencias.
    @Autowired private val analysisRepository: AnalysisRepository,
    @Autowired private val postResponseMapper: PostResponseMapper,
    @Autowired private val getResponseMapper: GetResponseMapper,
    @Autowired private val webAnalyzer: WebAnalyzer
) : AnalysisService {
    override fun makeAnalysis(url: String): PostResponseDTO {
        //-- Hacer Análisis de la url y contruir el objeto Analysis.
        val analysis = webAnalyzer.analyze(url)
        //-- Guardar el análisis en la BBDD.
        val savedAnalysis = analysisRepository.save(analysis)
        //-- Convertir la entidad guardada a PostResponseDTO y devolverla al usuario.
        return postResponseMapper.toDTO(savedAnalysis)
    }

    override fun getAnalysis(url: String): PostResponseDTO? {
        //-- Buscar en la base de datos el analisis por la url.
        val analysis = analysisRepository.findByUrl(url)
        //-- Si el análisis no existe hacer nuevo analisis, llamando al método makeAnalysis
        //-- si el análisis existe, convertir el análisis encontrado a PostResponseDTO y devolverlo.
        return if (analysis != null){
            postResponseMapper.toDTO(analysis)
        }else{
            makeAnalysis(url)
        }
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