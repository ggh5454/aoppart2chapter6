package com.example.aoppart2chapter6

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.CpuUsageInfo
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val remainMinutesTextView: TextView by lazy {
        findViewById(R.id.remainMinutesTextView)
    }

    private val remainSecondsTextView: TextView by lazy {
        findViewById(R.id.remainSecondsTextView)
    }
    private val seekBar1: SeekBar by lazy {
        findViewById(R.id.seekBar1)
    }
    private var currentCountDownTimer1: CountDownTimer? = null


    private val remainMinutesTextView2: TextView by lazy {
        findViewById(R.id.remainMinutesTextView2)
    }
    private val remainSecondsTextView2: TextView by lazy {
        findViewById(R.id.remainSecondsTextView2)
    }
    private val seekBar2: SeekBar by lazy {
        findViewById(R.id.seekBar2)
    }
    private var currentCountDownTimer2: CountDownTimer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews1()
        bindViews2()
    }

    private fun bindViews1() {
        seekBar1.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        updateRemainTime1(progress * 60 * 1000L)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    currentCountDownTimer1?.cancel()
                    currentCountDownTimer1 = null

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    //누르고 나서 바로 초가 움직임
                    seekBar ?: return

                    currentCountDownTimer1 = createCountDownTimer1(seekBar.progress * 60 * 1000L)

                    currentCountDownTimer1?.start()
                }
            }
        )


    }

    private fun bindViews2(){
        seekBar2.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        updateRemainTime2(progress * 60 * 1000L)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    currentCountDownTimer2?.cancel()
                    currentCountDownTimer2 = null

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    //누르고 나서 바로 초가 움직임
                    seekBar ?: return

                    currentCountDownTimer2 = createCountDownTimer2(seekBar.progress * 60 * 1000L)

                    currentCountDownTimer2?.start()
                }
            }
        )
    }

    private fun createCountDownTimer1(initialMills: Long) =
        object : CountDownTimer(initialMills, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                updateRemainTime1(millisUntilFinished)
                updateSeekBar1(millisUntilFinished)

            }

            override fun onFinish() {
                updateRemainTime1(0)
                updateSeekBar1(0)
            }
        }

    private fun createCountDownTimer2(initialMills: Long) =
        object : CountDownTimer(initialMills, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                updateRemainTime2(millisUntilFinished)
                updateSeekBar2(millisUntilFinished)

            }

            override fun onFinish() {
                updateRemainTime2(0)
                updateSeekBar2(0)
            }
        }

    @SuppressLint("SetTextI18n")
    private fun updateRemainTime1(remainMillis: Long) {
        val remainSeconds = remainMillis / 1000

        remainMinutesTextView.text = "%02d".format(remainSeconds / 60)
        remainSecondsTextView.text = "%02d".format(remainSeconds % 60)


    }

    @SuppressLint("SetTextI18n")
    private fun updateRemainTime2(remainMillis: Long) {
        val remainSeconds = remainMillis / 1000

        remainMinutesTextView2.text = "%02d".format(remainSeconds / 60)
        remainSecondsTextView2.text = "%02d".format(remainSeconds % 60)
    }

    private fun updateSeekBar1(remainMillis: Long) {
        seekBar1.progress = (remainMillis / 1000 / 60).toInt()
    }

    private fun updateSeekBar2(remainMillis: Long) {
        seekBar2.progress = (remainMillis / 1000 / 60).toInt()
    }
}

