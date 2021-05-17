package br.com.urbainski.appsorteio.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.urbainski.appsorteio.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { sortearNumero() }
    }

    fun sortearNumero() {

        var numeroSorteado = Random().nextInt(11)

        textViewResultado.text = "Número sorteado é: " + numeroSorteado
    }

}