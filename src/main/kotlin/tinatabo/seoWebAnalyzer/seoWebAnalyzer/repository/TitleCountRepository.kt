package tinatabo.seoWebAnalyzer.seoWebAnalyzer.repository

import org.springframework.data.repository.CrudRepository
import tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity.TitleCount

interface TitleCountRepository: CrudRepository<TitleCount, Long>