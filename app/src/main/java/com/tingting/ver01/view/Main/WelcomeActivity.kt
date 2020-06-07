package com.tingting.ver01.view.Main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroCustomLayoutFragment
import com.github.appintro.AppIntroPageTransformerType
import com.tingting.ver01.R

class WelcomeActivity: AppIntro(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addSlide(AppIntroCustomLayoutFragment.newInstance(
            R.layout.intro_slide1
        ))
        addSlide(AppIntroCustomLayoutFragment.newInstance(
            R.layout.intro_slide2)
        )
        addSlide(AppIntroCustomLayoutFragment.newInstance(
            R.layout.intro_slide3
        ))
        addSlide(AppIntroCustomLayoutFragment.newInstance(
            R.layout.intro_slide4
        ))
        addSlide(AppIntroCustomLayoutFragment.newInstance(
            R.layout.intro_slide5
        ))
        setTransformer(AppIntroPageTransformerType.Fade)
        isIndicatorEnabled=true
        setProgressIndicator()
        setIndicatorColor(
            selectedIndicatorColor = getColor(R.color.tingtingMain),
            unselectedIndicatorColor = getColor(R.color.darkGray)
        )
        setColorDoneText(getColor(R.color.black))
        setColorSkipButton(getColor(R.color.black))
        setNextArrowColor(getColor(R.color.black))

        isSystemBackButtonLocked=true
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

}