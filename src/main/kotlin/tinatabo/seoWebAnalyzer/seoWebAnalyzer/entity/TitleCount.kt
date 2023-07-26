package tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity

import jakarta.persistence.*

@Entity
@Table(name = "title_counts")
data class TitleCount(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_title_counts")
    var id_title_counts: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY) //-- Relación de muchos a uno. LAZY se utiliza para que no se cargue la entidad Analysis de la BBDD a menos que se acceda explicitamenta a ella.
    @JoinColumn(name = "id_analysis_title")
    var analysis: Analysis,

    @Column(name = "title_type", length = 5)
    var titleType: String,

    var count: Int
)
