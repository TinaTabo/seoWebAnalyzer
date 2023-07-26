package tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity

import com.fasterxml.jackson.annotation.*
import jakarta.persistence.*

@Entity
@Table(name = "keywords")
data class Keywords(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_keyword")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY) //-- Relación de muchos a uno. LAZY se utiliza para que no se cargue la entidad Analysis de la BBDD a menos que se acceda explicitamente a ella.
    @JoinColumn(name = "id_analysis_key")
    var analysis: Analysis,

    @Column(name = "keyword", length = 45)
    var keyword: String
)
