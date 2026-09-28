package com.example.forjadelheroe

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        //Etiqueta para saber cuáles son nuestros propios mensajes del logcat
        val TAG = "Eventos lOGCAT"

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Declarar todos los componentes de la interfaz que se van a programar
        val editNombre = findViewById<EditText>(R.id.editTextText)
        val spinnerAscendencias =findViewById<Spinner>(R.id.spinner)
        val grupoRadios = findViewById<RadioGroup>(R.id.radioGru)
        val cajaStealth = findViewById<CheckBox>(R.id.checkBox)
        val cajaCombate = findViewById<CheckBox>(R.id.checkBox2)
        val btnImagen = findViewById<ImageButton>(R.id.imageButton2)

        //Spinner, para que pueda poner los elementos de la lista.
        //Supuestamente se puede desde el activity main, pero no me deja
        // El Spinner necesita un Adapter para saber qué elementos mostrar en su lista
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.razas_tierra_media,
            android.R.layout.simple_spinner_item
        )

        // Indico cómo se mostrarán los elementos
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        // Asociamos el Adapter al Spinner
        spinnerAscendencias.adapter = adapter



        //Para que gane el foco el editText
        editNombre.requestFocus()

        // setOnFocusChangeListener permite saber cuándo el EditText gana o pierde el foco
        editNombre.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                // Validar si el campo se quedó vacío al abandonarlo
                if (editNombre.text.isEmpty()){
                    editNombre.error = "¡Tu héroe necesita un nombre!"
                    Log.d(TAG, "Ahora no tiene nombre")
                } else Log.d(TAG, "Nombre actual: ${editNombre.text}")
            }
        }
        //El spinner necesita un adaptador para nutrirse del Array de strings
        spinnerAscendencias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //Si se selecciona algo
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //obtengo la elección actual
                val razaSeleccionada = parent?.getItemAtPosition(position).toString()
                Log.d(TAG, "Raza seleccionada: $razaSeleccionada")
            }
            //En caso de que no se seleccione nada
            override fun onNothingSelected(parent: AdapterView<*>?) {Log.d(TAG, "No se ha seleccionado nada")}
        }


        //Solo se puede seleccionar una opción
        grupoRadios.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButton2 -> Log.d(TAG, "¡Has seleccionado la Comunidad!")
                R.id.radioButton3 -> Log.d(TAG, "Has seleccionado a Mordor")
            }
        }

        //Este es para el check box de espada, mira si está seleccionado o no
        cajaCombate.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) Log.d(TAG, "Has elegido combate.")
            else Log.d(TAG, "Has deseleccionado combate.")
        }
        //Este es para el check box de sigilo, mira si está seleccionada o no
        cajaStealth.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) Log.d(TAG, "Has elegido sigilo.")
            else Log.d(TAG, "Has deseleccionado sigilo.")
        }

        //Al pinchar el botón de imagen pasan dos cosas
        //1 Se añade al logcat que has pinchado
        //2 Se añade un toast
        btnImagen.setOnClickListener {
            //1 logcat
            Log.d(TAG, "Se ha pulsado el botón de imagen y se ha mostrado un resumen por toast")

            //2 resumen toast
            // Obtengo el nombre
            val nombre = editNombre.text.toString()
            // Obtengo la raza
            val raza = spinnerAscendencias.selectedItem.toString()
            // Compruebo qué radio button está selecciona
            val bando = when (grupoRadios.checkedRadioButtonId) {
                R.id.radioButton2 -> "Comunidad"
                R.id.radioButton3 -> "Mordor"
                else -> "Sin seleccionar" //o si no hay ninguno seleccionado
            }
            // Compruebo el estado de los checkbox
            val sigilo = if (cajaStealth.isChecked) "Sí" else "No"
            val combate = if (cajaCombate.isChecked) "Sí" else "No"

            // Creo el resumen del personaje
            val resumen = "Personaje: $nombre Raza: $raza Bando: $bando Sigilo: $sigilo Combate: $combate"

            // Muestro el resumen con un Toast
            Toast.makeText(this,resumen,Toast.LENGTH_LONG).show()

        }




    }
}