package mx.edu.potros.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.*
import java.util.logging.Logger
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private val logger = Logger.getLogger(MainActivity::class.java.name)
    var numeros = ArrayList<Double>()
    var operaciones=ArrayList<String>()
    var posicionResultado:Int=-1
    lateinit var txtInput:TextView
    lateinit var txtOperaciones:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn0:Button=findViewById(R.id.btn0)
        val btn1: Button =findViewById(R.id.btn1)
        val btn2:Button=findViewById(R.id.btn2)
        val btn3:Button=findViewById(R.id.btn3)
        val btn4:Button=findViewById(R.id.btn4)
        val btn5:Button=findViewById(R.id.btn5)
        val btn6:Button=findViewById(R.id.btn6)
        val btn7:Button=findViewById(R.id.btn7)
        val btn8:Button=findViewById(R.id.btn8)
        val btn9:Button=findViewById(R.id.btn9)
        val btnMas:Button=findViewById(R.id.btnMas)
        val btnMenos:Button=findViewById(R.id.btnMenos)
        val btnDividir:Button=findViewById(R.id.btnDividir)
        val btnMultiplicar:Button=findViewById(R.id.btnMultiplicar)
        val btnResultado:Button=findViewById(R.id.btnResultado)
        val btnBorrar:Button=findViewById(R.id.btnBorrar)
        txtInput=findViewById(R.id.txtInput)
        txtOperaciones=findViewById(R.id.txtOperaciones)

        btn0.setOnClickListener {numeroPresionado("0")}
        btn1.setOnClickListener {numeroPresionado("1")}
        btn2.setOnClickListener {numeroPresionado("2")}
        btn3.setOnClickListener {numeroPresionado("3")}
        btn4.setOnClickListener {numeroPresionado("4")}
        btn5.setOnClickListener {numeroPresionado("5")}
        btn6.setOnClickListener {numeroPresionado("6")}
        btn7.setOnClickListener {numeroPresionado("7")}
        btn8.setOnClickListener {numeroPresionado("8")}
        btn9.setOnClickListener {numeroPresionado("9")}
        btnMas.setOnClickListener {operacionPresionada("+")}
        btnMenos.setOnClickListener{operacionPresionada("-")}
        btnDividir.setOnClickListener { operacionPresionada("/") }
        btnMultiplicar.setOnClickListener { operacionPresionada("X") }
        btnResultado.setOnClickListener { resolverOperaciones() }
        btnBorrar.setOnClickListener { clean() }
    }
    fun numeroPresionado(numero:String){
        if(!(txtOperaciones.text.endsWith("+")||txtOperaciones.text.endsWith("-")||
                    txtOperaciones.text.endsWith("/")||txtOperaciones.text.endsWith("X"))){
            txtOperaciones.setText("")
            numeros.clear()
        }
        txtInput.setText(txtInput.text.toString()+numero)

    }
    fun operacionPresionada(operacion:String){
        if(txtInput.text.isNotBlank()||!(txtOperaciones.text.endsWith("+")||txtOperaciones.text.endsWith("-")||
                    txtOperaciones.text.endsWith("/")||txtOperaciones.text.endsWith("X"))){
            txtOperaciones.setText(txtOperaciones.text.toString()+txtInput.text.toString()+operacion)
            if(txtInput.text.isNotBlank()) numeros.add(txtInput.text.toString().toDouble())
            txtInput.setText("")
            logger.info("numeros: $numeros")

        }
        else if(!operacion.equals(operaciones.last())){
            operaciones.removeLast()
            txtOperaciones.setText(txtOperaciones.text.dropLast(1).toString()+operacion)
        }
        operaciones.add(operacion)

    }

    fun resolverOperaciones(){//Resuelve de acuerdo al orden de operaciones
        var resultado:Double=0.0

        if(txtInput.text.isNotBlank()){  numeros.add(txtInput.text.toString().toDouble())}
        else{
            numeros.add(0.0)
        }
        var c=0
        while(operaciones.isNotEmpty()){
            var operacion=operaciones.get(c)
            logger.info("num operacion: "+c)
            if(operaciones.contains("X")||operaciones.contains("/")){
                if(operacion.equals("X")||operacion.equals("/")){
                    var(num1,num2)=hallarNumeros(operacion)
                    if(operacion.equals("X")){
                        resultado = num1 * num2
                        numeros.add(posicionResultado,resultado)
                    }
                    else if(operacion.equals("/")) {
                        resultado = num1 / num2
                        numeros.add(posicionResultado,resultado)
                    }
                }
            }
            else{

                if(operaciones[0].equals("+")){
                    var(num1,num2)=hallarNumeros(operaciones[0])
                    resultado = num1 + num2
                }
                else{
                    var(num1,num2)=hallarNumeros(operaciones[0])
                    resultado = num1 - num2
                }
                numeros.add(posicionResultado,resultado)

            }
            if(c<operaciones.lastIndex) c++
            else {c=0}
        }
        txtOperaciones.setText(resultado.toString())
        txtInput.setText("")
    }

    fun hallarNumeros(operacion: String): Pair<Double,Double>{
        var indxOperacion =operaciones.indexOf(operacion)
        var num1= numeros.get(indxOperacion)
        var num2 = numeros.get(indxOperacion+1)
        numeros.remove(num1)
        numeros.remove(num2)
        operaciones.removeAt(indxOperacion)
        posicionResultado=indxOperacion
        return Pair(num1,num2)
    }
    fun clean(){
        txtInput.setText("")
        txtOperaciones.setText("")
        operaciones.clear()
        numeros.clear()
    }
}