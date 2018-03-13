package com.ernestoestrada.colormixer

import android.animation.ArgbEvaluator
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.util.Log
import android.widget.SeekBar
import com.ernestoestrada.colormixer.R.id.colorView
import com.ernestoestrada.colormixer.R.id.colorView1
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 5
    var color1:IntArray = intArrayOf(0,0,0)
    var color2:IntArray = intArrayOf(0,0,0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        colorBtn1.setOnClickListener {
            Log.i("First", "First call")
            getColor()
        }

        mixer.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var progressColor:IntArray = intArrayOf(0,0,0)
                var firstColor:IntArray = intArrayOf(0,0,0)
                var secondColor:IntArray = intArrayOf(0,0,0)

                val maxColor = color1
                val minColor = color2
                if (progress == 100){
                    colorView.setBackgroundColor(Color.rgb(maxColor[0], maxColor[1], maxColor[2]))
                    colorView1.setBackgroundColor(Color.rgb(maxColor[0], maxColor[1], maxColor[2]))
                }
                else if (progress == 0){
                    colorView.setBackgroundColor(Color.rgb(minColor[0], minColor[1], minColor[2]))
                    colorView1.setBackgroundColor(Color.rgb(minColor[0], minColor[1], minColor[2]))
                }

                else if (progress < 50 ) {
                    Log.i("Progress", "${progress}")
                    secondColor[0] = minColor[0] * progress
                    secondColor[1] = minColor[1] * progress
                    secondColor[2] = minColor[2] * progress

                    firstColor[0] = maxColor[0] * (100 - progress)
                    firstColor[1] = maxColor[1] * (100 - progress)
                    firstColor[2] = maxColor[2] * (100 - progress)

                    progressColor[0] = firstColor[0] + secondColor[0]
                    progressColor[1] = firstColor[1] + secondColor[1]
                    progressColor[2] = firstColor[2] + secondColor[2]

                    colorView.setBackgroundColor(Color.rgb(progressColor[0], progressColor[1], progressColor[2]))
                    colorView1.setBackgroundColor(Color.rgb(progressColor[0], progressColor[1], progressColor[2]))
                }
                else if (progress > 50 ) {
                    Log.i("Progress", "${progress}")
                    secondColor[0] = minColor[0] * progress
                    secondColor[1] = minColor[1] * progress
                    secondColor[2] = minColor[2] * progress

                    firstColor[0] = maxColor[0] * (100 - progress)
                    firstColor[1] = maxColor[1] * (100 - progress)
                    firstColor[2] = maxColor[2] * (100 - progress)

                    progressColor[0] = firstColor[0] + secondColor[0]
                    progressColor[1] = firstColor[1] + secondColor[1]
                    progressColor[2] = firstColor[2] + secondColor[2]

                    colorView.setBackgroundColor(Color.rgb(progressColor[0], progressColor[1], progressColor[2]))
                    colorView1.setBackgroundColor(Color.rgb(progressColor[0], progressColor[1], progressColor[2]))
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


    }


    fun getColor() {
        var intent = Intent(Intent.ACTION_DEFAULT)
        intent.action = "com.ernestoestrada.colorpicker"
        intent.putExtra("color_1", color1)
        intent.putExtra("color_2", color2)
        startActivityForResult(intent, REQUEST_CODE)
        Log.i("second", "second call")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if ((requestCode == REQUEST_CODE) &&
                (resultCode == RESULT_OK)) {

            if (data.hasExtra("color_1")) {
                val firstColor = data.getStringExtra("color_1")
                val item = firstColor.split(" ")
                color1[0] = item[0].toInt()
                color1[1] = item[1].toInt()
                color1[2] = item[2].toInt()
                colorView.setBackgroundColor(Color.rgb(color1[0], color1[1], color1[2]))
                Log.i("First", "${color1[0]} ${color1[1]} ${color1[2]}")
            }

            if (data.hasExtra("color_2")){
                val secondColor = data.getStringExtra("color_2")
                val item = secondColor.split(" ")
                color2[0] = item[0].toInt()
                color2[1] = item[1].toInt()
                color2[2] = item[2].toInt()
                colorView1.setBackgroundColor(Color.rgb(color2[0], color2[1], color2[2]))
                Log.i("second", "${color2[0]} ${color2[1]} ${color2[2]}")
            }

        }
    }
}
