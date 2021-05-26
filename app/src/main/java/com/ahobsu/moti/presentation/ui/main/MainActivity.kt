package com.ahobsu.moti.presentation.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.ahobsu.moti.R
import com.ahobsu.moti.data.injection.Injection
import com.ahobsu.moti.databinding.ActivityMainBinding
import com.ahobsu.moti.presentation.BaseActivity
import com.ahobsu.moti.presentation.ui.main.album.AlbumFragment
import com.ahobsu.moti.presentation.ui.main.diary.DiaryFragment
import com.ahobsu.moti.presentation.ui.main.home.HomeAfterFragment
import com.ahobsu.moti.presentation.ui.main.home.HomeBeforeFragment
import com.ahobsu.moti.presentation.ui.main.mypage.MyPageFragment

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel by lazy {
        ViewModelProvider(
            viewModelStore, MainViewModelFactory(Injection.provideAnswerRepository())
        ).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMainDataBinding()
        mainViewModel.getHomeAnswer()

        mainViewModel.todayAnswer.observe(this) {
            supportFragmentManager.beginTransaction().apply {
                replace(
                    R.id.main_container,
                    if (it) HomeAfterFragment.newInstance() else HomeBeforeFragment.newInstance()
                )
            }.commit()
        }

        binding.bottomNavi.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mypage -> {
                    changeFragment(MyPageFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.diary -> {
                    changeFragment(DiaryFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.album -> {
                    changeFragment(AlbumFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.home -> {
                    changeFragment(HomeAfterFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }
                else ->  {
                   false
                }
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_container, fragment)
        }.commit()
    }

    private fun initMainDataBinding() {
        binding.mainVM = mainViewModel
    }
}