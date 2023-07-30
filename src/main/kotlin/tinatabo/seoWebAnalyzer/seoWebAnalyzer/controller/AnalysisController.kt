package tinatabo.seoWebAnalyzer.seoWebAnalyzer.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto.DeleteResponseDTO
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto.GetResponseDTO
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto.PostResponseDTO
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.model.AnalysisRequest
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.service.AnalysisService

@RestController
@RequestMapping("/analisis")
class AnalysisController(
    @Autowired private val analysisService: AnalysisService
){

    //-- POST: Realiza un nuevo análisis SEO de la url pasada por parámetro u obtiene el analisis guardad en la BBDD.
    @PostMapping
    fun makeAnalysis(@RequestBody request: AnalysisRequest): ResponseEntity<PostResponseDTO>{
        val analysis = analysisService.makeAnalysis(request.url)
        return ResponseEntity(analysis, HttpStatus.CREATED)
    }

    //-- GET: Obtiene los análisis realizados guardados en el BBDD. Por defecto obtiene los últimos 15
    //--      análisis guardados. Este limite se puede cambiar al hacer la petición.
    @GetMapping
    fun getAnalysis(@RequestParam(required = false, defaultValue = "15") limit: Int): ResponseEntity<Any>{
        //-- Por defecto muestra las 15 primeras entradas de la BBDD, si no se le pasa ningun limite por parámtro.
        return try {
            val result: List<GetResponseDTO> = analysisService.getAllAnalysis(limit)
            ResponseEntity(result, HttpStatus.OK)
        }catch (exception: Exception){
            ResponseEntity("Error getting analysis", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    //-- DELETE: Elimina un análisis de la BBDD identificandolo por su ID.
    @DeleteMapping("{id}")
    fun deleteAnalysis(@PathVariable id: Int): ResponseEntity<DeleteResponseDTO> {
        return try {
            val isDeleted = analysisService.deleteAnalysis(id)
            if (isDeleted) {
                ResponseEntity(
                    DeleteResponseDTO("Analysis successfully deleted", HttpStatus.OK, HttpStatus.OK.value()),
                    HttpStatus.OK
                )
            } else {
                ResponseEntity(
                    DeleteResponseDTO("Analysis not found", HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND
                )
            }
        } catch (exception: Exception) {
            ResponseEntity(
                DeleteResponseDTO("Error deleting analysis", HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value()),
                HttpStatus.INTERNAL_SERVER_ERROR
            )
        }
    }
}