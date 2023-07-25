package tinatabo.seoWebAnalyzer.seoWebAnalyzer.repository

import org.springframework.data.repository.CrudRepository
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity.Analysis

//-- Este repositorio proporciona la funcionalidad básica de CRUD(Crear,Leer,Actualizar,Borrar),
//-- para inyectarlos en los servicios y utilizar sus métodos para interactuar con la base de datos.
interface AnalysisRepository: CrudRepository<Analysis, Long>{
    //-- Método para buscar una entrada en la base de datos por el campo 'url'.
    fun findByUrl(url: String): Analysis?
}