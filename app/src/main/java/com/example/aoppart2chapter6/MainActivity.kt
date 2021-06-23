package com.example.aoppart2chapter6

import android.annotation.SuppressLint
import android.media.SoundPool
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
    private val soundPool1 = SoundPool.Builder().build()
    private var tickingSoundId1: Int? = null
    private var bellSoundId1: Int? = null


    //첫번째 시간이 끝나면 바로 두번 째 seekbar를 움직일 수 있도록 해본다
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
    private val soundPool2 = SoundPool.Builder().build()
    private var tickingSoundId2: Int? = null
    private var bellSoundId2: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews1()
        bindViews2()

        initSounds1()
        initSounds2()
    }

    override fun onResume() {
        super.onResume()
        soundPool2.autoResume()
        soundPool1.autoResume()
    }

    override fun onPause() {
        super.onPause()
        soundPool1.autoPause()
        soundPool2.autoPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool1.release()
        soundPool2.release()
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

                    startCountDown1()
                }
            }
        )
    }

    private fun initSounds1() {
        tickingSoundId1 = soundPool1.load(this, R.raw.timer_ticking, 1)
        bellSoundId1 = soundPool1.load(this, R.raw.timer_bell, 1)
    }


    private fun createCountDownTimer1(initialMills: Long) =
        object : CountDownTimer(initialMills, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                updateRemainTime1(millisUntilFinished)
                updateSeekBar1(millisUntilFinished)
            }
            override fun onFinish() {
                completeCountDown1()
            }
        }

    private fun startCountDown1() {
        currentCountDownTimer1 = createCountDownTimer1(seekBar1.progress * 60 * 1000L)
        currentCountDownTimer1?.start()

        //null이 아닌 경우에만 실행
        tickingSoundId1?.let { soundId ->
            soundPool1.play(soundId, 1F, 1F, 0, -1, 1F)
        }
    }

    private fun completeCountDown1() {
        updateRemainTime1(0)
        updateSeekBar1(0)

        soundPool1.autoPause()
        bellSoundId1?.let { soundId ->
            soundPool1.play(soundId, 1F, 1F, 0, 0, 1F)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateRemainTime1(remainMillis: Long) {
        val remainSeconds = remainMillis / 1000

        remainMinutesTextView.text = "%02d".format(remainSeconds / 60)
        remainSecondsTextView.text = "%02d".format(remainSeconds % 60)
    }

    private fun updateSeekBar1(remainMillis: Long) {
        seekBar1.progress = (remainMillis / 1000 / 60).toInt()
    }



    private fun bindViews2() {
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

                    startCountDown2()
                }
            }
        )
    }

    private fun initSounds2() {
        tickingSoundId2 = soundPool2.load(this, R.raw.timer_ticking, 2)
        bellSoundId2 = soundPool2.load(this, R.raw.timer_bell, 2)
    }

    private fun createCountDownTimer2(initialMills: Long) =
        object : CountDownTimer(initialMills, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                updateRemainTime2(millisUntilFinished)
                updateSeekBar2(millisUntilFinished)
            }
            override fun onFinish() {
                completeCountDown2()
            }
        }

    private fun startCountDown2() {
        currentCountDownTimer2 = createCountDownTimer2(seekBar2.progress * 60 * 1000L)
        currentCountDownTimer2?.start()

        tickingSoundId2?.let { soundId ->
            soundPool2.play(soundId, 1F, 1F, 1, -1, 1F)
        }
    }

    private fun completeCountDown2() {
        updateRemainTime2(0)
        updateSeekBar2(0)

        soundPool1.autoPause()
        bellSoundId2?.let { soundId ->
            soundPool2.play(soundId, 1F, 1F, 1, 0, 1F)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateRemainTime2(remainMillis: Long) {
        val remainSeconds = remainMillis / 1000

        remainMinutesTextView2.text = "%02d".format(remainSeconds / 60)
        remainSecondsTextView2.text = "%02d".format(remainSeconds % 60)
    }

    private fun updateSeekBar2(remainMillis: Long) {
        seekBar2.progress = (remainMillis / 1000 / 60).toInt()
    }
}

