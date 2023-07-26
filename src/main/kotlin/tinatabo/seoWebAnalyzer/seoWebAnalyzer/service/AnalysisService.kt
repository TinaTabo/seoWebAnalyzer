package tinatabo.seoWebAnalyzer.seoWebAnalyzer.service

import tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto.GetResponseDTO
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.dto.PostResponseDTO

interface AnalysisService {
    //-- Funciones que tiene el servicio:
    //-- Hacer nuevo análisis.
    fun makeAnalysis(url: String): PostResponseDTO
    //-- Obtener los últimos 15 análisis realizados
    fun getAllAnalysis(limit: Int): List<GetResponseDTO>
    //-- Eliminar análisis
    fun deleteAnalysis(id: Int): Boolean
}