package tinatabo.seoWebAnalyzer.seoWebAnalyzer.repository

import org.springframework.data.repository.CrudRepository
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity.Keywords

interface KeywordsRepository: CrudRepository<Keywords, Long>{
    fun findByAnalysisId(analysisId: Long?): List<Keywords>
}