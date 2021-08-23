package ruzaik.mh.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    lateinit var textInput : TextView
    var lastNumeric = false
    var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view : View){
        val tvInput = (view as Button).text
        textInput.append(tvInput)

        lastNumeric = true

    }

    fun onClear(view : View){
        textInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view : View){
        if(lastNumeric && !lastDot){
            textInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    private fun removeZeroAfterDot(result : String) : String {
        var value = result
        if(result.contains("0"))
            value = result.substring(0,result.length - 2)
        return  value
    }

    fun onEqual(view : View){
        if(lastNumeric){
            var tvValue = textInput.text.toString()
            var prefix = ""

            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }


                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")

                    var one = splitValue[0]
                    val two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    textInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    val two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    textInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                } else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    val two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    textInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                } else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")

                    var one = splitValue[0]
                    val two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }

                    textInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }

            }catch (e : ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    fun onOperator(view : View){
        if(lastNumeric && !isOperatorAdded(textInput.text.toString())){
            textInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value : String) : Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("*") || value.contains("+")
                    || value.contains("-")
        }
    }
}