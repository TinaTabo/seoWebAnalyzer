package tinatabo.seoWebAnalyzer.seoWebAnalyzer.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto.GetResponseDTO
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto.PostResponseDTO
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity.Analysis
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.model.AnalysisRequest
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.service.AnalysisService

@RestController
@RequestMapping("/analisis")
class AnalysisController(
    @Autowired private val analysisService: AnalysisService
){

    //-- POST: Realiza un nuevo análisis SEO de la url pasada por parámetro.
    @PostMapping
    fun makeAnalysis(@RequestBody request: AnalysisRequest): ResponseEntity<PostResponseDTO>{
        val analysis = analysisService.makeAnalysis(request.url)
        return ResponseEntity(analysis, HttpStatus.CREATED)
    }

    //-- POST: Realiza un nuevo análisis SEO de la url pasada por parámetro.
    @GetMapping
    fun getAnalysis(@RequestParam(required = false, defaultValue = "15") limit: Int): ResponseEntity<Any>{
        return try {
            val result: List<GetResponseDTO> = analysisService.getAllAnalysis(limit)
            ResponseEntity(result, HttpStatus.OK)
        }catch (exception: Exception){
            ResponseEntity("Error al obtener análisis", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}