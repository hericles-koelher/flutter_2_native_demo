import com.example.flutter_2_native_demo.repositories.PokemonImageRepository
import com.example.flutter_2_native_demo.repositories.PokemonImageRepositoryImpl
import com.example.flutter_2_native_demo.services.PokemonImageService
import com.example.flutter_2_native_demo.services.PokemonImageServiceImpl
import org.testng.annotations.Test

class PokemonImageServiceTest {
    @Test
    fun get2Image() {
        val repo: PokemonImageRepository = PokemonImageRepositoryImpl()

        val service: PokemonImageService = PokemonImageServiceImpl(repo)

        var count = 0

        var imageCount = 0

        val disposable = service.getPokemonImageBytes()
            .subscribe({ bytes ->
                run {
                    println("Bytes da imagem: ${bytes.size}")

                    imageCount += 1
                }
            }, { error ->
                run {
                    println("Um erro ocorreu: ${error.message}")
                }
            })

        Thread.sleep(120000)

        disposable.dispose()

        assert(imageCount != 0)
    }
}