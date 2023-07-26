package tinatabo.seoWebAnalyzer.seoWebAnalyzer.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
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
}