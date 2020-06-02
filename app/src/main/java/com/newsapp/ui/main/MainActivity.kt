package com.newsapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.newsapp.R
import com.newsapp.data.model.Sources
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.click_to_refresh
import kotlinx.android.synthetic.main.activity_main.oops_des
import kotlinx.android.synthetic.main.activity_main.progressBar

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        setupObservables()
        mainViewModel.getSourcesList()

        click_to_refresh.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                mainViewModel.getSourcesList()
            }
        })

    }

    private fun setupObservables() {
        mainViewModel.isLoading.observe(this, Observer<Boolean> {
            if (it) {
                progressBar.visibility = View.VISIBLE;
            } else {
                progressBar.visibility = View.GONE;
            }
        })
        mainViewModel.sourceList.observe(this, Observer {
            it.run() {
                if (it.size > 0) {
                    setupViewPager(pager, it)
                    tabLayout.setupWithViewPager(pager)
                }
            }
        })

    }

    fun hideRefreshLayout() {
        oops_des.visibility = View.GONE
        click_to_refresh.visibility = View.GONE
        tabLayout.visibility = View.VISIBLE
        pager.visibility = View.VISIBLE
    }

    fun showRefreshLayout() {
        oops_des.visibility = View.VISIBLE
        click_to_refresh.visibility = View.VISIBLE
        tabLayout.visibility = View.GONE
        pager.visibility = View.GONE
    }

    private fun setupViewPager(viewPager: ViewPager, sourceList: List<Sources>) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(sourceList)
        viewPager.adapter = adapter

        val currentPage = mainViewModel.currentPage.value ?: 0

        if (currentPage > 0) {
            viewPager.setCurrentItem(currentPage, true)
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                mainViewModel.currentPage.value = position
            }
        })
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) :
        FragmentStatePagerAdapter(manager) {
        private val sourceList = ArrayList<Sources>();
        override fun getItem(position: Int): Fragment {
            val articleFragmet = ArticleListFragment()
            val args = Bundle()
            args.putString("sourceid", sourceList[position].id)
            articleFragmet.arguments = args;
            return articleFragmet
        }

        override fun getCount(): Int {
            return sourceList.size
        }

        fun addFragment(updatedSourceList: List<Sources>) {
            sourceList.clear()
            sourceList.addAll(updatedSourceList)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return sourceList[position].name
        }
    }


}
