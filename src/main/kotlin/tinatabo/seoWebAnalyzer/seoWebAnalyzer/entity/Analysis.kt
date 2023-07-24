package tinatabo.seoWebAnalyzer.seoWebAnalyzer.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "analysis")
data class Analysis(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_analysis")
    var id: Long? = null, //-- Se usa Long para rangos mayores que Int, como buenas prácticas.

    @Column(nullable = false) //-- Indicar que el campo URL no puede ser nulo.
    var url: String,

    var title: String,

    var description: String,

    @OneToMany(mappedBy = "analysis", cascade = [CascadeType.ALL], orphanRemoval = true) //-- Relación entre tablas.
    var keywords: MutableList<Keywords> = ArrayList(),

    @OneToMany(mappedBy = "analysis", cascade = [CascadeType.ALL], orphanRemoval = true)
    var titles: MutableList<TitleCount> = ArrayList(),

    var html5: Boolean,

    var images: Int,

    @Column(nullable = false)
    var createdAt: LocalDateTime
)
