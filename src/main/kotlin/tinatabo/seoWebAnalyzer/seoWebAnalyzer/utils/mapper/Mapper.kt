package tinatabo.seoWebAnalyzer.seoWebAnalyzer.utils.mapper

interface Mapper<D, E> {
    fun toDTO(entity: E): D
    fun toEntity(dto: D): E
}